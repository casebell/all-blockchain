import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { AirDropComponent } from './air-drop.component';
import { AirDropDetailComponent } from './air-drop-detail.component';
import { AirDropPopupComponent } from './air-drop-dialog.component';
import { AirDropDeletePopupComponent } from './air-drop-delete-dialog.component';

export const airDropRoute: Routes = [
    {
        path: 'air-drop',
        component: AirDropComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'blockchainApp.airDrop.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'air-drop/:id',
        component: AirDropDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'blockchainApp.airDrop.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const airDropPopupRoute: Routes = [
    {
        path: 'air-drop-new',
        component: AirDropPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'blockchainApp.airDrop.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'air-drop/:id/edit',
        component: AirDropPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'blockchainApp.airDrop.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'air-drop/:id/delete',
        component: AirDropDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'blockchainApp.airDrop.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
