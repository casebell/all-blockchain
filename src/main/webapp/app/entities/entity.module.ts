import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BlockchainCoinBoardBlockChainInfoModule } from './coin-board/coin-board-block-chain-info.module';
import { BlockchainCoinBoardCommentBlockChainInfoModule } from './coin-board-comment/coin-board-comment-block-chain-info.module';
import { BlockchainCoinBlockChainInfoModule } from './coin/coin-block-chain-info.module';
import { BlockchainResourceBlockChainInfoModule } from './resource/resource-block-chain-info.module';

import { BlockchainBitfinexModule } from './bitfinex/bitfinex.module';
import { BlockchainMarketModule } from './market/market.module';
import { BlockchainQuoteModule } from './quote/quote.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        BlockchainBitfinexModule,
        BlockchainMarketModule,
        BlockchainQuoteModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
        BlockchainCoinBlockChainInfoModule,
        BlockchainCoinBoardCommentBlockChainInfoModule,
        BlockchainCoinBoardBlockChainInfoModule,
        BlockchainResourceBlockChainInfoModule
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlockchainEntityModule {}
