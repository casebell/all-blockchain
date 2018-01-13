import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ResourceBlockChainInfo } from './resource-block-chain-info.model';
import { ResourceBlockChainInfoService } from './resource-block-chain-info.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-resource-block-chain-info',
    templateUrl: './resource-block-chain-info.component.html'
})
export class ResourceBlockChainInfoComponent implements OnInit, OnDestroy {
resources: ResourceBlockChainInfo[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private resourceService: ResourceBlockChainInfoService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search'] ?
            this.activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.resourceService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.resources = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.resourceService.query().subscribe(
            (res: ResponseWrapper) => {
                this.resources = res.json;
                this.currentSearch = '';
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInResources();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ResourceBlockChainInfo) {
        return item.id;
    }
    registerChangeInResources() {
        this.eventSubscriber = this.eventManager.subscribe('resourceListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
