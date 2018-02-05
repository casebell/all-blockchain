import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Bitfinex } from './bitfinex.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Bitfinex>;

@Injectable()
export class BitfinexService {

    private resourceUrl =  SERVER_API_URL + 'api/bitfinexes';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/bitfinexes';

    constructor(private http: HttpClient) { }

    create(bitfinex: Bitfinex): Observable<EntityResponseType> {
        const copy = this.convert(bitfinex);
        return this.http.post<Bitfinex>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(bitfinex: Bitfinex): Observable<EntityResponseType> {
        const copy = this.convert(bitfinex);
        return this.http.put<Bitfinex>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Bitfinex>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Bitfinex[]>> {
        const options = createRequestOption(req);
        return this.http.get<Bitfinex[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Bitfinex[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Bitfinex[]>> {
        const options = createRequestOption(req);
        return this.http.get<Bitfinex[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Bitfinex[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Bitfinex = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Bitfinex[]>): HttpResponse<Bitfinex[]> {
        const jsonResponse: Bitfinex[] = res.body;
        const body: Bitfinex[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Bitfinex.
     */
    private convertItemFromServer(bitfinex: Bitfinex): Bitfinex {
        const copy: Bitfinex = Object.assign({}, bitfinex);
        return copy;
    }

    /**
     * Convert a Bitfinex to a JSON which can be sent to the server.
     */
    private convert(bitfinex: Bitfinex): Bitfinex {
        const copy: Bitfinex = Object.assign({}, bitfinex);
        return copy;
    }
}
