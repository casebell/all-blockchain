import { Route } from '@angular/router';
import { AirdropsHomeComponent } from './airdrops-home.component';


export const airdropsHomeRoute: Route = {
    path: 'airdrops',
    component: AirdropsHomeComponent,
    data: {
        pageTitle: 'navbar.airdrops.title'
    }
};
