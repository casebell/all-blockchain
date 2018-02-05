import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CoinBlockChainInfo } from './coin-block-chain-info.model';
import { CoinBlockChainInfoService } from './coin-block-chain-info.service';
import { Principal } from '../../shared';

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
            this.coinService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: HttpResponse<CoinBlockChainInfo[]>) => this.coins = res.body,
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
       }
        this.coinService.query().subscribe(
            (res: HttpResponse<CoinBlockChainInfo[]>) => {
                this.coins = res.body;
                this.currentSearch = '';
            },
            (res: HttpErrorResponse) => this.onError(res.message)
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
        this.jhiAlertService.error(error.message, null, null);
    }
}
