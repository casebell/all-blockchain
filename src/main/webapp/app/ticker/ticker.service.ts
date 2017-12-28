import { Injectable } from '@angular/core';
import { MarketCoin } from './market-coin/market-coin.model';
import { SERVER_API_URL } from '../app.constants';
import { Http, Response } from '@angular/http';

@Injectable()
export class TickerService {

  constructor(private http: Http) { }

    private resourceUrl = SERVER_API_URL + 'api/tickers';

    addTickers(userId: any, selectedMarketCoins: MarketCoin[]) {
        return this.http.post(`${this.resourceUrl}/${userId}`, selectedMarketCoins).map((res: Response) => {
            return res.json();
        });
    }
}
