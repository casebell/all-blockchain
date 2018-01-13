import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Bitfinex } from './bitfinex.model';
import { BitfinexPopupService } from './bitfinex-popup.service';
import { BitfinexService } from './bitfinex.service';

@Component({
    selector: 'jhi-bitfinex-delete-dialog',
    templateUrl: './bitfinex-delete-dialog.component.html'
})
export class BitfinexDeleteDialogComponent {

    bitfinex: Bitfinex;

    constructor(
        private bitfinexService: BitfinexService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bitfinexService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bitfinexListModification',
                content: 'Deleted an bitfinex'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bitfinex-delete-popup',
    template: ''
})
export class BitfinexDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bitfinexPopupService: BitfinexPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.bitfinexPopupService
                .open(BitfinexDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
