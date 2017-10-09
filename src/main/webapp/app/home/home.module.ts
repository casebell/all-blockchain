import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BlockchainSharedModule } from '../shared';
import { HOME_ROUTE, HomeComponent } from './';
import { CoinPriceComponent } from './coin-price/coin-price.component';
import { CoinPriceService } from './coin-price/coin-price.service';
import { MatGridListModule, MatIconModule, MatSelectModule, MatTabsModule } from '@angular/material';
import { CoinPriceRowComponent } from './coin-price/coin-price-row/coin-price-row.component';
import { ExchangeRateService } from './coin-price/coin-price-row/exchange-rate.service';
import { ExchangeRateComponent } from './exchange-rate/exchange-rate.component';
import { BitfinexWebsocketService } from './coin-price/bitfinex-websocket.service';
import { PuserService } from './coin-price/pusher.service';
import { GDAXWebsocketService } from './coin-price/gdax-websocket.service';


@NgModule({
    imports: [
        BlockchainSharedModule,
        MatGridListModule,
        MatIconModule,
        MatTabsModule,
        MatSelectModule,
        RouterModule.forRoot([ HOME_ROUTE ], { useHash: true })
    ],
    declarations: [
        HomeComponent,
        CoinPriceComponent,
        CoinPriceRowComponent,
        ExchangeRateComponent,
    ],
    entryComponents: [
    ],
    providers: [
        CoinPriceService,
        BitfinexWebsocketService,
        GDAXWebsocketService,
        ExchangeRateService,
        PuserService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlockchainHomeModule {}
