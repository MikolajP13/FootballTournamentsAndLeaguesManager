import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TeamPlayersComponent } from './team-players.component';

describe('TeamPlayersComponent', () => {
  let component: TeamPlayersComponent;
  let fixture: ComponentFixture<TeamPlayersComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TeamPlayersComponent]
    });
    fixture = TestBed.createComponent(TeamPlayersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
