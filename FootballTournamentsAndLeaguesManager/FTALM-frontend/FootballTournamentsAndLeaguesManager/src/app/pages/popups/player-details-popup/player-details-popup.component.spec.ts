import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PlayerDetailsPopupComponent } from './player-details-popup.component';

describe('PlayerDetailsPopupComponent', () => {
  let component: PlayerDetailsPopupComponent;
  let fixture: ComponentFixture<PlayerDetailsPopupComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [PlayerDetailsPopupComponent]
    });
    fixture = TestBed.createComponent(PlayerDetailsPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
