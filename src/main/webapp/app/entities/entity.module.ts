import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { BlockchainCoinBlockChainInfoModule } from './coin-block-chain-info/coin-block-chain-info.module';
import { BlockchainCoinBoardBlockChainInfoModule } from './coin-board-block-chain-info/coin-board-block-chain-info.module';
import { BlockchainCoinBoardCommentBlockChainInfoModule } from './coin-board-comment-block-chain-info/coin-board-comment-block-chain-info.module';
import { BlockchainResourceBlockChainInfoModule } from './resource-block-chain-info/resource-block-chain-info.module';
import { BlockchainKrakenModule } from './kraken/kraken.module';
import { BlockchainCoinisModule } from './coinis/coinis.module';
import { BlockchainBitfinexModule } from './bitfinex/bitfinex.module';
import { BlockchainMarketModule } from './market/market.module';
import { BlockchainQuoteModule } from './quote/quote.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        BlockchainCoinBlockChainInfoModule,
        BlockchainCoinBoardBlockChainInfoModule,
        BlockchainCoinBoardCommentBlockChainInfoModule,
        BlockchainResourceBlockChainInfoModule,
        BlockchainKrakenModule,
        BlockchainCoinisModule,
        BlockchainBitfinexModule,
        BlockchainMarketModule,
        BlockchainQuoteModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlockchainEntityModule {}
