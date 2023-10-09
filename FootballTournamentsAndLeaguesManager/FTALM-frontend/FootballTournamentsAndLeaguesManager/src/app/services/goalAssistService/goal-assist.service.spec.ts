import { TestBed } from '@angular/core/testing';

import { GoalAssistService } from './goal-assist.service';

describe('GoalAssistService', () => {
  let service: GoalAssistService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GoalAssistService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
