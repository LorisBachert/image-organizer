import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GalleryMenuComponent } from './gallery-menu.component';

describe('GalleryMenuComponent', () => {
  let component: GalleryMenuComponent;
  let fixture: ComponentFixture<GalleryMenuComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GalleryMenuComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GalleryMenuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
