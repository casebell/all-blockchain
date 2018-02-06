import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Market } from './market.model';
import { MarketService } from './market.service';

@Component({
    selector: 'jhi-market-detail',
    templateUrl: './market-detail.component.html'
})
export class MarketDetailComponent implements OnInit, OnDestroy {

    market: Market;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private marketService: MarketService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMarkets();
    }

    load(id) {
        this.marketService.find(id)
            .subscribe((marketResponse: HttpResponse<Market>) => {
                this.market = marketResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMarkets() {
        this.eventSubscriber = this.eventManager.subscribe(
            'marketListModification',
            (response) => this.load(this.market.id)
        );
    }
}
