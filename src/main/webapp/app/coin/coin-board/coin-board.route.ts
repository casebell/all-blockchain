
import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';



import {CoinBoardComponent} from "./coin-board.component";

export const coinBoardRoute: Routes = [
    {
        path: 'coin-board',
        component: CoinBoardComponent,
        data: {
            authorities: [],
            pageTitle: 'blockchainApp.coin.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];


