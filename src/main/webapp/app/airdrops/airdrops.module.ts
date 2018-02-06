import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BlockchainSharedModule } from '../shared';
import { ReactiveFormsModule } from '@angular/forms';
import {
    MatButtonModule,
    MatDialogModule,
    MatInputModule,
    MatStepperModule,
    MatAutocompleteModule,
    MatCardModule,
    MatSelectModule,
    MatChipsModule,
    MatGridListModule,
    MatIconModule
} from '@angular/material';

import { AirdropsHomeComponent } from './airdrops-home/airdrops-home.component';
import { airdropsState } from './airdrops.route';

@NgModule({
    imports: [
        BlockchainSharedModule,
        RouterModule.forChild(airdropsState),
        MatButtonModule,
        MatDialogModule,
        MatStepperModule,
        MatInputModule,
        ReactiveFormsModule,
        MatAutocompleteModule,
        MatCardModule,
        MatSelectModule,
        MatChipsModule,
        MatGridListModule,
        MatIconModule
    ],
    declarations: [ AirdropsHomeComponent],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AirdropsModule {}
