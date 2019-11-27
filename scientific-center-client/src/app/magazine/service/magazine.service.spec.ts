import { TestBed } from '@angular/core/testing';

import { MagazineService } from './magazine.service';

describe('MagazineService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: MagazineService = TestBed.get(MagazineService);
    expect(service).toBeTruthy();
  });
});
