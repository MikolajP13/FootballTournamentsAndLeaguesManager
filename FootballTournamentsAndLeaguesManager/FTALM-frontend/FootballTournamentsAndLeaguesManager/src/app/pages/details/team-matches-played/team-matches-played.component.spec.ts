import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamMatchesPlayedComponent } from './team-matches-played.component';

describe('TeamMatchesPlayedComponent', () => {
  let component: TeamMatchesPlayedComponent;
  let fixture: ComponentFixture<TeamMatchesPlayedComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TeamMatchesPlayedComponent]
    });
    fixture = TestBed.createComponent(TeamMatchesPlayedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
