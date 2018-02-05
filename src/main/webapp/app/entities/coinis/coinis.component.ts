import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Coinis } from './coinis.model';
import { CoinisService } from './coinis.service';
import { Principal } from '../../shared';

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
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.coinisService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: HttpResponse<Coinis[]>) => this.coinis = res.body,
                    (res: HttpErrorResponse) => this.onError(res.message)
                );
            return;
       }
        this.coinisService.query().subscribe(
            (res: HttpResponse<Coinis[]>) => {
                this.coinis = res.body;
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
        this.alertService.error(error.message, null, null);
    }
}
