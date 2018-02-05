import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { Quote } from './quote.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<Quote>;

@Injectable()
export class QuoteService {

    private resourceUrl =  SERVER_API_URL + 'api/quotes';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/quotes';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(quote: Quote): Observable<EntityResponseType> {
        const copy = this.convert(quote);
        return this.http.post<Quote>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(quote: Quote): Observable<EntityResponseType> {
        const copy = this.convert(quote);
        return this.http.put<Quote>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Quote>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<Quote[]>> {
        const options = createRequestOption(req);
        return this.http.get<Quote[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Quote[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<Quote[]>> {
        const options = createRequestOption(req);
        return this.http.get<Quote[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Quote[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Quote = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<Quote[]>): HttpResponse<Quote[]> {
        const jsonResponse: Quote[] = res.body;
        const body: Quote[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Quote.
     */
    private convertItemFromServer(quote: Quote): Quote {
        const copy: Quote = Object.assign({}, quote);
        copy.quoteTime = this.dateUtils
            .convertDateTimeFromServer(quote.quoteTime);
        return copy;
    }

    /**
     * Convert a Quote to a JSON which can be sent to the server.
     */
    private convert(quote: Quote): Quote {
        const copy: Quote = Object.assign({}, quote);

        copy.quoteTime = this.dateUtils.toDate(quote.quoteTime);
        return copy;
    }
}
