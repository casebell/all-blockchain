import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Kraken } from './kraken.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Kraken>;

@Injectable()
export class KrakenService {

    private resourceUrl =  SERVER_API_URL + 'api/krakens';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/krakens';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(kraken: Kraken): Observable<EntityResponseType> {
        const copy = this.convert(kraken);
        return this.http.post<Kraken>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(kraken: Kraken): Observable<EntityResponseType> {
        const copy = this.convert(kraken);
        return this.http.put<Kraken>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Kraken>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Kraken[]>> {
        const options = createRequestOption(req);
        return this.http.get<Kraken[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Kraken[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Kraken[]>> {
        const options = createRequestOption(req);
        return this.http.get<Kraken[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Kraken[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Kraken = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Kraken[]>): HttpResponse<Kraken[]> {
        const jsonResponse: Kraken[] = res.body;
        const body: Kraken[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Kraken.
     */
    private convertItemFromServer(kraken: Kraken): Kraken {
        const copy: Kraken = Object.assign({}, kraken);
        copy.createdat = this.dateUtils
            .convertDateTimeFromServer(kraken.createdat);
        return copy;
    }

    /**
     * Convert a Kraken to a JSON which can be sent to the server.
     */
    private convert(kraken: Kraken): Kraken {
        const copy: Kraken = Object.assign({}, kraken);

        copy.createdat = this.dateUtils.toDate(kraken.createdat);
        return copy;
    }
}
