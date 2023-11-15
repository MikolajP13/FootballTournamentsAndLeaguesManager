import { TestBed } from '@angular/core/testing';

import { MatchWeekNumberService } from './match-week-number.service';

describe('MatchWeekNumberService', () => {
  let service: MatchWeekNumberService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(MatchWeekNumberService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
