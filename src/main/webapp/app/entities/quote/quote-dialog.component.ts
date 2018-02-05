import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Quote } from './quote.model';
import { QuotePopupService } from './quote-popup.service';
import { QuoteService } from './quote.service';
//import { MarketCoin, MarketCoinService } from '../market-coin';
import { MarketCoin } from '../../ticker/market-coin/market-coin.model';

@Component({
    selector: 'jhi-quote-dialog',
    templateUrl: './quote-dialog.component.html'
})
export class QuoteDialogComponent implements OnInit {

    quote: Quote;
    isSaving: boolean;

    marketcoins: MarketCoin[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private quoteService: QuoteService,
     //   private marketCoinService: MarketCoinService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.marketCoinService.query()
            .subscribe((res: HttpResponse<MarketCoin[]>) => { this.marketcoins = res.body; }, (res: HttpErrorResponse) => this.onError(res.message));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.quote.id !== undefined) {
            this.subscribeToSaveResponse(
                this.quoteService.update(this.quote));
        } else {
            this.subscribeToSaveResponse(
                this.quoteService.create(this.quote));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<Quote>>) {
        result.subscribe((res: HttpResponse<Quote>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: Quote) {
        this.eventManager.broadcast({ name: 'quoteListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

     trackMarketCoinById(index: number, item: MarketCoin) {
         return item.id;
     }
}

@Component({
    selector: 'jhi-quote-popup',
    template: ''
})
export class QuotePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private quotePopupService: QuotePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.quotePopupService
                    .open(QuoteDialogComponent as Component, params['id']);
            } else {
                this.quotePopupService
                    .open(QuoteDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
