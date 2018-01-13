import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { CoinBlockChainInfo } from './coin-block-chain-info.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CoinBlockChainInfoService {

    private resourceUrl = SERVER_API_URL + 'api/coins';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/coins';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(coin: CoinBlockChainInfo): Observable<CoinBlockChainInfo> {
        const copy = this.convert(coin);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(coin: CoinBlockChainInfo): Observable<CoinBlockChainInfo> {
        const copy = this.convert(coin);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<CoinBlockChainInfo> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
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
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to CoinBlockChainInfo.
     */
    private convertItemFromServer(json: any): CoinBlockChainInfo {
        const entity: CoinBlockChainInfo = Object.assign(new CoinBlockChainInfo(), json);
        entity.releaseat = this.dateUtils
            .convertDateTimeFromServer(json.releaseat);
        entity.createdat = this.dateUtils
            .convertDateTimeFromServer(json.createdat);
        entity.updatedat = this.dateUtils
            .convertDateTimeFromServer(json.updatedat);
        return entity;
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
