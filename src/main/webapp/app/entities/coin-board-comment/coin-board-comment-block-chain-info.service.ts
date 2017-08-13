import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { CoinBoardCommentBlockChainInfo } from './coin-board-comment-block-chain-info.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CoinBoardCommentBlockChainInfoService {

    private resourceUrl = 'api/coin-board-comments';
    private resourceSearchUrl = 'api/_search/coin-board-comments';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(coinBoardComment: CoinBoardCommentBlockChainInfo): Observable<CoinBoardCommentBlockChainInfo> {
        const copy = this.convert(coinBoardComment);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(coinBoardComment: CoinBoardCommentBlockChainInfo): Observable<CoinBoardCommentBlockChainInfo> {
        const copy = this.convert(coinBoardComment);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<CoinBoardCommentBlockChainInfo> {
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

    private convert(coinBoardComment: CoinBoardCommentBlockChainInfo): CoinBoardCommentBlockChainInfo {
        const copy: CoinBoardCommentBlockChainInfo = Object.assign({}, coinBoardComment);

        copy.createdat = this.dateUtils.toDate(coinBoardComment.createdat);

        copy.updatedat = this.dateUtils.toDate(coinBoardComment.updatedat);
        return copy;
    }
}
