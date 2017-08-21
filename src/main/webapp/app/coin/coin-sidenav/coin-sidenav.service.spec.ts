import { TestBed, inject } from '@angular/core/testing';

import { CoinSidenavService } from './coin-sidenav.service';
import { HttpClientTestingModule } from "@angular/common/http/testing";

describe('CoinSidenavService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [CoinSidenavService]
    });
  });

  it('should be created', inject([CoinSidenavService], (service: CoinSidenavService) => {
    expect(service).toBeTruthy();
  }));
});
