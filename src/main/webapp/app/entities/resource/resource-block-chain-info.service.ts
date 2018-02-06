import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { ResourceBlockChainInfo } from './resource-block-chain-info.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<ResourceBlockChainInfo>;

@Injectable()
export class ResourceBlockChainInfoService {

    private resourceUrl =  SERVER_API_URL + 'api/resources';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/resources';

    constructor(private http: HttpClient, private dateUtils: JhiDateUtils) { }

    create(resource: ResourceBlockChainInfo): Observable<EntityResponseType> {
        const copy = this.convert(resource);
        return this.http.post<ResourceBlockChainInfo>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(resource: ResourceBlockChainInfo): Observable<EntityResponseType> {
        const copy = this.convert(resource);
        return this.http.put<ResourceBlockChainInfo>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ResourceBlockChainInfo>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<ResourceBlockChainInfo[]>> {
        const options = createRequestOption(req);
        return this.http.get<ResourceBlockChainInfo[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ResourceBlockChainInfo[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    search(req?: any): Observable<HttpResponse<ResourceBlockChainInfo[]>> {
        const options = createRequestOption(req);
        return this.http.get<ResourceBlockChainInfo[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<ResourceBlockChainInfo[]>) => this.convertArrayResponse(res));
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: ResourceBlockChainInfo = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<ResourceBlockChainInfo[]>): HttpResponse<ResourceBlockChainInfo[]> {
        const jsonResponse: ResourceBlockChainInfo[] = res.body;
        const body: ResourceBlockChainInfo[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to ResourceBlockChainInfo.
     */
    private convertItemFromServer(resource: ResourceBlockChainInfo): ResourceBlockChainInfo {
        const copy: ResourceBlockChainInfo = Object.assign({}, resource);
        copy.createdAt = this.dateUtils
            .convertDateTimeFromServer(resource.createdAt);
        copy.updatedAt = this.dateUtils
            .convertDateTimeFromServer(resource.updatedAt);
        return copy;
    }

    /**
     * Convert a ResourceBlockChainInfo to a JSON which can be sent to the server.
     */
    private convert(resource: ResourceBlockChainInfo): ResourceBlockChainInfo {
        const copy: ResourceBlockChainInfo = Object.assign({}, resource);

        copy.createdAt = this.dateUtils.toDate(resource.createdAt);

        copy.updatedAt = this.dateUtils.toDate(resource.updatedAt);
        return copy;
    }
}
