import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { CoinBoardBlockChainInfo, CoinBoardBlockChainInfoService } from '../coin-board';

@Injectable()
export class CoinBoardBlockChainInfoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private coinBoardService: CoinBoardBlockChainInfoService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.coinBoardService.find(id)
                    .subscribe((coinBoardResponse: HttpResponse<CoinBoardBlockChainInfo>) => {
                        const coinBoard: CoinBoardBlockChainInfo = coinBoardResponse.body;
                        coinBoard.createdat = this.datePipe
                            .transform(coinBoard.createdat, 'yyyy-MM-ddTHH:mm:ss');
                        coinBoard.updatedat = this.datePipe
                            .transform(coinBoard.updatedat, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.coinBoardModalRef(component, coinBoard);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.coinBoardModalRef(component, new CoinBoardBlockChainInfo());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    coinBoardModalRef(component: Component, coinBoard: CoinBoardBlockChainInfo): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.coinBoard = coinBoard;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true, queryParamsHandling: 'merge' });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}