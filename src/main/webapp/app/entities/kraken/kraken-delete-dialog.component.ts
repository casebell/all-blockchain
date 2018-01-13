import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Kraken } from './kraken.model';
import { KrakenPopupService } from './kraken-popup.service';
import { KrakenService } from './kraken.service';

@Component({
    selector: 'jhi-kraken-delete-dialog',
    templateUrl: './kraken-delete-dialog.component.html'
})
export class KrakenDeleteDialogComponent {

    kraken: Kraken;

    constructor(
        private krakenService: KrakenService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.krakenService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'krakenListModification',
                content: 'Deleted an kraken'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-kraken-delete-popup',
    template: ''
})
export class KrakenDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private krakenPopupService: KrakenPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.krakenPopupService
                .open(KrakenDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
