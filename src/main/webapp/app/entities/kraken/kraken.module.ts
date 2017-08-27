import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BlockchainSharedModule } from '../../shared';
import {
    KrakenService,
    KrakenPopupService,
    KrakenComponent,
    KrakenDetailComponent,
    KrakenDialogComponent,
    KrakenPopupComponent,
    KrakenDeletePopupComponent,
    KrakenDeleteDialogComponent,
    krakenRoute,
    krakenPopupRoute,
} from './';

const ENTITY_STATES = [
    ...krakenRoute,
    ...krakenPopupRoute,
];

@NgModule({
    imports: [
        BlockchainSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        KrakenComponent,
        KrakenDetailComponent,
        KrakenDialogComponent,
        KrakenDeleteDialogComponent,
        KrakenPopupComponent,
        KrakenDeletePopupComponent,
    ],
    entryComponents: [
        KrakenComponent,
        KrakenDialogComponent,
        KrakenPopupComponent,
        KrakenDeleteDialogComponent,
        KrakenDeletePopupComponent,
    ],
    providers: [
        KrakenService,
        KrakenPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlockchainKrakenModule {}
