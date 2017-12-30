import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { QuoteComponent } from './quote.component';
import { QuoteDetailComponent } from './quote-detail.component';
import { QuotePopupComponent } from './quote-dialog.component';
import { QuoteDeletePopupComponent } from './quote-delete-dialog.component';

export const quoteRoute: Routes = [
    {
        path: 'quote',
        component: QuoteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blockchainApp.quote.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'quote/:id',
        component: QuoteDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blockchainApp.quote.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const quotePopupRoute: Routes = [
    {
        path: 'quote-new',
        component: QuotePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blockchainApp.quote.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'quote/:id/edit',
        component: QuotePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blockchainApp.quote.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'quote/:id/delete',
        component: QuoteDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blockchainApp.quote.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
