import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GalleryNameFormComponent } from './trip-name-form.component';

describe('TripNameFormComponent', () => {
  let component: GalleryNameFormComponent;
  let fixture: ComponentFixture<GalleryNameFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GalleryNameFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GalleryNameFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
