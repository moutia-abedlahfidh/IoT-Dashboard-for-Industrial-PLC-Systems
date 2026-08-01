import { ChangeDetectorRef, Component, OnInit, Inject, PLATFORM_ID } from '@angular/core';
import { Api } from '../services/api';
import { CommonModule, isPlatformBrowser } from '@angular/common';

@Component({
  selector: 'app-sensor',
  imports: [CommonModule],
  templateUrl: './sensor.html',
  styleUrl: './sensor.css',
})
export class Sensor implements OnInit{

  valueSoll = 25.69;
  timeSoll = "Tue Jun 02 06:28:59 UTC 2026";
  valueIst = 26.01;
  timeIst = "Tue Jun 02 06:28:59 UTC 2026";
  valueDiff = 0.34;
  timeDiff = "Tue Jun 02 06:28:44 UTC 2026";

  constructor(private cdr: ChangeDetectorRef,private api : Api,@Inject(PLATFORM_ID) private platformId: Object) {}

  ngOnInit() {
    

    if (isPlatformBrowser(this.platformId)) {
    this.LiveSoll();
    this.LiveIst() ;
    this.LiveDiff();
    }
  }

  LiveSoll(): void {

  const eventSourceControl = this.api.getLiveSollStream();

  eventSourceControl.onmessage = (event) => {

    const valueMatch = event.data.match(/value=([\d.]+)/);
    const timeMatch = event.data.match(/Time=([^,]+)/);

    if (valueMatch) {
      this.valueSoll = Number(valueMatch[1]);

      if (timeMatch) {
        this.timeSoll = timeMatch[1];
      }
      this.cdr.detectChanges();
    }
  };

  eventSourceControl.onerror = (error) => {
    console.error("Stream error:", error);
  };

} 

LiveIst(): void {

  const eventSourceControl = this.api.getLiveIstStream();

  eventSourceControl.onmessage = (event) => {

    const valueMatch = event.data.match(/value=([\d.]+)/);
    const timeMatch = event.data.match(/Time=([^,]+)/);

    if (valueMatch) {
      this.valueIst = Number(valueMatch[1]);

      if (timeMatch) {
        this.timeIst = timeMatch[1];
      }
      this.cdr.detectChanges();
    }
  };

  eventSourceControl.onerror = (error) => {
    console.error("Stream error:", error);
  };

}

LiveDiff(): void {

  const eventSourceControl = this.api.getLiveDiffStream();

  eventSourceControl.onmessage = (event) => {

    const valueMatch = event.data.match(/value=([\d.]+)/);
    const timeMatch = event.data.match(/Time=([^,]+)/);

    if (valueMatch) {
      this.valueDiff = Number(valueMatch[1]);

      if (timeMatch) {
        this.timeDiff = timeMatch[1];
      }
      this.cdr.detectChanges();
    }
  };

  eventSourceControl.onerror = (error) => {
    console.error("Stream error:", error);
  };

}

}
