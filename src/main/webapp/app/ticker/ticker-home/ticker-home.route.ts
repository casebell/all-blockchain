import { Route } from '@angular/router';
import { TickerHomeComponent } from './ticker-home.component';

export const tickerHomeRoute: Route = {
    path: 'ticker',
    component: TickerHomeComponent,
    data: {
        authorities: [],
        pageTitle: 'navbar.ticker.title'
    }
};
