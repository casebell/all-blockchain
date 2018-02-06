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

    getTickers(userId:number): Observable<HttpResponse<Ticker[]>> {

        return this.http.get<Ticker[]>(`${this.resourceUrl}/user/${userId}`, { observe: 'response'})
            .map((res: HttpResponse<Ticker[]>) => this.convertArrayResponse(res));
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

    private convertArrayResponse(res: HttpResponse<Ticker[]>): HttpResponse<Ticker[]> {
        const jsonResponse: Ticker[] = res.body;
        const body: Ticker[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }
}
