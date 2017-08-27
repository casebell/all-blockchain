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
import { MdButtonModule, MdButtonToggleModule, MdIconModule, MdMenuModule, MdToolbarModule} from '@angular/material';
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
        MdIconModule,
        MdButtonModule,
        MdMenuModule,
        MdToolbarModule,
        MdButtonToggleModule,
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
        DataService
    ],
    bootstrap: [ JhiMainComponent ]
})
export class BlockchainAppModule {}
