import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { CoinBoardCommentBlockChainInfo } from './coin-board-comment-block-chain-info.model';
import { CoinBoardCommentBlockChainInfoService } from './coin-board-comment-block-chain-info.service';

@Injectable()
export class CoinBoardCommentBlockChainInfoPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private coinBoardCommentService: CoinBoardCommentBlockChainInfoService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.coinBoardCommentService.find(id).subscribe((res:HttpResponse<CoinBoardCommentBlockChainInfo>) => {
               const coinBoardComment = res.body;
                coinBoardComment.createdat = this.datePipe
                    .transform(coinBoardComment.createdat, 'yyyy-MM-ddThh:mm');
                coinBoardComment.updatedat = this.datePipe
                    .transform(coinBoardComment.updatedat, 'yyyy-MM-ddThh:mm');
                this.coinBoardCommentModalRef(component, coinBoardComment);
            });
        } else {
            return this.coinBoardCommentModalRef(component, new CoinBoardCommentBlockChainInfo());
        }
    }

    coinBoardCommentModalRef(component: Component, coinBoardComment: CoinBoardCommentBlockChainInfo): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.coinBoardComment = coinBoardComment;
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
