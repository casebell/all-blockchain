import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { CoinBlockChainInfo } from './coin-block-chain-info.model';
import { CoinBlockChainInfoService } from './coin-block-chain-info.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-coin-block-chain-info',
    templateUrl: './coin-block-chain-info.component.html'
})
export class CoinBlockChainInfoComponent implements OnInit, OnDestroy {
coins: CoinBlockChainInfo[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private coinService: CoinBlockChainInfoService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.coinService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.coins = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.coinService.query().subscribe(
            (res: ResponseWrapper) => {
                this.coins = res.json;
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
        this.registerChangeInCoins();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: CoinBlockChainInfo) {
        return item.id;
    }
    registerChangeInCoins() {
        this.eventSubscriber = this.eventManager.subscribe('coinListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
