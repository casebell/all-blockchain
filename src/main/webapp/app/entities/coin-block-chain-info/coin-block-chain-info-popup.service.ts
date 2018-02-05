import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { CoinBlockChainInfo } from './coin-block-chain-info.model';
import { CoinBlockChainInfoService } from './coin-block-chain-info.service';

@Injectable()
export class CoinBlockChainInfoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private coinService: CoinBlockChainInfoService

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
                this.coinService.find(id)
                    .subscribe((coinResponse: HttpResponse<CoinBlockChainInfo>) => {
                        const coin: CoinBlockChainInfo = coinResponse.body;
                        coin.releaseat = this.datePipe
                            .transform(coin.releaseat, 'yyyy-MM-ddTHH:mm:ss');
                        coin.createdat = this.datePipe
                            .transform(coin.createdat, 'yyyy-MM-ddTHH:mm:ss');
                        coin.updatedat = this.datePipe
                            .transform(coin.updatedat, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.coinModalRef(component, coin);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.coinModalRef(component, new CoinBlockChainInfo());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    coinModalRef(component: Component, coin: CoinBlockChainInfo): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.coin = coin;
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
