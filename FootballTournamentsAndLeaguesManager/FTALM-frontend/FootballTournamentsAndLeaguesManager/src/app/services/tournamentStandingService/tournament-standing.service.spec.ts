import { TestBed } from '@angular/core/testing';

import { TournamentStandingService } from './tournament-standing.service';

describe('TournamentStandingService', () => {
  let service: TournamentStandingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TournamentStandingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
