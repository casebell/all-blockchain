import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { CoinBoardCommentBlockChainInfo } from './coin-board-comment-block-chain-info.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<CoinBoardCommentBlockChainInfo>;

@Injectable()
export class CoinBoardCommentBlockChainInfoService {

    private resourceUrl =  SERVER_API_URL + 'api/coin-board-comments';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/coin-board-comments';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(coinBoardComment: CoinBoardCommentBlockChainInfo): Observable<EntityResponseType> {
        const copy = this.convert(coinBoardComment);
        return this.http.post<CoinBoardCommentBlockChainInfo>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(coinBoardComment: CoinBoardCommentBlockChainInfo): Observable<EntityResponseType> {
        const copy = this.convert(coinBoardComment);
        return this.http.put<CoinBoardCommentBlockChainInfo>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<CoinBoardCommentBlockChainInfo>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<CoinBoardCommentBlockChainInfo[]>> {
        const options = createRequestOption(req);
        return this.http.get<CoinBoardCommentBlockChainInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<CoinBoardCommentBlockChainInfo[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<CoinBoardCommentBlockChainInfo[]>> {
        const options = createRequestOption(req);
        return this.http.get<CoinBoardCommentBlockChainInfo[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<CoinBoardCommentBlockChainInfo[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: CoinBoardCommentBlockChainInfo = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<CoinBoardCommentBlockChainInfo[]>): HttpResponse<CoinBoardCommentBlockChainInfo[]> {
        const jsonResponse: CoinBoardCommentBlockChainInfo[] = res.body;
        const body: CoinBoardCommentBlockChainInfo[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to CoinBoardCommentBlockChainInfo.
     */
    private convertItemFromServer(coinBoardComment: CoinBoardCommentBlockChainInfo): CoinBoardCommentBlockChainInfo {
        const copy: CoinBoardCommentBlockChainInfo = Object.assign({}, coinBoardComment);
        copy.createdat = this.dateUtils
            .convertDateTimeFromServer(coinBoardComment.createdat);
        copy.updatedat = this.dateUtils
            .convertDateTimeFromServer(coinBoardComment.updatedat);
        return copy;
    }

    /**
     * Convert a CoinBoardCommentBlockChainInfo to a JSON which can be sent to the server.
     */
    private convert(coinBoardComment: CoinBoardCommentBlockChainInfo): CoinBoardCommentBlockChainInfo {
        const copy: CoinBoardCommentBlockChainInfo = Object.assign({}, coinBoardComment);

        copy.createdat = this.dateUtils.toDate(coinBoardComment.createdat);

        copy.updatedat = this.dateUtils.toDate(coinBoardComment.updatedat);
        return copy;
    }
}
