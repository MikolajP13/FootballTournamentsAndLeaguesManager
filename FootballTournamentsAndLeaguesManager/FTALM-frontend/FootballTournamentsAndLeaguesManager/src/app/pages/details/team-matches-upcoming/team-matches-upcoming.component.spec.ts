import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamMatchesUpcomingComponent } from './team-matches-upcoming.component';

describe('TeamMatchesUpcomingComponent', () => {
  let component: TeamMatchesUpcomingComponent;
  let fixture: ComponentFixture<TeamMatchesUpcomingComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TeamMatchesUpcomingComponent]
    });
    fixture = TestBed.createComponent(TeamMatchesUpcomingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
