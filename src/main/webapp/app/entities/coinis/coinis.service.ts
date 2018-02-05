import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Coinis } from './coinis.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Coinis>;

@Injectable()
export class CoinisService {

    private resourceUrl =  SERVER_API_URL + 'api/coinis';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/coinis';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(coinis: Coinis): Observable<EntityResponseType> {
        const copy = this.convert(coinis);
        return this.http.post<Coinis>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(coinis: Coinis): Observable<EntityResponseType> {
        const copy = this.convert(coinis);
        return this.http.put<Coinis>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Coinis>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Coinis[]>> {
        const options = createRequestOption(req);
        return this.http.get<Coinis[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Coinis[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Coinis[]>> {
        const options = createRequestOption(req);
        return this.http.get<Coinis[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Coinis[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Coinis = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Coinis[]>): HttpResponse<Coinis[]> {
        const jsonResponse: Coinis[] = res.body;
        const body: Coinis[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Coinis.
     */
    private convertItemFromServer(coinis: Coinis): Coinis {
        const copy: Coinis = Object.assign({}, coinis);
        copy.createdat = this.dateUtils
            .convertDateTimeFromServer(coinis.createdat);
        return copy;
    }

    /**
     * Convert a Coinis to a JSON which can be sent to the server.
     */
    private convert(coinis: Coinis): Coinis {
        const copy: Coinis = Object.assign({}, coinis);

        copy.createdat = this.dateUtils.toDate(coinis.createdat);
        return copy;
    }
}
