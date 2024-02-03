import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeletePlayerPopupComponent } from './delete-player-popup.component';

describe('DeletePlayerPopupComponent', () => {
  let component: DeletePlayerPopupComponent;
  let fixture: ComponentFixture<DeletePlayerPopupComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DeletePlayerPopupComponent]
    });
    fixture = TestBed.createComponent(DeletePlayerPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
