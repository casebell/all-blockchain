import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Kraken } from './kraken.model';
import { KrakenPopupService } from './kraken-popup.service';
import { KrakenService } from './kraken.service';

@Component({
    selector: 'jhi-kraken-dialog',
    templateUrl: './kraken-dialog.component.html'
})
export class KrakenDialogComponent implements OnInit {

    kraken: Kraken;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private krakenService: KrakenService,
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
        if (this.kraken.id !== undefined) {
            this.subscribeToSaveResponse(
                this.krakenService.update(this.kraken));
        } else {
            this.subscribeToSaveResponse(
                this.krakenService.create(this.kraken));
        }
    }

    private subscribeToSaveResponse(result: Observable<Kraken>) {
        result.subscribe((res: Kraken) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Kraken) {
        this.eventManager.broadcast({ name: 'krakenListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-kraken-popup',
    template: ''
})
export class KrakenPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private krakenPopupService: KrakenPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.krakenPopupService
                    .open(KrakenDialogComponent as Component, params['id']);
            } else {
                this.krakenPopupService
                    .open(KrakenDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
