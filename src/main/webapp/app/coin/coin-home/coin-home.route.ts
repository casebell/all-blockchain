import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../shared/index';
import { CoinHomeComponent } from './coin-home.component';

export const coinHomeRoute: Route = {
    path: 'home',
    component: CoinHomeComponent,
    data: {
        authorities: [],
        pageTitle: 'coin.coin-home.title'
    },
    canActivate: [UserRouteAccessService]
};
