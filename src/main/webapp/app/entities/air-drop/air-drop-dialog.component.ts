import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AirDrop } from './air-drop.model';
import { AirDropPopupService } from './air-drop-popup.service';
import { AirDropService } from './air-drop.service';

@Component({
    selector: 'jhi-air-drop-dialog',
    templateUrl: './air-drop-dialog.component.html'
})
export class AirDropDialogComponent implements OnInit {

    airDrop: AirDrop;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private airDropService: AirDropService,
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
        if (this.airDrop.id !== undefined) {
            this.subscribeToSaveResponse(
                this.airDropService.update(this.airDrop));
        } else {
            this.subscribeToSaveResponse(
                this.airDropService.create(this.airDrop));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<AirDrop>>) {
        result.subscribe((res: HttpResponse<AirDrop>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: AirDrop) {
        this.eventManager.broadcast({ name: 'airDropListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-air-drop-popup',
    template: ''
})
export class AirDropPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private airDropPopupService: AirDropPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.airDropPopupService
                    .open(AirDropDialogComponent as Component, params['id']);
            } else {
                this.airDropPopupService
                    .open(AirDropDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
