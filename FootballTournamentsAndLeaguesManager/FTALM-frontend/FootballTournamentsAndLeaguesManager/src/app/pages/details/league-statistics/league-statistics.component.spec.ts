import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeagueStatisticsComponent } from './league-statistics.component';

describe('LeagueStatisticsComponent', () => {
  let component: LeagueStatisticsComponent;
  let fixture: ComponentFixture<LeagueStatisticsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LeagueStatisticsComponent]
    });
    fixture = TestBed.createComponent(LeagueStatisticsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
