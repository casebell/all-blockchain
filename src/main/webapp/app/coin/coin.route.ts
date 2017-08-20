import {  Routes } from '@angular/router';
import { coinHomeRoute } from "./coin-home/coin-home.route";
import { CoinComponent } from "./coin.component";
const COIN_ROUTES = [
    coinHomeRoute
];

export const coinState: Routes = [{
    path: 'coin',
    component:CoinComponent,
    children: COIN_ROUTES
}];
