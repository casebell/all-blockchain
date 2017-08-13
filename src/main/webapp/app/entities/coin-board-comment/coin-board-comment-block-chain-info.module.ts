import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BlockchainSharedModule } from '../../shared';
import { BlockchainAdminModule } from '../../admin/admin.module';
import {
    CoinBoardCommentBlockChainInfoService,
    CoinBoardCommentBlockChainInfoPopupService,
    CoinBoardCommentBlockChainInfoComponent,
    CoinBoardCommentBlockChainInfoDetailComponent,
    CoinBoardCommentBlockChainInfoDialogComponent,
    CoinBoardCommentBlockChainInfoPopupComponent,
    CoinBoardCommentBlockChainInfoDeletePopupComponent,
    CoinBoardCommentBlockChainInfoDeleteDialogComponent,
    coinBoardCommentRoute,
    coinBoardCommentPopupRoute,
} from './';

const ENTITY_STATES = [
    ...coinBoardCommentRoute,
    ...coinBoardCommentPopupRoute,
];

@NgModule({
    imports: [
        BlockchainSharedModule,
        BlockchainAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CoinBoardCommentBlockChainInfoComponent,
        CoinBoardCommentBlockChainInfoDetailComponent,
        CoinBoardCommentBlockChainInfoDialogComponent,
        CoinBoardCommentBlockChainInfoDeleteDialogComponent,
        CoinBoardCommentBlockChainInfoPopupComponent,
        CoinBoardCommentBlockChainInfoDeletePopupComponent,
    ],
    entryComponents: [
        CoinBoardCommentBlockChainInfoComponent,
        CoinBoardCommentBlockChainInfoDialogComponent,
        CoinBoardCommentBlockChainInfoPopupComponent,
        CoinBoardCommentBlockChainInfoDeleteDialogComponent,
        CoinBoardCommentBlockChainInfoDeletePopupComponent,
    ],
    providers: [
        CoinBoardCommentBlockChainInfoService,
        CoinBoardCommentBlockChainInfoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlockchainCoinBoardCommentBlockChainInfoModule {}
