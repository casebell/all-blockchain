import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { ResourceBlockChainInfo } from './resource-block-chain-info.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ResourceBlockChainInfoService {

    private resourceUrl = 'api/resources';
    private resourceSearchUrl = 'api/_search/resources';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(resource: ResourceBlockChainInfo): Observable<ResourceBlockChainInfo> {
        const copy = this.convert(resource);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(resource: ResourceBlockChainInfo): Observable<ResourceBlockChainInfo> {
        const copy = this.convert(resource);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<ResourceBlockChainInfo> {
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
        entity.createdAt = this.dateUtils
            .convertDateTimeFromServer(entity.createdAt);
        entity.updatedAt = this.dateUtils
            .convertDateTimeFromServer(entity.updatedAt);
    }

    private convert(resource: ResourceBlockChainInfo): ResourceBlockChainInfo {
        const copy: ResourceBlockChainInfo = Object.assign({}, resource);

        copy.createdAt = this.dateUtils.toDate(resource.createdAt);

        copy.updatedAt = this.dateUtils.toDate(resource.updatedAt);
        return copy;
    }
}
