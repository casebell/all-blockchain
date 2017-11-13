import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { coinState } from './coin.route';
import { CoinComponent } from './coin.component';
import { CoinHomeComponent } from './coin-home/coin-home.component';
import { CoinSidenavComponent} from './coin-sidenav/coin-sidenav.component';
import {
    MatSidenavModule,
    MatButtonModule,
    MatTableModule,
    MatInputModule, MatIconModule
} from '@angular/material';
import { CoinSidenavService } from './coin-sidenav/coin-sidenav.service';
import { BlockchainSharedModule } from '../shared/shared.module';
import { CoinService } from './coin.service';

@NgModule({
    imports: [
        MatSidenavModule,
        MatButtonModule,
        MatTableModule,
        MatInputModule,
        MatIconModule,
        BlockchainSharedModule,
        RouterModule.forRoot(coinState, {useHash: true})
    ],
    declarations: [CoinComponent,
                   CoinSidenavComponent,
                   CoinHomeComponent],
    providers: [CoinSidenavService, CoinService],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CoinModule {}
