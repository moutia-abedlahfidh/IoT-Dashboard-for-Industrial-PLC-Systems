import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Values } from './values';

describe('Values', () => {
  let component: Values;
  let fixture: ComponentFixture<Values>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Values],
    }).compileComponents();

    fixture = TestBed.createComponent(Values);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
