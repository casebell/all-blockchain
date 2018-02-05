import './vendor.ts';

import { NgModule, Injector } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { Ng2Webstorage, LocalStorageService, SessionStorageService  } from 'ngx-webstorage';
import { JhiEventManager } from 'ng-jhipster';

import { AuthInterceptor } from './blocks/interceptor/auth.interceptor';
import { AuthExpiredInterceptor } from './blocks/interceptor/auth-expired.interceptor';
import { ErrorHandlerInterceptor } from './blocks/interceptor/errorhandler.interceptor';
import { NotificationInterceptor } from './blocks/interceptor/notification.interceptor';
import { BlockchainSharedModule, UserRouteAccessService } from './shared';
import { BlockchainAppRoutingModule} from './app-routing.module';
import { BlockchainHomeModule } from './home/home.module';
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
        PaginationConfig,
        UserRouteAccessService,
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthInterceptor,
            multi: true,
            deps: [
                LocalStorageService,
                SessionStorageService
            ]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: AuthExpiredInterceptor,
            multi: true,
            deps: [
                Injector
            ]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: ErrorHandlerInterceptor,
            multi: true,
            deps: [
                JhiEventManager
            ]
        },
        {
            provide: HTTP_INTERCEPTORS,
            useClass: NotificationInterceptor,
            multi: true,
            deps: [
                Injector
            ]
        }
    ],
    bootstrap: [ JhiMainComponent ]
})
export class BlockchainAppModule {}
