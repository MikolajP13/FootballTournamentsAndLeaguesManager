import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EditPlayerPopupComponent } from './edit-player-popup.component';

describe('EditPlayerPopupComponent', () => {
  let component: EditPlayerPopupComponent;
  let fixture: ComponentFixture<EditPlayerPopupComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EditPlayerPopupComponent]
    });
    fixture = TestBed.createComponent(EditPlayerPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
