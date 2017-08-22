import { TestBed, inject } from '@angular/core/testing';

import { CoinSidenavService } from './coin-sidenav.service';
import { HttpClientTestingModule } from "@angular/common/http/testing";
import { HttpTestingController } from "@angular/common/http/testing";

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

  it('should list the coins', () => {
    const coinService = TestBed.get(CoinSidenavService);
    const http = TestBed.get(HttpTestingController);
    // fake response
    const expectedCoins = [{ name: 'Bitcoin' }];

    let actualCoins = [];
    coinService.coins().subscribe((coins: Array<any>) => {
      actualCoins = coins;
    });

   // http.expectOne('/api/coins').flush(expectedCoins);

    expect(actualCoins).toEqual(expectedCoins);
  });
});
