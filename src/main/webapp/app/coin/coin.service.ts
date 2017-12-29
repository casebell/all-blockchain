import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';
import { createRequestOption, ResponseWrapper } from '../shared';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../app.constants';
import { Coin } from '../ticker/coin.model';

@Injectable()
export class CoinService {

     sideNavCloseButtonChanged: Subject<string> = new Subject();
     sideNavOpenButtonChanged: Subject<string> = new Subject();
    private resourceUrl = SERVER_API_URL + 'api/coins';

    constructor(private http: Http) {
    }

    sendSideNavCloseButton(message) {
        this.sideNavCloseButtonChanged.next(message);
    }

    sendSideNavOpenButton(message) {
        this.sideNavOpenButtonChanged.next(message);
    }


    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    find(id: number): Observable<Coin> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
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
     * Convert a returned JSON object to Coin.
     */
    private convertItemFromServer(json: any): Coin {
        const entity: Coin = Object.assign(new Coin(), json);
        return entity;
    }



    /*   getSidenavOpenButton(): Observable<any> {
           return this.sideNavOpenButtonChanged.asObservable();
       }
       getSidenavCloseButton(): Observable<any> {
           return this.sideNavCloseButtonChanged.asObservable();
       }*/

}
