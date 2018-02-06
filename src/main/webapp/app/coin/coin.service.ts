import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';
import { createRequestOption } from '../shared';
import { HttpClient, HttpResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../app.constants';
import { Coin } from '../ticker/coin.model';

export type EntityResponseType = HttpResponse<Coin>;


@Injectable()
export class CoinService {

     sideNavCloseButtonChanged: Subject<string> = new Subject();
     sideNavOpenButtonChanged: Subject<string> = new Subject();
    private resourceUrl = SERVER_API_URL + 'api/coins';

    constructor(private http: HttpClient) {
    }

    sendSideNavCloseButton(message) {
        this.sideNavCloseButtonChanged.next(message);
    }

    sendSideNavOpenButton(message) {
        this.sideNavOpenButtonChanged.next(message);
    }


    query(req?: any): Observable<HttpResponse<Coin[]>> {
        const options = createRequestOption(req);
        return this.http.get<Coin[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<Coin[]>) => this.convertArrayResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<Coin>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }


    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: Coin = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to Coin.
     */
    private convertItemFromServer(coin: Coin): Coin {
        const copy: Coin = Object.assign({}, coin);
        return copy;
    }
    private convertArrayResponse(res: HttpResponse<Coin[]>): HttpResponse<Coin[]> {
        const jsonResponse: Coin[] = res.body;
        const body: Coin[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }


    /*   getSidenavOpenButton(): Observable<any> {
           return this.sideNavOpenButtonChanged.asObservable();
       }
       getSidenavCloseButton(): Observable<any> {
           return this.sideNavCloseButtonChanged.asObservable();
       }*/

}
