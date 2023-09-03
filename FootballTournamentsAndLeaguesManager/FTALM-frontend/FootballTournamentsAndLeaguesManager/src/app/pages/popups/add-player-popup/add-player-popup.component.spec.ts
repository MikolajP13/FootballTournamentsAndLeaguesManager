import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddPlayerPopupComponent } from './add-player-popup.component';

describe('AddPlayerPopupComponent', () => {
  let component: AddPlayerPopupComponent;
  let fixture: ComponentFixture<AddPlayerPopupComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [AddPlayerPopupComponent]
    });
    fixture = TestBed.createComponent(AddPlayerPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
