import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Market } from './market.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Market>;

@Injectable()
export class MarketService {

    private resourceUrl =  SERVER_API_URL + 'api/markets';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/markets';

    constructor(private http: HttpClient) { }

    create(market: Market): Observable<EntityResponseType> {
        const copy = this.convert(market);
        return this.http.post<Market>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(market: Market): Observable<EntityResponseType> {
        const copy = this.convert(market);
        return this.http.put<Market>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Market>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Market[]>> {
        const options = createRequestOption(req);
        return this.http.get<Market[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Market[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Market[]>> {
        const options = createRequestOption(req);
        return this.http.get<Market[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Market[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Market = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Market[]>): HttpResponse<Market[]> {
        const jsonResponse: Market[] = res.body;
        const body: Market[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Market.
     */
    private convertItemFromServer(market: Market): Market {
        const copy: Market = Object.assign({}, market);
        return copy;
    }

    /**
     * Convert a Market to a JSON which can be sent to the server.
     */
    private convert(market: Market): Market {
        const copy: Market = Object.assign({}, market);
        return copy;
    }
}
