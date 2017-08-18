import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../shared/index';
import { AboutComponent } from "./about.component";


export const aboutRoute: Route = {
    path: 'about',
    component: AboutComponent,
    data: {
        authorities: [],
        pageTitle: 'about.title'
    },
    canActivate: [UserRouteAccessService]
};

