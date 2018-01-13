import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Quote } from './quote.model';
import { QuotePopupService } from './quote-popup.service';
import { QuoteService } from './quote.service';

@Component({
    selector: 'jhi-quote-delete-dialog',
    templateUrl: './quote-delete-dialog.component.html'
})
export class QuoteDeleteDialogComponent {

    quote: Quote;

    constructor(
        private quoteService: QuoteService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.quoteService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'quoteListModification',
                content: 'Deleted an quote'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-quote-delete-popup',
    template: ''
})
export class QuoteDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private quotePopupService: QuotePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.quotePopupService
                .open(QuoteDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
