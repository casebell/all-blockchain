import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Coinis } from './coinis.model';
import { CoinisPopupService } from './coinis-popup.service';
import { CoinisService } from './coinis.service';

@Component({
    selector: 'jhi-coinis-delete-dialog',
    templateUrl: './coinis-delete-dialog.component.html'
})
export class CoinisDeleteDialogComponent {

    coinis: Coinis;

    constructor(
        private coinisService: CoinisService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.coinisService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'coinisListModification',
                content: 'Deleted an coinis'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-coinis-delete-popup',
    template: ''
})
export class CoinisDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private coinisPopupService: CoinisPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.coinisPopupService
                .open(CoinisDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
