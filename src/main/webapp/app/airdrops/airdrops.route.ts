import {  Routes } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { airdropsHomeRoute } from './airdrops-home/airdrops-home.route';
const AIRDROPS_ROUTES = [
    airdropsHomeRoute
];

export const airdropsState: Routes = [{
    path: '',
    data: {
        authorities: []
    },
    canActivate: [UserRouteAccessService],
    children: AIRDROPS_ROUTES
}];
