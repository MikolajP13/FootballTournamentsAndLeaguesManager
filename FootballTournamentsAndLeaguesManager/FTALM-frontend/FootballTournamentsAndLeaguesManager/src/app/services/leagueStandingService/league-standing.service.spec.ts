import { TestBed } from '@angular/core/testing';

import { LeagueStandingService } from './league-standing.service';

describe('LeagueStandingService', () => {
  let service: LeagueStandingService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(LeagueStandingService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
