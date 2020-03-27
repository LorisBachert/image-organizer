import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GalleriesComponent } from './trips.component';

describe('TripsComponent', () => {
  let component: GalleriesComponent;
  let fixture: ComponentFixture<GalleriesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GalleriesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GalleriesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
