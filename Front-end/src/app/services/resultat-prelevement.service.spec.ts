import { TestBed } from '@angular/core/testing';

import { ResultatPrelevementService } from './resultat-prelevement.service';

describe('ResultatPrelevementService', () => {
  let service: ResultatPrelevementService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ResultatPrelevementService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
