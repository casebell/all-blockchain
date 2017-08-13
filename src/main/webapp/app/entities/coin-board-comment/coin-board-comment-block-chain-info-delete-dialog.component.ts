import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CoinBoardCommentBlockChainInfo } from './coin-board-comment-block-chain-info.model';
import { CoinBoardCommentBlockChainInfoPopupService } from './coin-board-comment-block-chain-info-popup.service';
import { CoinBoardCommentBlockChainInfoService } from './coin-board-comment-block-chain-info.service';

@Component({
    selector: 'jhi-coin-board-comment-block-chain-info-delete-dialog',
    templateUrl: './coin-board-comment-block-chain-info-delete-dialog.component.html'
})
export class CoinBoardCommentBlockChainInfoDeleteDialogComponent {

    coinBoardComment: CoinBoardCommentBlockChainInfo;

    constructor(
        private coinBoardCommentService: CoinBoardCommentBlockChainInfoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.coinBoardCommentService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'coinBoardCommentListModification',
                content: 'Deleted an coinBoardComment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-coin-board-comment-block-chain-info-delete-popup',
    template: ''
})
export class CoinBoardCommentBlockChainInfoDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private coinBoardCommentPopupService: CoinBoardCommentBlockChainInfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.coinBoardCommentPopupService.open(CoinBoardCommentBlockChainInfoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
