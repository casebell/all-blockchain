import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { CoinBlockChainInfo } from './coin-block-chain-info.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<CoinBlockChainInfo>;

@Injectable()
export class CoinBlockChainInfoService {

    private resourceUrl =  SERVER_API_URL + 'api/coins';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/coins';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(coin: CoinBlockChainInfo): Observable<EntityResponseType> {
        const copy = this.convert(coin);
        return this.http.post<CoinBlockChainInfo>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(coin: CoinBlockChainInfo): Observable<EntityResponseType> {
        const copy = this.convert(coin);
        return this.http.put<CoinBlockChainInfo>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<CoinBlockChainInfo>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<CoinBlockChainInfo[]>> {
        const options = createRequestOption(req);
        return this.http.get<CoinBlockChainInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<CoinBlockChainInfo[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<CoinBlockChainInfo[]>> {
        const options = createRequestOption(req);
        return this.http.get<CoinBlockChainInfo[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<CoinBlockChainInfo[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: CoinBlockChainInfo = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<CoinBlockChainInfo[]>): HttpResponse<CoinBlockChainInfo[]> {
        const jsonResponse: CoinBlockChainInfo[] = res.body;
        const body: CoinBlockChainInfo[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to CoinBlockChainInfo.
     */
    private convertItemFromServer(coin: CoinBlockChainInfo): CoinBlockChainInfo {
        const copy: CoinBlockChainInfo = Object.assign({}, coin);
        copy.releaseat = this.dateUtils
            .convertDateTimeFromServer(coin.releaseat);
        copy.createdat = this.dateUtils
            .convertDateTimeFromServer(coin.createdat);
        copy.updatedat = this.dateUtils
            .convertDateTimeFromServer(coin.updatedat);
        return copy;
    }

    /**
     * Convert a CoinBlockChainInfo to a JSON which can be sent to the server.
     */
    private convert(coin: CoinBlockChainInfo): CoinBlockChainInfo {
        const copy: CoinBlockChainInfo = Object.assign({}, coin);

        copy.releaseat = this.dateUtils.toDate(coin.releaseat);

        copy.createdat = this.dateUtils.toDate(coin.createdat);

        copy.updatedat = this.dateUtils.toDate(coin.updatedat);
        return copy;
    }
}
