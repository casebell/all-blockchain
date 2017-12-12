import { NgModule, LOCALE_ID } from '@angular/core';
import { Title } from '@angular/platform-browser';
import { registerLocaleData } from '@angular/common';
import localeKo from '@angular/common/locales/ko';
import {
    BlockchainSharedLibsModule,
    JhiLanguageHelper,
    FindLanguageFromKeyPipe,
    JhiAlertComponent,
    JhiAlertErrorComponent,
    CurrencyExchangePipe
} from './';

@NgModule({
    imports: [
        BlockchainSharedLibsModule
    ],
    declarations: [
        FindLanguageFromKeyPipe,
        CurrencyExchangePipe,
        JhiAlertComponent,
        JhiAlertErrorComponent
    ],
    providers: [
        JhiLanguageHelper,
        Title,
        {
            provide: LOCALE_ID,
            useValue: 'ko'
        },
    ],
    exports: [
        BlockchainSharedLibsModule,
        FindLanguageFromKeyPipe,
        CurrencyExchangePipe,
        JhiAlertComponent,
        JhiAlertErrorComponent
    ]
})
export class BlockchainSharedCommonModule {
    constructor() {
        registerLocaleData(localeKo);
    }
}
