import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { CoinBoardCommentBlockChainInfoComponent } from './coin-board-comment-block-chain-info.component';
import { CoinBoardCommentBlockChainInfoDetailComponent } from './coin-board-comment-block-chain-info-detail.component';
import { CoinBoardCommentBlockChainInfoPopupComponent } from './coin-board-comment-block-chain-info-dialog.component';
import {
    CoinBoardCommentBlockChainInfoDeletePopupComponent
} from './coin-board-comment-block-chain-info-delete-dialog.component';

export const coinBoardCommentRoute: Routes = [
    {
        path: 'coin-board-comment-block-chain-info',
        component: CoinBoardCommentBlockChainInfoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blockchainApp.coinBoardComment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'coin-board-comment-block-chain-info/:id',
        component: CoinBoardCommentBlockChainInfoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blockchainApp.coinBoardComment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const coinBoardCommentPopupRoute: Routes = [
    {
        path: 'coin-board-comment-block-chain-info-new',
        component: CoinBoardCommentBlockChainInfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blockchainApp.coinBoardComment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'coin-board-comment-block-chain-info/:id/edit',
        component: CoinBoardCommentBlockChainInfoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blockchainApp.coinBoardComment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'coin-board-comment-block-chain-info/:id/delete',
        component: CoinBoardCommentBlockChainInfoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blockchainApp.coinBoardComment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
