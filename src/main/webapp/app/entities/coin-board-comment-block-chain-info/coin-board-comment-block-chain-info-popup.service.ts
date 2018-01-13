import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { CoinBoardCommentBlockChainInfo } from './coin-board-comment-block-chain-info.model';
import { CoinBoardCommentBlockChainInfoService } from './coin-board-comment-block-chain-info.service';

@Injectable()
export class CoinBoardCommentBlockChainInfoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private coinBoardCommentService: CoinBoardCommentBlockChainInfoService

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
                this.coinBoardCommentService.find(id).subscribe((coinBoardComment) => {
                    coinBoardComment.createdat = this.datePipe
                        .transform(coinBoardComment.createdat, 'yyyy-MM-ddTHH:mm:ss');
                    coinBoardComment.updatedat = this.datePipe
                        .transform(coinBoardComment.updatedat, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.coinBoardCommentModalRef(component, coinBoardComment);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.coinBoardCommentModalRef(component, new CoinBoardCommentBlockChainInfo());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    coinBoardCommentModalRef(component: Component, coinBoardComment: CoinBoardCommentBlockChainInfo): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.coinBoardComment = coinBoardComment;
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
