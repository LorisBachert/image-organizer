import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TripNameFormComponent } from './trip-name-form.component';

describe('TripNameFormComponent', () => {
  let component: TripNameFormComponent;
  let fixture: ComponentFixture<TripNameFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TripNameFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TripNameFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
