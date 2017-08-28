import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CoinisComponent } from './coinis.component';
import { CoinisDetailComponent } from './coinis-detail.component';
import { CoinisPopupComponent } from './coinis-dialog.component';
import { CoinisDeletePopupComponent } from './coinis-delete-dialog.component';

export const coinisRoute: Routes = [
    {
        path: 'coinis',
        component: CoinisComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blockchainApp.coinis.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'coinis/:id',
        component: CoinisDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blockchainApp.coinis.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const coinisPopupRoute: Routes = [
    {
        path: 'coinis-new',
        component: CoinisPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blockchainApp.coinis.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'coinis/:id/edit',
        component: CoinisPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blockchainApp.coinis.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'coinis/:id/delete',
        component: CoinisDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blockchainApp.coinis.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
