import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Bitfinex } from './bitfinex.model';
import { BitfinexService } from './bitfinex.service';

@Component({
    selector: 'jhi-bitfinex-detail',
    templateUrl: './bitfinex-detail.component.html'
})
export class BitfinexDetailComponent implements OnInit, OnDestroy {

    bitfinex: Bitfinex;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private bitfinexService: BitfinexService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInBitfinexes();
    }

    load(id) {
        this.bitfinexService.find(id).subscribe((bitfinex) => {
            this.bitfinex = bitfinex;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInBitfinexes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'bitfinexListModification',
            (response) => this.load(this.bitfinex.id)
        );
    }
}
