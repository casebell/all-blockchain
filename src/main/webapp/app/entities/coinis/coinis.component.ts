import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Coinis } from './coinis.model';
import { CoinisService } from './coinis.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-coinis',
    templateUrl: './coinis.component.html'
})
export class CoinisComponent implements OnInit, OnDestroy {
coinis: Coinis[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private coinisService: CoinisService,
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
            this.coinisService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.coinis = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.coinisService.query().subscribe(
            (res: ResponseWrapper) => {
                this.coinis = res.json;
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
        this.registerChangeInCoinis();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Coinis) {
        return item.id;
    }
    registerChangeInCoinis() {
        this.eventSubscriber = this.eventManager.subscribe('coinisListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
