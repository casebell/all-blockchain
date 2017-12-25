import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';
import { MarketCoin } from './market-coin.model';
import { createRequestOption, ResponseWrapper } from '../../shared';


@Injectable()
export class MarketCoinService {

    private resourceUrl = SERVER_API_URL + 'api/market-coins';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/market-coins';

    constructor(private http: Http) { }

    create(marketCoin: MarketCoin): Observable<MarketCoin> {
        const copy = this.convert(marketCoin);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(marketCoin: MarketCoin): Observable<MarketCoin> {
        const copy = this.convert(marketCoin);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<MarketCoin> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    findMarketCoinAll(id: number){
        return this.http.get(`${this.resourceUrl}/coins/${id}`).map((res: Response) => {
            return  res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
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
     * Convert a returned JSON object to MarketCoin.
     */
    private convertItemFromServer(json: any): MarketCoin {
        const entity: MarketCoin = Object.assign(new MarketCoin(), json);
        return entity;
    }

    /**
     * Convert a MarketCoin to a JSON which can be sent to the server.
     */
    private convert(marketCoin: MarketCoin): MarketCoin {
        const copy: MarketCoin = Object.assign({}, marketCoin);
        return copy;
    }
}
