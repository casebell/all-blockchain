import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Ng2Webstorage } from 'ngx-webstorage';
import { BlockchainSharedModule, UserRouteAccessService } from './shared';
import { BlockchainHomeModule } from './home';
import { BlockchainAdminModule } from './admin/admin.module';
import { BlockchainAccountModule } from './account/account.module';
import { BlockchainEntityModule } from './entities/entity.module';
import { CoinModule } from './coin/coin.module';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';
import { HttpClientModule } from '@angular/common/http';
import { BlockchainHelpModule } from './help/help.module';
import { AdsenseModule } from 'ng2-adsense';
import 'hammerjs';
// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';
import { MatButtonModule, MatButtonToggleModule, MatIconModule, MatMenuModule, MatToolbarModule,        MatCardModule,
} from '@angular/material';
import { NewsModule } from './news/news.module';
import { BlockchainAppRoutingModule } from './app-routing.module';
import { TickerModule } from './ticker/ticker.module';
import { DndModule } from 'ng2-dnd';
import { FlexLayoutModule } from '@angular/flex-layout';

@NgModule({
    imports: [
        BrowserModule,
        HttpClientModule,
        BlockchainAppRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        BlockchainSharedModule,
        BlockchainHomeModule,
        BlockchainAdminModule,
        BlockchainAccountModule,
        BlockchainEntityModule,
        BlockchainHelpModule,
        MatCardModule,
        CoinModule,
        NewsModule,
        TickerModule,
        BrowserAnimationsModule,
        MatIconModule,
        MatButtonModule,
        MatMenuModule,
        MatToolbarModule,
        MatButtonToggleModule,
        FlexLayoutModule,
        DndModule.forRoot(),
        AdsenseModule.forRoot({
            adClient: 'ca-pub-3429112291996061',
            adSlot: 7386502521,
          })
        // jhipster-needle-angular-add-module JHipster will add new module here
    ],
    declarations: [
        JhiMainComponent,
        NavbarComponent,
        ErrorComponent,
        PageRibbonComponent,
        ActiveMenuDirective,
        FooterComponent
    ],
    providers: [
        ProfileService,
        customHttpProvider(),
        PaginationConfig,
        UserRouteAccessService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class BlockchainAppModule {}
