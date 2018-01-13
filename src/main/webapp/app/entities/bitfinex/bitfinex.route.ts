import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { BitfinexComponent } from './bitfinex.component';
import { BitfinexDetailComponent } from './bitfinex-detail.component';
import { BitfinexPopupComponent } from './bitfinex-dialog.component';
import { BitfinexDeletePopupComponent } from './bitfinex-delete-dialog.component';

export const bitfinexRoute: Routes = [
    {
        path: 'bitfinex',
        component: BitfinexComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blockchainApp.bitfinex.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'bitfinex/:id',
        component: BitfinexDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blockchainApp.bitfinex.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bitfinexPopupRoute: Routes = [
    {
        path: 'bitfinex-new',
        component: BitfinexPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blockchainApp.bitfinex.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bitfinex/:id/edit',
        component: BitfinexPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blockchainApp.bitfinex.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'bitfinex/:id/delete',
        component: BitfinexDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blockchainApp.bitfinex.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
