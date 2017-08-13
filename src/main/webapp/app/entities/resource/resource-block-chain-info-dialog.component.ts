import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ResourceBlockChainInfo } from './resource-block-chain-info.model';
import { ResourceBlockChainInfoPopupService } from './resource-block-chain-info-popup.service';
import { ResourceBlockChainInfoService } from './resource-block-chain-info.service';
import { CoinBlockChainInfo, CoinBlockChainInfoService } from '../coin';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-resource-block-chain-info-dialog',
    templateUrl: './resource-block-chain-info-dialog.component.html'
})
export class ResourceBlockChainInfoDialogComponent implements OnInit {

    resource: ResourceBlockChainInfo;
    authorities: any[];
    isSaving: boolean;

    coins: CoinBlockChainInfo[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private resourceService: ResourceBlockChainInfoService,
        private coinService: CoinBlockChainInfoService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.coinService.query()
            .subscribe((res: ResponseWrapper) => { this.coins = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
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

    private subscribeToSaveResponse(result: Observable<ResourceBlockChainInfo>) {
        result.subscribe((res: ResourceBlockChainInfo) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ResourceBlockChainInfo) {
        this.eventManager.broadcast({ name: 'resourceListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-resource-block-chain-info-popup',
    template: ''
})
export class ResourceBlockChainInfoPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private resourcePopupService: ResourceBlockChainInfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.resourcePopupService
                    .open(ResourceBlockChainInfoDialogComponent as Component, params['id']);
            } else {
                this.modalRef = this.resourcePopupService
                    .open(ResourceBlockChainInfoDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
