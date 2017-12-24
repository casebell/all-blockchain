import {  Routes } from '@angular/router';
import { tickerHomeRoute } from './ticker-home/ticker-home.route';
import { UserRouteAccessService } from '../shared';
const TICKER_ROUTES = [
    tickerHomeRoute
];

export const tickerState: Routes = [{
    path: '',
    data: {
        authorities: ['ROLE_ADMIN']
    },
    canActivate: [UserRouteAccessService],
    children: TICKER_ROUTES
}];
