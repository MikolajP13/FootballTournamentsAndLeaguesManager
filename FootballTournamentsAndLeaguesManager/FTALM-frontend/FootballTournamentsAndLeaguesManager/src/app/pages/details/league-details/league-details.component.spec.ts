import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeagueDetailsComponent } from './league-details.component';

describe('LeagueDetailsComponent', () => {
  let component: LeagueDetailsComponent;
  let fixture: ComponentFixture<LeagueDetailsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LeagueDetailsComponent]
    });
    fixture = TestBed.createComponent(LeagueDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
