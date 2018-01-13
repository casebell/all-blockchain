import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Bitfinex } from './bitfinex.model';
import { BitfinexService } from './bitfinex.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-bitfinex',
    templateUrl: './bitfinex.component.html'
})
export class BitfinexComponent implements OnInit, OnDestroy {
bitfinexes: Bitfinex[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private bitfinexService: BitfinexService,
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
            this.bitfinexService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.bitfinexes = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.bitfinexService.query().subscribe(
            (res: ResponseWrapper) => {
                this.bitfinexes = res.json;
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
        this.registerChangeInBitfinexes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Bitfinex) {
        return item.id;
    }
    registerChangeInBitfinexes() {
        this.eventSubscriber = this.eventManager.subscribe('bitfinexListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
