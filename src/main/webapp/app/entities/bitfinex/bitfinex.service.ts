import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { Bitfinex } from './bitfinex.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class BitfinexService {

    private resourceUrl =  SERVER_API_URL + 'api/bitfinexes';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/bitfinexes';

    constructor(private http: Http) { }

    create(bitfinex: Bitfinex): Observable<Bitfinex> {
        const copy = this.convert(bitfinex);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(bitfinex: Bitfinex): Observable<Bitfinex> {
        const copy = this.convert(bitfinex);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Bitfinex> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
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
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    /**
     * Convert a returned JSON object to Bitfinex.
     */
    private convertItemFromServer(json: any): Bitfinex {
        const entity: Bitfinex = Object.assign(new Bitfinex(), json);
        return entity;
    }

    /**
     * Convert a Bitfinex to a JSON which can be sent to the server.
     */
    private convert(bitfinex: Bitfinex): Bitfinex {
        const copy: Bitfinex = Object.assign({}, bitfinex);
        return copy;
    }
}
