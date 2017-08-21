import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { coinState } from './coin.route';
import { CoinComponent } from './coin.component';
import { CoinHomeComponent } from './coin-home/coin-home.component';
import { CoinSidenavComponent} from './coin-sidenav/coin-sidenav.component';
import { MdSidenavModule, 
         MdButtonModule,
         MdTableModule, 
         MdInputModule } from '@angular/material';
import { CdkTableModule } from "@angular/cdk";
import { CoinSidenavService } from "./coin-sidenav/coin-sidenav.service";


@NgModule({
    imports: [
        MdSidenavModule,
        MdButtonModule,
        MdTableModule,
        MdInputModule,
        CdkTableModule,
        RouterModule.forRoot(coinState, {useHash: true})
    ],
    declarations: [CoinComponent,
                   CoinSidenavComponent, 
                   CoinHomeComponent],
    providers: [CoinSidenavService],                    
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CoinModule {}
