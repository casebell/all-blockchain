
import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import {BitCoinComponent} from "./bit-coin.component";


export const bitCoinRoute: Routes = [
    {
        path: 'bitcoin',
        component: BitCoinComponent,
        data: {
            authorities: [],
            pageTitle: 'blockchainApp.coin.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];


