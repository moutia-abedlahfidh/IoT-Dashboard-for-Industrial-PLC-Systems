import { Component, signal } from '@angular/core';
import { Leds } from './leds/leds';
import { Sensor } from './sensor/sensor';
import { Values } from './values/values';
import { Position } from './position/position';

@Component({
  selector: 'app-root',
  imports: [Leds,Sensor,Values,Position],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('Frontend_Seminar');
}
