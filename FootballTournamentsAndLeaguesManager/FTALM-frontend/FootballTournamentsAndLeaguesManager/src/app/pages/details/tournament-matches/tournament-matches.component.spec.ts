import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TournamentMatchesComponent } from './tournament-matches.component';

describe('TournamentMatchesComponent', () => {
  let component: TournamentMatchesComponent;
  let fixture: ComponentFixture<TournamentMatchesComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TournamentMatchesComponent]
    });
    fixture = TestBed.createComponent(TournamentMatchesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
