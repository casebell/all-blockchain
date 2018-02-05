import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CoinBoardBlockChainInfo } from './coin-board-block-chain-info.model';
import { CoinBoardBlockChainInfoPopupService } from './coin-board-block-chain-info-popup.service';
import { CoinBoardBlockChainInfoService } from './coin-board-block-chain-info.service';
//import { CoinBlockChainInfo, CoinBlockChainInfoService } from '../coin-block-chain-info';
import { User, UserService } from '../../shared';
import { CoinBlockChainInfo, CoinBlockChainInfoService } from '../coin';

@Component({
    selector: 'jhi-coin-board-block-chain-info-dialog',
    templateUrl: './coin-board-block-chain-info-dialog.component.html'
})
export class CoinBoardBlockChainInfoDialogComponent implements OnInit {

    coinBoard: CoinBoardBlockChainInfo;
    isSaving: boolean;

    coins: CoinBlockChainInfo[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private coinBoardService: CoinBoardBlockChainInfoService,
        private coinService: CoinBlockChainInfoService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.coinService.query()
            .subscribe((res: HttpResponse<CoinBlockChainInfo[]>) => { this.coins = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
        this.userService.query()
            .subscribe((res: HttpResponse<User[]>) => { this.users = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
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

    private subscribeToSaveResponse(result: Observable<HttpResponse<CoinBoardBlockChainInfo>>) {
        result.subscribe((res: HttpResponse<CoinBoardBlockChainInfo>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: CoinBoardBlockChainInfo) {
        this.eventManager.broadcast({ name: 'coinBoardListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
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

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private coinBoardPopupService: CoinBoardBlockChainInfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.coinBoardPopupService
                    .open(CoinBoardBlockChainInfoDialogComponent as Component, params['id']);
            } else {
                this.coinBoardPopupService
                    .open(CoinBoardBlockChainInfoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
