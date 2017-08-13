import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BitCoinModule } from "./bit-coin/bit-coin.module";

@NgModule({
    imports: [
        BitCoinModule
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CoinModule {}
