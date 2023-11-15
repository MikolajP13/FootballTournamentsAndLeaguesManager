import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeagueMatchDetailsComponent } from './league-match-details.component';

describe('LeagueMatchDetailsComponent', () => {
  let component: LeagueMatchDetailsComponent;
  let fixture: ComponentFixture<LeagueMatchDetailsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LeagueMatchDetailsComponent]
    });
    fixture = TestBed.createComponent(LeagueMatchDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
