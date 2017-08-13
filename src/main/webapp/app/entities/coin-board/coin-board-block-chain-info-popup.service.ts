import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { CoinBoardBlockChainInfo } from './coin-board-block-chain-info.model';
import { CoinBoardBlockChainInfoService } from './coin-board-block-chain-info.service';

@Injectable()
export class CoinBoardBlockChainInfoPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private coinBoardService: CoinBoardBlockChainInfoService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.coinBoardService.find(id).subscribe((coinBoard) => {
                coinBoard.createdat = this.datePipe
                    .transform(coinBoard.createdat, 'yyyy-MM-ddThh:mm');
                coinBoard.updatedat = this.datePipe
                    .transform(coinBoard.updatedat, 'yyyy-MM-ddThh:mm');
                this.coinBoardModalRef(component, coinBoard);
            });
        } else {
            return this.coinBoardModalRef(component, new CoinBoardBlockChainInfo());
        }
    }

    coinBoardModalRef(component: Component, coinBoard: CoinBoardBlockChainInfo): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.coinBoard = coinBoard;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
