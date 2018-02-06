import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { CoinBoardBlockChainInfo } from './coin-board-block-chain-info.model';
import { CoinBoardBlockChainInfoService } from './coin-board-block-chain-info.service';

@Component({
    selector: 'jhi-coin-board-block-chain-info-detail',
    templateUrl: './coin-board-block-chain-info-detail.component.html'
})
export class CoinBoardBlockChainInfoDetailComponent implements OnInit, OnDestroy {

    coinBoard: CoinBoardBlockChainInfo;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private coinBoardService: CoinBoardBlockChainInfoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCoinBoards();
    }

    load(id) {
        this.coinBoardService.find(id)
            .subscribe((coinBoardResponse: HttpResponse<CoinBoardBlockChainInfo>) => {
                this.coinBoard = coinBoardResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCoinBoards() {
        this.eventSubscriber = this.eventManager.subscribe(
            'coinBoardListModification',
            (response) => this.load(this.coinBoard.id)
        );
    }
}
