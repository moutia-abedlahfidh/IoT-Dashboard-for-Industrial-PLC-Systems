import { Component, ChangeDetectorRef, OnInit, OnDestroy, Inject, PLATFORM_ID } from '@angular/core';
import { CommonModule, isPlatformBrowser } from '@angular/common';
import { Api } from '../services/api';
import { distinctUntilChanged, interval, switchMap } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';


@Component({
  selector: 'app-leds',
  imports: [CommonModule],
  templateUrl: './leds.html',
  styleUrl: './leds.css',
})
export class Leds implements OnInit{
  bits16 = "000000000000000";
  controle = 0;
  
  constructor(private cdr: ChangeDetectorRef,private api : Api,@Inject(PLATFORM_ID) private platformId: Object) {}
  
  ngOnInit(): void{
    if (isPlatformBrowser(this.platformId)) {
      this.LiveServerStatus();
      this.LiveServerControl();
    }
  }

  LiveServerStatus() : void {

  const eventSource = this.api.getLiveStatusStream();

  eventSource.onmessage = (event) => {

    const match = event.data.match(/value=([\d.]+)/);

    if (match) {
      this.bits16 = Number(match[1]).toString(2).padStart(16,'0');
      this.cdr.detectChanges();
    }
      
    }

  eventSource.onerror = (error) => {
    console.error("Stream error:", error);
  };

}

  LiveServerControl() : void {

  const eventSourceControl = this.api.getLiveControlStream();

  eventSourceControl.onmessage = (event) => {

    const match = event.data.match(/mode=([\d.]+)/);

    if (match) {
      this.controle = Number(match[1]);
      this.cdr.detectChanges();
    }
      
    }

  eventSourceControl.onerror = (error) => {
    console.error("Stream error:", error);
  };

}

   sendControl(mode: number) :void {
    this.controle = mode ;
    this.api.sendControl(mode).subscribe(
      {
        error : (err) => console.log(err) 
      }
    );
  }


}