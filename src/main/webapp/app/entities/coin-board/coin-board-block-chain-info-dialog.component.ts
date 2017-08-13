import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CoinBoardBlockChainInfo } from './coin-board-block-chain-info.model';
import { CoinBoardBlockChainInfoPopupService } from './coin-board-block-chain-info-popup.service';
import { CoinBoardBlockChainInfoService } from './coin-board-block-chain-info.service';
import { CoinBlockChainInfo, CoinBlockChainInfoService } from '../coin';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-coin-board-block-chain-info-dialog',
    templateUrl: './coin-board-block-chain-info-dialog.component.html'
})
export class CoinBoardBlockChainInfoDialogComponent implements OnInit {

    coinBoard: CoinBoardBlockChainInfo;
    authorities: any[];
    isSaving: boolean;

    coins: CoinBlockChainInfo[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private coinBoardService: CoinBoardBlockChainInfoService,
        private coinService: CoinBlockChainInfoService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.coinService.query()
            .subscribe((res: ResponseWrapper) => { this.coins = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.coinBoard.id !== undefined) {
            this.subscribeToSaveResponse(
                this.coinBoardService.update(this.coinBoard));
        } else {
            this.subscribeToSaveResponse(
                this.coinBoardService.create(this.coinBoard));
        }
    }

    private subscribeToSaveResponse(result: Observable<CoinBoardBlockChainInfo>) {
        result.subscribe((res: CoinBoardBlockChainInfo) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CoinBoardBlockChainInfo) {
        this.eventManager.broadcast({ name: 'coinBoardListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    trackCoinById(index: number, item: CoinBlockChainInfo) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-coin-board-block-chain-info-popup',
    template: ''
})
export class CoinBoardBlockChainInfoPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private coinBoardPopupService: CoinBoardBlockChainInfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.coinBoardPopupService.open(CoinBoardBlockChainInfoDialogComponent as Component, params['id']);
            } else {
                this.modalRef = this.coinBoardPopupService
                    .open(CoinBoardBlockChainInfoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
