import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CoinBoardBlockChainInfo } from './coin-board-block-chain-info.model';
import { CoinBoardBlockChainInfoPopupService } from './coin-board-block-chain-info-popup.service';
import { CoinBoardBlockChainInfoService } from './coin-board-block-chain-info.service';

@Component({
    selector: 'jhi-coin-board-block-chain-info-delete-dialog',
    templateUrl: './coin-board-block-chain-info-delete-dialog.component.html'
})
export class CoinBoardBlockChainInfoDeleteDialogComponent {

    coinBoard: CoinBoardBlockChainInfo;

    constructor(
        private coinBoardService: CoinBoardBlockChainInfoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.coinBoardService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'coinBoardListModification',
                content: 'Deleted an coinBoard'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-coin-board-block-chain-info-delete-popup',
    template: ''
})
export class CoinBoardBlockChainInfoDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private coinBoardPopupService: CoinBoardBlockChainInfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.coinBoardPopupService.open(CoinBoardBlockChainInfoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
