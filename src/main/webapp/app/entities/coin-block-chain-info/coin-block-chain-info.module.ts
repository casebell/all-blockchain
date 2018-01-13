import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BlockchainSharedModule } from '../../shared';
import {
    CoinBlockChainInfoService,
    CoinBlockChainInfoPopupService,
    CoinBlockChainInfoComponent,
    CoinBlockChainInfoDetailComponent,
    CoinBlockChainInfoDialogComponent,
    CoinBlockChainInfoPopupComponent,
    CoinBlockChainInfoDeletePopupComponent,
    CoinBlockChainInfoDeleteDialogComponent,
    coinRoute,
    coinPopupRoute,
} from './';

const ENTITY_STATES = [
    ...coinRoute,
    ...coinPopupRoute,
];

@NgModule({
    imports: [
        BlockchainSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        CoinBlockChainInfoComponent,
        CoinBlockChainInfoDetailComponent,
        CoinBlockChainInfoDialogComponent,
        CoinBlockChainInfoDeleteDialogComponent,
        CoinBlockChainInfoPopupComponent,
        CoinBlockChainInfoDeletePopupComponent,
    ],
    entryComponents: [
        CoinBlockChainInfoComponent,
        CoinBlockChainInfoDialogComponent,
        CoinBlockChainInfoPopupComponent,
        CoinBlockChainInfoDeleteDialogComponent,
        CoinBlockChainInfoDeletePopupComponent,
    ],
    providers: [
        CoinBlockChainInfoService,
        CoinBlockChainInfoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlockchainCoinBlockChainInfoModule {}
