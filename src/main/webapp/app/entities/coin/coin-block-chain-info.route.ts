import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CoinBlockChainInfoComponent } from './coin-block-chain-info.component';
import { CoinBlockChainInfoDetailComponent } from './coin-block-chain-info-detail.component';
import { CoinBlockChainInfoPopupComponent } from './coin-block-chain-info-dialog.component';
import { CoinBlockChainInfoDeletePopupComponent } from './coin-block-chain-info-delete-dialog.component';

import { Principal } from '../../shared';

export const coinRoute: Routes = [
    {
        path: 'coin-block-chain-info',
        component: CoinBlockChainInfoComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'blockchainApp.coin.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'coin-block-chain-info/:id',
        component: CoinBlockChainInfoDetailComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'blockchainApp.coin.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const coinPopupRoute: Routes = [
    {
        path: 'coin-block-chain-info-new',
        component: CoinBlockChainInfoPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'blockchainApp.coin.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'coin-block-chain-info/:id/edit',
        component: CoinBlockChainInfoPopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'blockchainApp.coin.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'coin-block-chain-info/:id/delete',
        component: CoinBlockChainInfoDeletePopupComponent,
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'blockchainApp.coin.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
