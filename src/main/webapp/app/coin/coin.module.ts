import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { coinState } from './coin.route';
import { CoinComponent } from './coin.component';
import { CoinHomeComponent } from './coin-home/coin-home.component';
import { CoinSidenavComponent} from './coin-sidenav/coin-sidenav.component';

@NgModule({
    imports: [
        RouterModule.forRoot(coinState, {useHash: true})
    ],
    declarations: [CoinComponent,
                   CoinSidenavComponent, 
                   CoinHomeComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CoinModule {}
