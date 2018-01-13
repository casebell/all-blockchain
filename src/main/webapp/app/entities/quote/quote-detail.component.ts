import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Quote } from './quote.model';
import { QuoteService } from './quote.service';

@Component({
    selector: 'jhi-quote-detail',
    templateUrl: './quote-detail.component.html'
})
export class QuoteDetailComponent implements OnInit, OnDestroy {

    quote: Quote;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private quoteService: QuoteService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInQuotes();
    }

    load(id) {
        this.quoteService.find(id).subscribe((quote) => {
            this.quote = quote;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInQuotes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'quoteListModification',
            (response) => this.load(this.quote.id)
        );
    }
}
