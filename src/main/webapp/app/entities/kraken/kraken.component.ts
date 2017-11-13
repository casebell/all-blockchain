import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Kraken } from './kraken.model';
import { KrakenService } from './kraken.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-kraken',
    templateUrl: './kraken.component.html'
})
export class KrakenComponent implements OnInit, OnDestroy {
krakens: Kraken[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private krakenService: KrakenService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.krakenService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.krakens = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.krakenService.query().subscribe(
            (res: ResponseWrapper) => {
                this.krakens = res.json;
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
        this.registerChangeInKrakens();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Kraken) {
        return item.id;
    }
    registerChangeInKrakens() {
        this.eventSubscriber = this.eventManager.subscribe('krakenListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}
