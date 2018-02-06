import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CoinBlockChainInfo } from './coin-block-chain-info.model';
import { CoinBlockChainInfoPopupService } from './coin-block-chain-info-popup.service';
import { CoinBlockChainInfoService } from './coin-block-chain-info.service';

@Component({
    selector: 'jhi-coin-block-chain-info-dialog',
    templateUrl: './coin-block-chain-info-dialog.component.html'
})
export class CoinBlockChainInfoDialogComponent implements OnInit {

    coin: CoinBlockChainInfo;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private coinService: CoinBlockChainInfoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.coin.id !== undefined) {
            this.subscribeToSaveResponse(
                this.coinService.update(this.coin));
        } else {
            this.subscribeToSaveResponse(
                this.coinService.create(this.coin));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<CoinBlockChainInfo>>) {
        result.subscribe((res: HttpResponse<CoinBlockChainInfo>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: CoinBlockChainInfo) {
        this.eventManager.broadcast({ name: 'coinListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-coin-block-chain-info-popup',
    template: ''
})
export class CoinBlockChainInfoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private coinPopupService: CoinBlockChainInfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.coinPopupService
                    .open(CoinBlockChainInfoDialogComponent as Component, params['id']);
            } else {
                this.coinPopupService
                    .open(CoinBlockChainInfoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
