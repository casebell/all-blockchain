import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';

import { ResourceBlockChainInfoComponent } from './resource-block-chain-info.component';
import { ResourceBlockChainInfoDetailComponent } from './resource-block-chain-info-detail.component';
import { ResourceBlockChainInfoPopupComponent } from './resource-block-chain-info-dialog.component';
import { ResourceBlockChainInfoDeletePopupComponent } from './resource-block-chain-info-delete-dialog.component';


export const resourceRoute: Routes = [
    {
        path: 'resource-block-chain-info',
        component: ResourceBlockChainInfoComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'blockchainApp.resource.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'resource-block-chain-info/:id',
        component: ResourceBlockChainInfoDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'blockchainApp.resource.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const resourcePopupRoute: Routes = [
    {
        path: 'resource-block-chain-info-new',
        component: ResourceBlockChainInfoPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'blockchainApp.resource.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'resource-block-chain-info/:id/edit',
        component: ResourceBlockChainInfoPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'blockchainApp.resource.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'resource-block-chain-info/:id/delete',
        component: ResourceBlockChainInfoDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'blockchainApp.resource.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
