import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DatePipe } from '@angular/common';

import {
    BlockchainSharedLibsModule,
    BlockchainSharedCommonModule,
    CSRFService,
    AuthServerProvider,
    AccountService,
    UserService,
    StateStorageService,
    LoginService,
    LoginModalService,
    Principal,
    HasAnyAuthorityDirective,
    JhiSocialComponent,
    SocialService,
    JhiLoginModalComponent
} from './';
import { MdCheckboxModule, MdDialogModule, MdInputModule, MdButtonModule, MdIconModule, MdTabsModule } from "@angular/material";
import {ReactiveFormsModule} from "@angular/forms";

@NgModule({
    imports: [
        BlockchainSharedLibsModule,
        BlockchainSharedCommonModule,
        MdDialogModule,
        MdInputModule,
        MdButtonModule,
        MdIconModule,
        ReactiveFormsModule,
        MdCheckboxModule,
        MdTabsModule
    ],
    declarations: [
        JhiSocialComponent,
        JhiLoginModalComponent,
        HasAnyAuthorityDirective
    ],
    providers: [
        LoginService,
        LoginModalService,
        AccountService,
        StateStorageService,
        Principal,
        CSRFService,
        AuthServerProvider,
        SocialService,
        UserService,
        DatePipe
    ],
    entryComponents: [JhiLoginModalComponent],
    exports: [
        BlockchainSharedCommonModule,
        JhiSocialComponent,
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        DatePipe
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class BlockchainSharedModule {}
