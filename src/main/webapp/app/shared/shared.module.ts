import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DatePipe } from '@angular/common';
import {DndModule} from 'ng2-dnd';

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
import {ReactiveFormsModule} from '@angular/forms';
import {
    MatButtonModule, MatCheckboxModule, MatDialogModule, MatIconModule, MatInputModule,
    MatTabsModule
} from '@angular/material';

@NgModule({
    imports: [
        BlockchainSharedLibsModule,
        BlockchainSharedCommonModule,
        MatDialogModule,
        MatInputModule,
        MatButtonModule,
        MatIconModule,
        ReactiveFormsModule,
        MatCheckboxModule,
        MatTabsModule,
        DndModule
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
        DatePipe,
        DndModule
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class BlockchainSharedModule {}
