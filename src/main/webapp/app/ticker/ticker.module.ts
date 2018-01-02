import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BlockchainSharedModule } from '../shared';
import { tickerState } from './ticker.route';
import { TickerHomeComponent } from './ticker-home/ticker-home.component';
import { AddTickerDialogComponent } from './ticker-home/add-ticker-dialog/add-ticker-dialog.component';
import { ReactiveFormsModule } from '@angular/forms';
import {
    MatButtonModule,
    MatDialogModule,
    MatInputModule,
    MatStepperModule,
    MatAutocompleteModule,
    MatCardModule,
    MatSelectModule,
    MatChipsModule,
    MatGridListModule
} from '@angular/material';
import { TickerService } from './ticker.service';
import { MarketService } from '../entities/market';
import { CoinService } from '../coin/coin.service';
import { MarketCoinService } from './market-coin/market-coin.service';
import { TickerItemComponent } from './ticker-home/ticker-item/ticker-item.component';
import { ExchangeRateService } from '../home/coin-price/coin-price-row/exchange-rate.service';
@NgModule({
    imports: [
        BlockchainSharedModule,
        RouterModule.forChild(tickerState),
        MatButtonModule,
        MatDialogModule,
        MatStepperModule,
        MatInputModule,
        ReactiveFormsModule,
        MatAutocompleteModule,
        MatCardModule,
        MatSelectModule,
        MatChipsModule,
        MatGridListModule
    ],
    declarations: [TickerHomeComponent, AddTickerDialogComponent, TickerItemComponent],
    entryComponents: [AddTickerDialogComponent],
    providers: [TickerService,MarketService,CoinService,MarketCoinService,ExchangeRateService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TickerModule {}
