import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../shared/index';
import { TermsComponent } from './terms.component';


export const termsRoute: Route = {
    path: 'terms',
    component: TermsComponent,
    data: {
        authorities: [],
        pageTitle: 'terms.title'
    },
    canActivate: [UserRouteAccessService]
};

