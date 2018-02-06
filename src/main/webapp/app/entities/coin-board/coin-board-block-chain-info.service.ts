import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { CoinBoardBlockChainInfo } from './coin-board-block-chain-info.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<CoinBoardBlockChainInfo>;

@Injectable()
export class CoinBoardBlockChainInfoService {

    private resourceUrl =  SERVER_API_URL + 'api/coin-boards';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/coin-boards';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(coinBoard: CoinBoardBlockChainInfo): Observable<EntityResponseType> {
        const copy = this.convert(coinBoard);
        return this.http.post<CoinBoardBlockChainInfo>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(coinBoard: CoinBoardBlockChainInfo): Observable<EntityResponseType> {
        const copy = this.convert(coinBoard);
        return this.http.put<CoinBoardBlockChainInfo>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<CoinBoardBlockChainInfo>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<CoinBoardBlockChainInfo[]>> {
        const options = createRequestOption(req);
        return this.http.get<CoinBoardBlockChainInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<CoinBoardBlockChainInfo[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<CoinBoardBlockChainInfo[]>> {
        const options = createRequestOption(req);
        return this.http.get<CoinBoardBlockChainInfo[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<CoinBoardBlockChainInfo[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: CoinBoardBlockChainInfo = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<CoinBoardBlockChainInfo[]>): HttpResponse<CoinBoardBlockChainInfo[]> {
        const jsonResponse: CoinBoardBlockChainInfo[] = res.body;
        const body: CoinBoardBlockChainInfo[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to CoinBoardBlockChainInfo.
     */
    private convertItemFromServer(coinBoard: CoinBoardBlockChainInfo): CoinBoardBlockChainInfo {
        const copy: CoinBoardBlockChainInfo = Object.assign({}, coinBoard);
        copy.createdat = this.dateUtils
            .convertDateTimeFromServer(coinBoard.createdat);
        copy.updatedat = this.dateUtils
            .convertDateTimeFromServer(coinBoard.updatedat);
        return copy;
    }

    /**
     * Convert a CoinBoardBlockChainInfo to a JSON which can be sent to the server.
     */
    private convert(coinBoard: CoinBoardBlockChainInfo): CoinBoardBlockChainInfo {
        const copy: CoinBoardBlockChainInfo = Object.assign({}, coinBoard);

        copy.createdat = this.dateUtils.toDate(coinBoard.createdat);

        copy.updatedat = this.dateUtils.toDate(coinBoard.updatedat);
        return copy;
    }
}
