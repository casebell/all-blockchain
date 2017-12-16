import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BlockchainSharedModule } from '../shared';
import { tickerState } from './ticker.route';
import { MatButtonModule } from '@angular/material/button';
import { TickerHomeComponent } from './ticker-home/ticker-home.component';
@NgModule({
    imports: [
        BlockchainSharedModule,
        RouterModule.forChild(tickerState),
        MatButtonModule
    ],
    declarations: [TickerHomeComponent],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class TickerModule {}
