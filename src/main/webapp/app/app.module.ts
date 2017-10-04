import './vendor.ts';

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { Ng2Webstorage } from 'ng2-webstorage';
import { BlockchainSharedModule, UserRouteAccessService } from './shared';
import { BlockchainHomeModule } from './home/home.module';
import { BlockchainAdminModule } from './admin/admin.module';
import { BlockchainAccountModule } from './account/account.module';
import { BlockchainEntityModule } from './entities/entity.module';
import { CoinModule } from './coin/coin.module';
import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';
import { HttpClientModule } from '@angular/common/http';
import { FlexLayoutModule } from '@angular/flex-layout';
import { BlockchainHelpModule } from './help/help.module';
import { AdsenseModule } from 'ng2-adsense';

import 'hammerjs';
// jhipster-needle-angular-add-module-import JHipster will add new module here

import {
    JhiMainComponent,
    LayoutRoutingModule,
    NavbarComponent,
    FooterComponent,
    ProfileService,
    PageRibbonComponent,
    ActiveMenuDirective,
    ErrorComponent
} from './layouts';
import { MatButtonModule, MatButtonToggleModule, MatIconModule, MatMenuModule, MatToolbarModule,
    MATERIAL_COMPATIBILITY_MODE} from '@angular/material';
import { DataService } from './shared/data.service';

@NgModule({
    imports: [
        BrowserModule,
        HttpClientModule,
        FlexLayoutModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-'}),
        BlockchainSharedModule,
        BlockchainHomeModule,
        BlockchainAdminModule,
        BlockchainAccountModule,
        BlockchainEntityModule,
        BlockchainHelpModule,
        CoinModule,
        BrowserAnimationsModule,
        MatIconModule,
        MatButtonModule,
        MatMenuModule,
        MatToolbarModule,
        MatButtonToggleModule,
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
        UserRouteAccessService,
        {provide : MATERIAL_COMPATIBILITY_MODE, useValue: true},
        DataService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class BlockchainAppModule {}
