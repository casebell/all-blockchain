import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BlockchainSharedModule } from '../../shared';
import {
    AirDropService,
    AirDropPopupService,
    AirDropComponent,
    AirDropDetailComponent,
    AirDropDialogComponent,
    AirDropPopupComponent,
    AirDropDeletePopupComponent,
    AirDropDeleteDialogComponent,
    airDropRoute,
    airDropPopupRoute,
} from './';

const ENTITY_STATES = [
    ...airDropRoute,
    ...airDropPopupRoute,
];

@NgModule({
    imports: [
        BlockchainSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        AirDropComponent,
        AirDropDetailComponent,
        AirDropDialogComponent,
        AirDropDeleteDialogComponent,
        AirDropPopupComponent,
        AirDropDeletePopupComponent,
    ],
    entryComponents: [
        AirDropComponent,
        AirDropDialogComponent,
        AirDropPopupComponent,
        AirDropDeleteDialogComponent,
        AirDropDeletePopupComponent,
    ],
    providers: [
        AirDropService,
        AirDropPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlockchainAirDropModule {}
