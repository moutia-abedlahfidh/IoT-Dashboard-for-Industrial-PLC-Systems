import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Leds } from './leds';

describe('Leds', () => {
  let component: Leds;
  let fixture: ComponentFixture<Leds>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Leds],
    }).compileComponents();

    fixture = TestBed.createComponent(Leds);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
