import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';
import { MarketCoin } from './market-coin.model';
import { createRequestOption } from '../../shared';
import { Quote } from '../../entities/quote/quote.model';
import { CoinMarket } from '../../model/coin-market.model';

export type EntityResponseType = HttpResponse<MarketCoin>;


@Injectable()
export class MarketCoinService {

    private resourceUrl = SERVER_API_URL + 'api/market-coins';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/market-coins';

    constructor(private http: HttpClient) { }

    create(marketCoin: MarketCoin): Observable<EntityResponseType> {
        const copy = this.convert(marketCoin);
        return this.http.post<MarketCoin>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    //
    // create(marketCoin: MarketCoin): Observable<EntityResponseType> {
    //     const copy = this.convert(marketCoin);
    //     return this.http.post<EntityResponseType>(this.resourceUrl, copy, { observe: 'response' })
    //         .map((res: EntityResponseType) => this.convertResponse(res));
    //     // return this.http.post<EntityResponseType>(this.resourceUrl, copy, { observe: 'response' })
    //     //     .map((res: EntityResponseType) => this.convertResponse(res));
    // }

    update(marketCoin: MarketCoin): Observable<EntityResponseType> {
        const copy = this.convert(marketCoin);
        return this.http.put(this.resourceUrl, copy, {observe: 'response'})
        .map((res: EntityResponseType) => this.convertResponse(res))
    }


    find(id: number): Observable<EntityResponseType> {
        return this.http.get<MarketCoin>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    findMarketCoinAll(marketId: number,userId:number): Observable<HttpResponse<MarketCoin[]>>{
        return this.http.get<MarketCoin[]>(`${this.resourceUrl}/coins/${marketId}/${userId}`, { observe: 'response'})
            .map((res: HttpResponse<MarketCoin[]>) => this.convertArrayResponse(res));
    }

    query(req?: any): Observable<HttpResponse<MarketCoin[]>> {
        const options = createRequestOption(req);
        return this.http.get<MarketCoin[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<MarketCoin[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});

    }

    search(req?: any): Observable<HttpResponse<MarketCoin[]>> {
        const options = createRequestOption(req);
        return this.http.get<MarketCoin[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<MarketCoin[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: MarketCoin = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to MarketCoin.
     */
    private convertItemFromServer(marketCoin: MarketCoin): MarketCoin {
        const entity: MarketCoin = Object.assign(new MarketCoin(), marketCoin);
        return entity;
    }

    /**
     * Convert a MarketCoin to a JSON which can be sent to the server.
     */
    private convert(marketCoin: MarketCoin): MarketCoin {
        const copy: MarketCoin = Object.assign({}, marketCoin);
        return copy;
    }

    private convertArrayResponse(res: HttpResponse<MarketCoin[]>): HttpResponse<MarketCoin[]> {
        const jsonResponse: MarketCoin[] = res.body;
        const body: MarketCoin[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }
}
