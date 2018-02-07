import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AirDrop } from './air-drop.model';
import { AirDropPopupService } from './air-drop-popup.service';
import { AirDropService } from './air-drop.service';

@Component({
    selector: 'jhi-air-drop-delete-dialog',
    templateUrl: './air-drop-delete-dialog.component.html'
})
export class AirDropDeleteDialogComponent {

    airDrop: AirDrop;

    constructor(
        private airDropService: AirDropService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.airDropService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'airDropListModification',
                content: 'Deleted an airDrop'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-air-drop-delete-popup',
    template: ''
})
export class AirDropDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private airDropPopupService: AirDropPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.airDropPopupService
                .open(AirDropDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
