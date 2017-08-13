import { TestBed, inject } from '@angular/core/testing';

import { CoinPriceService } from './coin-price.service';
import {HttpClientTestingModule} from '@angular/common/http/testing';

describe('CoinPriceService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      providers: [CoinPriceService]
    });
  });

  it('should be created', inject([CoinPriceService], (service: CoinPriceService) => {
    expect(service).toBeTruthy();
  }));
});
