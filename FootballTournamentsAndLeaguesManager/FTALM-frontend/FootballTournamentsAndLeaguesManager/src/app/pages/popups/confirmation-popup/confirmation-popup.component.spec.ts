import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmationPopupComponent } from './confirmation-popup.component';

describe('ConfirmationPopupComponent', () => {
  let component: ConfirmationPopupComponent;
  let fixture: ComponentFixture<ConfirmationPopupComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ConfirmationPopupComponent]
    });
    fixture = TestBed.createComponent(ConfirmationPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
