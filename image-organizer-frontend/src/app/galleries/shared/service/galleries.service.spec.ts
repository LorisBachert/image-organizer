import { TestBed } from '@angular/core/testing';

import { GalleriesService } from './galleries.service';

describe('GalleriesService', () => {
  let service: GalleriesService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(GalleriesService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
