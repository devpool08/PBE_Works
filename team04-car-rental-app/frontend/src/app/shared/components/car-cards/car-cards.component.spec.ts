import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarCardComponent } from './car-cards.component';

describe('CarCardComponent', () => {
  let component: CarCardComponent;
  let fixture: ComponentFixture<CarCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [CarCardComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CarCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
