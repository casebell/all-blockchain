import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BlockchainSharedModule } from '../shared';
import { HOME_ROUTE, HomeComponent } from './';
import { CoinPriceComponent } from './coin-price/coin-price.component';
import { CoinPriceService } from './coin-price/coin-price.service';
import { MdGridListModule, MdIconModule, MdSelectModule, MdTabsModule } from '@angular/material';
import { CoinPriceRowComponent } from './coin-price/coin-price-row/coin-price-row.component';
import { ExchangeRateService } from './coin-price/coin-price-row/exchange-rate.service';
import { ExchangeRateComponent } from './exchange-rate/exchange-rate.component';
import { WebsocketService } from '../shared/websocket.service';


@NgModule({
    imports: [
        BlockchainSharedModule,
        MdGridListModule,
        MdIconModule,
        MdTabsModule,
        MdSelectModule,
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
        WebsocketService,
        ExchangeRateService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlockchainHomeModule {}
