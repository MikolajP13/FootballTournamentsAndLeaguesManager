import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeagueMatchComponent } from './league-match.component';

describe('LeagueMatchComponent', () => {
  let component: LeagueMatchComponent;
  let fixture: ComponentFixture<LeagueMatchComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LeagueMatchComponent]
    });
    fixture = TestBed.createComponent(LeagueMatchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
