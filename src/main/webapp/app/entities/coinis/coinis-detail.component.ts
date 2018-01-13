import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { Coinis } from './coinis.model';
import { CoinisService } from './coinis.service';

@Component({
    selector: 'jhi-coinis-detail',
    templateUrl: './coinis-detail.component.html'
})
export class CoinisDetailComponent implements OnInit, OnDestroy {

    coinis: Coinis;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private coinisService: CoinisService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCoinis();
    }

    load(id) {
        this.coinisService.find(id).subscribe((coinis) => {
            this.coinis = coinis;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCoinis() {
        this.eventSubscriber = this.eventManager.subscribe(
            'coinisListModification',
            (response) => this.load(this.coinis.id)
        );
    }
}
