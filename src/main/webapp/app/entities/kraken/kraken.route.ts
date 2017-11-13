import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';

import { KrakenComponent } from './kraken.component';
import { KrakenDetailComponent } from './kraken-detail.component';
import { KrakenPopupComponent } from './kraken-dialog.component';
import { KrakenDeletePopupComponent } from './kraken-delete-dialog.component';

export const krakenRoute: Routes = [
    {
        path: 'kraken',
        component: KrakenComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blockchainApp.kraken.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'kraken/:id',
        component: KrakenDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blockchainApp.kraken.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const krakenPopupRoute: Routes = [
    {
        path: 'kraken-new',
        component: KrakenPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blockchainApp.kraken.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'kraken/:id/edit',
        component: KrakenPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blockchainApp.kraken.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'kraken/:id/delete',
        component: KrakenDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blockchainApp.kraken.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
