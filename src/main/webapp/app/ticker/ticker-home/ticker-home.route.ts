import { Route } from '@angular/router';
import { TickerHomeComponent } from './ticker-home.component';

export const tickerHomeRoute: Route = {
    path: 'ticker',
    component: TickerHomeComponent,
    data: {
        pageTitle: 'navbar.ticker.title'
    }
};
