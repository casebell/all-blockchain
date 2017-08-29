import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BlockchainSharedModule } from '../../shared';
import {
    BitfinexService,
    BitfinexPopupService,
    BitfinexComponent,
    BitfinexDetailComponent,
    BitfinexDialogComponent,
    BitfinexPopupComponent,
    BitfinexDeletePopupComponent,
    BitfinexDeleteDialogComponent,
    bitfinexRoute,
    bitfinexPopupRoute,
} from './';

const ENTITY_STATES = [
    ...bitfinexRoute,
    ...bitfinexPopupRoute,
];

@NgModule({
    imports: [
        BlockchainSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BitfinexComponent,
        BitfinexDetailComponent,
        BitfinexDialogComponent,
        BitfinexDeleteDialogComponent,
        BitfinexPopupComponent,
        BitfinexDeletePopupComponent,
    ],
    entryComponents: [
        BitfinexComponent,
        BitfinexDialogComponent,
        BitfinexPopupComponent,
        BitfinexDeleteDialogComponent,
        BitfinexDeletePopupComponent,
    ],
    providers: [
        BitfinexService,
        BitfinexPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlockchainBitfinexModule {}
