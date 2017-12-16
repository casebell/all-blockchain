import {  Routes } from '@angular/router';
import { tickerHomeRoute } from './ticker-home/ticker-home.route';
const TICKER_ROUTES = [
    tickerHomeRoute
];

export const tickerState: Routes = [{
    path: '',
    children: TICKER_ROUTES
}];
