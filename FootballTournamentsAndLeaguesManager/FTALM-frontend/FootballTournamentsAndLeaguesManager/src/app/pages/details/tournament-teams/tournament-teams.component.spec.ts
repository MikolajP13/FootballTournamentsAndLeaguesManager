import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TournamentTeamsComponent } from './tournament-teams.component';

describe('TournamentTeamsComponent', () => {
  let component: TournamentTeamsComponent;
  let fixture: ComponentFixture<TournamentTeamsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TournamentTeamsComponent]
    });
    fixture = TestBed.createComponent(TournamentTeamsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
