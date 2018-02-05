import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Bitfinex } from './bitfinex.model';
import { BitfinexPopupService } from './bitfinex-popup.service';
import { BitfinexService } from './bitfinex.service';
import { CoinBlockChainInfo, CoinBlockChainInfoService } from '../coin';

@Component({
    selector: 'jhi-bitfinex-dialog',
    templateUrl: './bitfinex-dialog.component.html'
})
export class BitfinexDialogComponent implements OnInit {

    bitfinex: Bitfinex;
    isSaving: boolean;

    coins: CoinBlockChainInfo[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private bitfinexService: BitfinexService,
        private coinService: CoinBlockChainInfoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.coinService.query()
            .subscribe((res: HttpResponse<CoinBlockChainInfo[]>) => { this.coins = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.bitfinex.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bitfinexService.update(this.bitfinex));
        } else {
            this.subscribeToSaveResponse(
                this.bitfinexService.create(this.bitfinex));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Bitfinex>>) {
        result.subscribe((res: HttpResponse<Bitfinex>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Bitfinex) {
        this.eventManager.broadcast({ name: 'bitfinexListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-bitfinex-popup',
    template: ''
})
export class BitfinexPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bitfinexPopupService: BitfinexPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.bitfinexPopupService
                    .open(BitfinexDialogComponent as Component, params['id']);
            } else {
                this.bitfinexPopupService
                    .open(BitfinexDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
