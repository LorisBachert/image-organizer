import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DuplicatesComponent } from './duplicates.component';

describe('DuplicatesComponent', () => {
  let component: DuplicatesComponent;
  let fixture: ComponentFixture<DuplicatesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DuplicatesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DuplicatesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
