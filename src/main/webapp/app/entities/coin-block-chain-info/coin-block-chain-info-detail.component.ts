import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { CoinBlockChainInfo } from './coin-block-chain-info.model';
import { CoinBlockChainInfoService } from './coin-block-chain-info.service';

@Component({
    selector: 'jhi-coin-block-chain-info-detail',
    templateUrl: './coin-block-chain-info-detail.component.html'
})
export class CoinBlockChainInfoDetailComponent implements OnInit, OnDestroy {

    coin: CoinBlockChainInfo;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private coinService: CoinBlockChainInfoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCoins();
    }

    load(id) {
        this.coinService.find(id).subscribe((coin) => {
            this.coin = coin;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCoins() {
        this.eventSubscriber = this.eventManager.subscribe(
            'coinListModification',
            (response) => this.load(this.coin.id)
        );
    }
}
