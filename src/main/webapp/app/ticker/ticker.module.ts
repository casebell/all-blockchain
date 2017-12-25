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
    MatAutocompleteModule
} from '@angular/material';
import { TickerService } from './ticker.service';
import { MarketService } from '../entities/market';
import { CoinService } from '../coin/coin.service';
import { MarketCoinService } from './market-coin/market-coin.service';
@NgModule({
    imports: [
        BlockchainSharedModule,
        RouterModule.forChild(tickerState),
        MatButtonModule,
        MatDialogModule,
        MatStepperModule,
        MatInputModule,
        ReactiveFormsModule,
        MatAutocompleteModule
    ],
    declarations: [TickerHomeComponent, AddTickerDialogComponent],
    entryComponents: [AddTickerDialogComponent],
    providers: [TickerService,MarketService,CoinService,MarketCoinService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TickerModule {}
