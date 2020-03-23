import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DuplicateListComponent } from './duplicate-list.component';

describe('DuplicateListComponent', () => {
  let component: DuplicateListComponent;
  let fixture: ComponentFixture<DuplicateListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DuplicateListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DuplicateListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
