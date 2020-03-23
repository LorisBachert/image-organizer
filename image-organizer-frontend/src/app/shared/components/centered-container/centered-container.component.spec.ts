import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CenteredContainerComponent } from './centered-container.component';

describe('CenteredContainerComponent', () => {
  let component: CenteredContainerComponent;
  let fixture: ComponentFixture<CenteredContainerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CenteredContainerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CenteredContainerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
