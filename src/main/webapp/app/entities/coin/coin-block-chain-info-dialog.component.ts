import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CoinBlockChainInfo } from './coin-block-chain-info.model';
import { CoinBlockChainInfoPopupService } from './coin-block-chain-info-popup.service';
import { CoinBlockChainInfoService } from './coin-block-chain-info.service';

@Component({
    selector: 'jhi-coin-block-chain-info-dialog',
    templateUrl: './coin-block-chain-info-dialog.component.html'
})
export class CoinBlockChainInfoDialogComponent implements OnInit {

    coin: CoinBlockChainInfo;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private coinService: CoinBlockChainInfoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
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

    private subscribeToSaveResponse(result: Observable<CoinBlockChainInfo>) {
        result.subscribe((res: CoinBlockChainInfo) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CoinBlockChainInfo) {
        this.eventManager.broadcast({ name: 'coinListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-coin-block-chain-info-popup',
    template: ''
})
export class CoinBlockChainInfoPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private coinPopupService: CoinBlockChainInfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.coinPopupService.open(CoinBlockChainInfoDialogComponent  as Component, params['id']);
            } else {
                this.modalRef = this.coinPopupService.open(CoinBlockChainInfoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
