import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CoinBoardBlockChainInfoComponent } from './coin-board-block-chain-info.component';
import { CoinBoardBlockChainInfoDetailComponent } from './coin-board-block-chain-info-detail.component';
import { CoinBoardBlockChainInfoPopupComponent } from './coin-board-block-chain-info-dialog.component';
import { CoinBoardBlockChainInfoDeletePopupComponent } from './coin-board-block-chain-info-delete-dialog.component';

export const coinBoardRoute: Routes = [
    {
        path: 'coin-board-block-chain-info',
        component: CoinBoardBlockChainInfoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blockchainApp.coinBoard.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'coin-board-block-chain-info/:id',
        component: CoinBoardBlockChainInfoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blockchainApp.coinBoard.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const coinBoardPopupRoute: Routes = [
    {
        path: 'coin-board-block-chain-info-new',
        component: CoinBoardBlockChainInfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blockchainApp.coinBoard.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'coin-board-block-chain-info/:id/edit',
        component: CoinBoardBlockChainInfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blockchainApp.coinBoard.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'coin-board-block-chain-info/:id/delete',
        component: CoinBoardBlockChainInfoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blockchainApp.coinBoard.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
