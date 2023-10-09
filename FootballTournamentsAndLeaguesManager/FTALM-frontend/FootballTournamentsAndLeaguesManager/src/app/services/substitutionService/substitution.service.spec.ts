import { TestBed } from '@angular/core/testing';

import { SubstitutionService } from './substitution.service';

describe('SubstitutionService', () => {
  let service: SubstitutionService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SubstitutionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
