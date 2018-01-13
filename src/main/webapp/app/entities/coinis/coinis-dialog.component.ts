import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Coinis } from './coinis.model';
import { CoinisPopupService } from './coinis-popup.service';
import { CoinisService } from './coinis.service';

@Component({
    selector: 'jhi-coinis-dialog',
    templateUrl: './coinis-dialog.component.html'
})
export class CoinisDialogComponent implements OnInit {

    coinis: Coinis;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private coinisService: CoinisService,
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
        if (this.coinis.id !== undefined) {
            this.subscribeToSaveResponse(
                this.coinisService.update(this.coinis));
        } else {
            this.subscribeToSaveResponse(
                this.coinisService.create(this.coinis));
        }
    }

    private subscribeToSaveResponse(result: Observable<Coinis>) {
        result.subscribe((res: Coinis) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Coinis) {
        this.eventManager.broadcast({ name: 'coinisListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-coinis-popup',
    template: ''
})
export class CoinisPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private coinisPopupService: CoinisPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.coinisPopupService
                    .open(CoinisDialogComponent as Component, params['id']);
            } else {
                this.coinisPopupService
                    .open(CoinisDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
