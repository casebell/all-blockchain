import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Kraken } from './kraken.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class KrakenService {

    private resourceUrl = 'api/krakens';
    private resourceSearchUrl = 'api/_search/krakens';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(kraken: Kraken): Observable<Kraken> {
        const copy = this.convert(kraken);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(kraken: Kraken): Observable<Kraken> {
        const copy = this.convert(kraken);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Kraken> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
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
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.createdat = this.dateUtils
            .convertDateTimeFromServer(entity.createdat);
    }

    private convert(kraken: Kraken): Kraken {
        const copy: Kraken = Object.assign({}, kraken);

        copy.createdat = this.dateUtils.toDate(kraken.createdat);
        return copy;
    }
}
