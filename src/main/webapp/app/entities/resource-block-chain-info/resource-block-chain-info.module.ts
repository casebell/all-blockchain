import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BlockchainSharedModule } from '../../shared';
import {
    ResourceBlockChainInfoService,
    ResourceBlockChainInfoPopupService,
    ResourceBlockChainInfoComponent,
    ResourceBlockChainInfoDetailComponent,
    ResourceBlockChainInfoDialogComponent,
    ResourceBlockChainInfoPopupComponent,
    ResourceBlockChainInfoDeletePopupComponent,
    ResourceBlockChainInfoDeleteDialogComponent,
    resourceRoute,
    resourcePopupRoute,
} from './';

const ENTITY_STATES = [
    ...resourceRoute,
    ...resourcePopupRoute,
];

@NgModule({
    imports: [
        BlockchainSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        ResourceBlockChainInfoComponent,
        ResourceBlockChainInfoDetailComponent,
        ResourceBlockChainInfoDialogComponent,
        ResourceBlockChainInfoDeleteDialogComponent,
        ResourceBlockChainInfoPopupComponent,
        ResourceBlockChainInfoDeletePopupComponent,
    ],
    entryComponents: [
        ResourceBlockChainInfoComponent,
        ResourceBlockChainInfoDialogComponent,
        ResourceBlockChainInfoPopupComponent,
        ResourceBlockChainInfoDeleteDialogComponent,
        ResourceBlockChainInfoDeletePopupComponent,
    ],
    providers: [
        ResourceBlockChainInfoService,
        ResourceBlockChainInfoPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlockchainResourceBlockChainInfoModule {}
