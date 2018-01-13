import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CoinBoardCommentBlockChainInfo } from './coin-board-comment-block-chain-info.model';
import { CoinBoardCommentBlockChainInfoPopupService } from './coin-board-comment-block-chain-info-popup.service';
import { CoinBoardCommentBlockChainInfoService } from './coin-board-comment-block-chain-info.service';
import { CoinBoardBlockChainInfo, CoinBoardBlockChainInfoService } from '../coin-board-block-chain-info';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-coin-board-comment-block-chain-info-dialog',
    templateUrl: './coin-board-comment-block-chain-info-dialog.component.html'
})
export class CoinBoardCommentBlockChainInfoDialogComponent implements OnInit {

    coinBoardComment: CoinBoardCommentBlockChainInfo;
    isSaving: boolean;

    coinboards: CoinBoardBlockChainInfo[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private coinBoardCommentService: CoinBoardCommentBlockChainInfoService,
        private coinBoardService: CoinBoardBlockChainInfoService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.coinBoardService.query()
            .subscribe((res: ResponseWrapper) => { this.coinboards = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.coinBoardComment.id !== undefined) {
            this.subscribeToSaveResponse(
                this.coinBoardCommentService.update(this.coinBoardComment));
        } else {
            this.subscribeToSaveResponse(
                this.coinBoardCommentService.create(this.coinBoardComment));
        }
    }

    private subscribeToSaveResponse(result: Observable<CoinBoardCommentBlockChainInfo>) {
        result.subscribe((res: CoinBoardCommentBlockChainInfo) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CoinBoardCommentBlockChainInfo) {
        this.eventManager.broadcast({ name: 'coinBoardCommentListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCoinBoardById(index: number, item: CoinBoardBlockChainInfo) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-coin-board-comment-block-chain-info-popup',
    template: ''
})
export class CoinBoardCommentBlockChainInfoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private coinBoardCommentPopupService: CoinBoardCommentBlockChainInfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.coinBoardCommentPopupService
                    .open(CoinBoardCommentBlockChainInfoDialogComponent as Component, params['id']);
            } else {
                this.coinBoardCommentPopupService
                    .open(CoinBoardCommentBlockChainInfoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
