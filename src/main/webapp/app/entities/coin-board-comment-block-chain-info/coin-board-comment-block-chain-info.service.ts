import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { CoinBoardCommentBlockChainInfo } from './coin-board-comment-block-chain-info.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CoinBoardCommentBlockChainInfoService {

    private resourceUrl = SERVER_API_URL + 'api/coin-board-comments';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/coin-board-comments';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(coinBoardComment: CoinBoardCommentBlockChainInfo): Observable<CoinBoardCommentBlockChainInfo> {
        const copy = this.convert(coinBoardComment);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(coinBoardComment: CoinBoardCommentBlockChainInfo): Observable<CoinBoardCommentBlockChainInfo> {
        const copy = this.convert(coinBoardComment);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<CoinBoardCommentBlockChainInfo> {
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
     * Convert a returned JSON object to CoinBoardCommentBlockChainInfo.
     */
    private convertItemFromServer(json: any): CoinBoardCommentBlockChainInfo {
        const entity: CoinBoardCommentBlockChainInfo = Object.assign(new CoinBoardCommentBlockChainInfo(), json);
        entity.createdat = this.dateUtils
            .convertDateTimeFromServer(json.createdat);
        entity.updatedat = this.dateUtils
            .convertDateTimeFromServer(json.updatedat);
        return entity;
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
