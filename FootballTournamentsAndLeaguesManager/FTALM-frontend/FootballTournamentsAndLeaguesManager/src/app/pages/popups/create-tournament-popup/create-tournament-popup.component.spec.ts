import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CreateTournamentPopupComponent } from './create-tournament-popup.component';

describe('CreateTournamentPopupComponent', () => {
  let component: CreateTournamentPopupComponent;
  let fixture: ComponentFixture<CreateTournamentPopupComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CreateTournamentPopupComponent]
    });
    fixture = TestBed.createComponent(CreateTournamentPopupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
