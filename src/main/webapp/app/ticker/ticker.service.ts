import { Injectable } from '@angular/core';
import { MarketCoin } from './market-coin/market-coin.model';
import { SERVER_API_URL } from '../app.constants';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import { Ticker } from './ticker.model';

export type EntityResponseType = HttpResponse<Ticker>;


@Injectable()
export class TickerService {

  constructor(private http: HttpClient) { }

    private resourceUrl = SERVER_API_URL + 'api/tickers';
    private resourceQuoteUrl = SERVER_API_URL + 'api/quotes';

    addTickers(userId: any, selectedMarketCoins: MarketCoin[]) {
        return this.http.post(`${this.resourceUrl}/${userId}`, selectedMarketCoins).map((res: Response) => {
            return res.json();
        });
    }

    getTickers(userId:number): Observable<EntityResponseType> {

        return this.http.get(`${this.resourceUrl}/user/${userId}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }


    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Ticker = this.convertItemFromServer(res.body);
        return res.clone({body});
    }


    /**
     * Convert a returned JSON object to Ticker.
     */
    private convertItemFromServer(ticker : Ticker): Ticker {
        const copy: Ticker = Object.assign({}, ticker);
        return copy;
    }
}
