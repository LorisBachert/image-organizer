import { TestBed } from '@angular/core/testing';

import { DuplicateService } from './duplicate.service';

describe('DuplicateService', () => {
  let service: DuplicateService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DuplicateService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
