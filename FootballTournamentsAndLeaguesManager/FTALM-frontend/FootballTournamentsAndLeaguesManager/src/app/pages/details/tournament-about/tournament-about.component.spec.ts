import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TournamentAboutComponent } from './tournament-about.component';

describe('TournamentAboutComponent', () => {
  let component: TournamentAboutComponent;
  let fixture: ComponentFixture<TournamentAboutComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TournamentAboutComponent]
    });
    fixture = TestBed.createComponent(TournamentAboutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
