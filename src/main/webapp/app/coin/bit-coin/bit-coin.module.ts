import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { bitCoinRoute } from "./bit-coin.route";
import { BitCoinComponent } from "./bit-coin.component";

const ENTITY_STATES = [
    ...bitCoinRoute
];

@NgModule({
    imports: [
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        BitCoinComponent

    ],
    entryComponents: [

    ],
    providers: [

    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BitCoinModule {}
