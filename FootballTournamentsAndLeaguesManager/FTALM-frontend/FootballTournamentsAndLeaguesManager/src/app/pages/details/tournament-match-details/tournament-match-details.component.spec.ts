import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TournamentMatchDetailsComponent } from './tournament-match-details.component';

describe('TournamentMatchDetailsComponent', () => {
  let component: TournamentMatchDetailsComponent;
  let fixture: ComponentFixture<TournamentMatchDetailsComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TournamentMatchDetailsComponent]
    });
    fixture = TestBed.createComponent(TournamentMatchDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
