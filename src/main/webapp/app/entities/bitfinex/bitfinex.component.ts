import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Bitfinex } from './bitfinex.model';
import { BitfinexService } from './bitfinex.service';
import { Principal } from '../../shared';

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
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.bitfinexService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: HttpResponse<Bitfinex[]>) => this.bitfinexes = res.body,
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
       }
        this.bitfinexService.query().subscribe(
            (res: HttpResponse<Bitfinex[]>) => {
                this.bitfinexes = res.body;
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
        this.alertService.error(error.message, null, null);
    }
}
