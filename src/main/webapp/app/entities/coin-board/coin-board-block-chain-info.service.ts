import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { CoinBoardBlockChainInfo } from './coin-board-block-chain-info.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CoinBoardBlockChainInfoService {

    private resourceUrl = 'api/coin-boards';
    private resourceSearchUrl = 'api/_search/coin-boards';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(coinBoard: CoinBoardBlockChainInfo): Observable<CoinBoardBlockChainInfo> {
        const copy = this.convert(coinBoard);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(coinBoard: CoinBoardBlockChainInfo): Observable<CoinBoardBlockChainInfo> {
        const copy = this.convert(coinBoard);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<CoinBoardBlockChainInfo> {
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
        entity.updatedat = this.dateUtils
            .convertDateTimeFromServer(entity.updatedat);
    }

    private convert(coinBoard: CoinBoardBlockChainInfo): CoinBoardBlockChainInfo {
        const copy: CoinBoardBlockChainInfo = Object.assign({}, coinBoard);

        copy.createdat = this.dateUtils.toDate(coinBoard.createdat);

        copy.updatedat = this.dateUtils.toDate(coinBoard.updatedat);
        return copy;
    }
}
