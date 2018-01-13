import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BlockchainSharedModule } from '../../shared';
import {
    CoinisService,
    CoinisPopupService,
    CoinisComponent,
    CoinisDetailComponent,
    CoinisDialogComponent,
    CoinisPopupComponent,
    CoinisDeletePopupComponent,
    CoinisDeleteDialogComponent,
    coinisRoute,
    coinisPopupRoute,
} from './';

const ENTITY_STATES = [
    ...coinisRoute,
    ...coinisPopupRoute,
];

@NgModule({
    imports: [
        BlockchainSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CoinisComponent,
        CoinisDetailComponent,
        CoinisDialogComponent,
        CoinisDeleteDialogComponent,
        CoinisPopupComponent,
        CoinisDeletePopupComponent,
    ],
    entryComponents: [
        CoinisComponent,
        CoinisDialogComponent,
        CoinisPopupComponent,
        CoinisDeleteDialogComponent,
        CoinisDeletePopupComponent,
    ],
    providers: [
        CoinisService,
        CoinisPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlockchainCoinisModule {}
