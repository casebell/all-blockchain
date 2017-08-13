import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BlockchainSharedModule } from '../../shared';
import { BlockchainAdminModule } from '../../admin/admin.module';
import {
    CoinBoardBlockChainInfoService,
    CoinBoardBlockChainInfoPopupService,
    CoinBoardBlockChainInfoComponent,
    CoinBoardBlockChainInfoDetailComponent,
    CoinBoardBlockChainInfoDialogComponent,
    CoinBoardBlockChainInfoPopupComponent,
    CoinBoardBlockChainInfoDeletePopupComponent,
    CoinBoardBlockChainInfoDeleteDialogComponent,
    coinBoardRoute,
    coinBoardPopupRoute,
} from './';

const ENTITY_STATES = [
    ...coinBoardRoute,
    ...coinBoardPopupRoute,
];

@NgModule({
    imports: [
        BlockchainSharedModule,
        BlockchainAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CoinBoardBlockChainInfoComponent,
        CoinBoardBlockChainInfoDetailComponent,
        CoinBoardBlockChainInfoDialogComponent,
        CoinBoardBlockChainInfoDeleteDialogComponent,
        CoinBoardBlockChainInfoPopupComponent,
        CoinBoardBlockChainInfoDeletePopupComponent,
    ],
    entryComponents: [
        CoinBoardBlockChainInfoComponent,
        CoinBoardBlockChainInfoDialogComponent,
        CoinBoardBlockChainInfoPopupComponent,
        CoinBoardBlockChainInfoDeleteDialogComponent,
        CoinBoardBlockChainInfoDeletePopupComponent,
    ],
    providers: [
        CoinBoardBlockChainInfoService,
        CoinBoardBlockChainInfoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlockchainCoinBoardBlockChainInfoModule {}
