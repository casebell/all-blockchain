import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { AirDrop } from './air-drop.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<AirDrop>;

@Injectable()
export class AirDropService {

    private resourceUrl =  SERVER_API_URL + 'api/air-drops';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/air-drops';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(airDrop: AirDrop): Observable<EntityResponseType> {
        const copy = this.convert(airDrop);
        return this.http.post<AirDrop>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(airDrop: AirDrop): Observable<EntityResponseType> {
        const copy = this.convert(airDrop);
        return this.http.put<AirDrop>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<AirDrop>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<AirDrop[]>> {
        const options = createRequestOption(req);
        return this.http.get<AirDrop[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<AirDrop[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<AirDrop[]>> {
        const options = createRequestOption(req);
        return this.http.get<AirDrop[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<AirDrop[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: AirDrop = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<AirDrop[]>): HttpResponse<AirDrop[]> {
        const jsonResponse: AirDrop[] = res.body;
        const body: AirDrop[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to AirDrop.
     */
    private convertItemFromServer(airDrop: AirDrop): AirDrop {
        const copy: AirDrop = Object.assign({}, airDrop);
        copy.startDate = this.dateUtils
            .convertDateTimeFromServer(airDrop.startDate);
        copy.finishDate = this.dateUtils
            .convertDateTimeFromServer(airDrop.finishDate);
        return copy;
    }

    /**
     * Convert a AirDrop to a JSON which can be sent to the server.
     */
    private convert(airDrop: AirDrop): AirDrop {
        const copy: AirDrop = Object.assign({}, airDrop);

        copy.startDate = this.dateUtils.toDate(airDrop.startDate);

        copy.finishDate = this.dateUtils.toDate(airDrop.finishDate);
        return copy;
    }
}
