import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DirectorySelectComponent } from './directory-select.component';

describe('DirectorySelectComponent', () => {
  let component: DirectorySelectComponent;
  let fixture: ComponentFixture<DirectorySelectComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DirectorySelectComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DirectorySelectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
