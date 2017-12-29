import { Injectable } from '@angular/core';
import { MarketCoin } from './market-coin/market-coin.model';
import { SERVER_API_URL } from '../app.constants';
import { Http, Response } from '@angular/http';
import { createRequestOption, ResponseWrapper } from '../shared';
import { Observable } from 'rxjs/Rx';
import { Ticker } from './ticker.model';

@Injectable()
export class TickerService {

  constructor(private http: Http) { }

    private resourceUrl = SERVER_API_URL + 'api/tickers';

    addTickers(userId: any, selectedMarketCoins: MarketCoin[]) {
        return this.http.post(`${this.resourceUrl}/${userId}`, selectedMarketCoins).map((res: Response) => {
            return res.json();
        });
    }

    getTickers(userId:number): Observable<ResponseWrapper> {

        return this.http.get(`${this.resourceUrl}/user/${userId}`)
            .map((res: Response) => this.convertResponse(res));
    }


    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to Ticker.
     */
    private convertItemFromServer(json: any): Ticker {
        const entity: Ticker = Object.assign(new Ticker(), json);
        return entity;
    }
}
