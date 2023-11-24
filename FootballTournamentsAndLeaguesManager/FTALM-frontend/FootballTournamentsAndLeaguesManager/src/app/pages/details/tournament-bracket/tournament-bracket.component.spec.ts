import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TournamentBracketComponent } from './tournament-bracket.component';

describe('TournamentBracketComponent', () => {
  let component: TournamentBracketComponent;
  let fixture: ComponentFixture<TournamentBracketComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TournamentBracketComponent]
    });
    fixture = TestBed.createComponent(TournamentBracketComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
