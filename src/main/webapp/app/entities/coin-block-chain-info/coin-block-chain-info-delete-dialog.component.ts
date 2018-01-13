import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CoinBlockChainInfo } from './coin-block-chain-info.model';
import { CoinBlockChainInfoPopupService } from './coin-block-chain-info-popup.service';
import { CoinBlockChainInfoService } from './coin-block-chain-info.service';

@Component({
    selector: 'jhi-coin-block-chain-info-delete-dialog',
    templateUrl: './coin-block-chain-info-delete-dialog.component.html'
})
export class CoinBlockChainInfoDeleteDialogComponent {

    coin: CoinBlockChainInfo;

    constructor(
        private coinService: CoinBlockChainInfoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.coinService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'coinListModification',
                content: 'Deleted an coin'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-coin-block-chain-info-delete-popup',
    template: ''
})
export class CoinBlockChainInfoDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private coinPopupService: CoinBlockChainInfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.coinPopupService
                .open(CoinBlockChainInfoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
