import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ResourceBlockChainInfo } from './resource-block-chain-info.model';
import { ResourceBlockChainInfoPopupService } from './resource-block-chain-info-popup.service';
import { ResourceBlockChainInfoService } from './resource-block-chain-info.service';

import { CoinBlockChainInfo, CoinBlockChainInfoService } from '../coin';

@Component({
    selector: 'jhi-resource-block-chain-info-dialog',
    templateUrl: './resource-block-chain-info-dialog.component.html'
})
export class ResourceBlockChainInfoDialogComponent implements OnInit {

    resource: ResourceBlockChainInfo;
    isSaving: boolean;

    coins: CoinBlockChainInfo[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private resourceService: ResourceBlockChainInfoService,
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
        if (this.resource.id !== undefined) {
            this.subscribeToSaveResponse(
                this.resourceService.update(this.resource));
        } else {
            this.subscribeToSaveResponse(
                this.resourceService.create(this.resource));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ResourceBlockChainInfo>>) {
        result.subscribe((res: HttpResponse<ResourceBlockChainInfo>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: ResourceBlockChainInfo) {
        this.eventManager.broadcast({ name: 'resourceListModification', content: 'OK'});
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
    selector: 'jhi-resource-block-chain-info-popup',
    template: ''
})
export class ResourceBlockChainInfoPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private resourcePopupService: ResourceBlockChainInfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.resourcePopupService
                    .open(ResourceBlockChainInfoDialogComponent as Component, params['id']);
            } else {
                this.resourcePopupService
                    .open(ResourceBlockChainInfoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
