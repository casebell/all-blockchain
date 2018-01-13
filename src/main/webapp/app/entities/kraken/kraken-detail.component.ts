import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Kraken } from './kraken.model';
import { KrakenService } from './kraken.service';

@Component({
    selector: 'jhi-kraken-detail',
    templateUrl: './kraken-detail.component.html'
})
export class KrakenDetailComponent implements OnInit, OnDestroy {

    kraken: Kraken;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private krakenService: KrakenService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInKrakens();
    }

    load(id) {
        this.krakenService.find(id).subscribe((kraken) => {
            this.kraken = kraken;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInKrakens() {
        this.eventSubscriber = this.eventManager.subscribe(
            'krakenListModification',
            (response) => this.load(this.kraken.id)
        );
    }
}
