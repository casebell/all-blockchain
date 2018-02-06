import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { CoinBoardCommentBlockChainInfo } from './coin-board-comment-block-chain-info.model';
import { CoinBoardCommentBlockChainInfoService } from './coin-board-comment-block-chain-info.service';

@Component({
    selector: 'jhi-coin-board-comment-block-chain-info-detail',
    templateUrl: './coin-board-comment-block-chain-info-detail.component.html'
})
export class CoinBoardCommentBlockChainInfoDetailComponent implements OnInit, OnDestroy {

    coinBoardComment: CoinBoardCommentBlockChainInfo;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private coinBoardCommentService: CoinBoardCommentBlockChainInfoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCoinBoardComments();
    }

    load(id) {
        this.coinBoardCommentService.find(id)
            .subscribe((coinBoardCommentResponse: HttpResponse<CoinBoardCommentBlockChainInfo>) => {
                this.coinBoardComment = coinBoardCommentResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCoinBoardComments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'coinBoardCommentListModification',
            (response) => this.load(this.coinBoardComment.id)
        );
    }
}
