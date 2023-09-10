import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LeagueAboutComponent } from './league-about.component';

describe('LeagueAboutComponent', () => {
  let component: LeagueAboutComponent;
  let fixture: ComponentFixture<LeagueAboutComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [LeagueAboutComponent]
    });
    fixture = TestBed.createComponent(LeagueAboutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
