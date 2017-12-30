import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BlockchainSharedModule } from '../../shared';
import {
    QuoteService,
    QuotePopupService,
    QuoteComponent,
    QuoteDetailComponent,
    QuoteDialogComponent,
    QuotePopupComponent,
    QuoteDeletePopupComponent,
    QuoteDeleteDialogComponent,
    quoteRoute,
    quotePopupRoute,
} from './';

const ENTITY_STATES = [
    ...quoteRoute,
    ...quotePopupRoute,
];

@NgModule({
    imports: [
        BlockchainSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        QuoteComponent,
        QuoteDetailComponent,
        QuoteDialogComponent,
        QuoteDeleteDialogComponent,
        QuotePopupComponent,
        QuoteDeletePopupComponent,
    ],
    entryComponents: [
        QuoteComponent,
        QuoteDialogComponent,
        QuotePopupComponent,
        QuoteDeleteDialogComponent,
        QuoteDeletePopupComponent,
    ],
    providers: [
        QuoteService,
        QuotePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlockchainQuoteModule {}
