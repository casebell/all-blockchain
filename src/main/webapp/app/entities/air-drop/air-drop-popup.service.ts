import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { AirDrop } from './air-drop.model';
import { AirDropService } from './air-drop.service';

@Injectable()
export class AirDropPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private airDropService: AirDropService

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
                this.airDropService.find(id)
                    .subscribe((airDropResponse: HttpResponse<AirDrop>) => {
                        const airDrop: AirDrop = airDropResponse.body;
                        airDrop.startDate = this.datePipe
                            .transform(airDrop.startDate, 'yyyy-MM-ddTHH:mm:ss');
                        airDrop.finishDate = this.datePipe
                            .transform(airDrop.finishDate, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.airDropModalRef(component, airDrop);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.airDropModalRef(component, new AirDrop());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    airDropModalRef(component: Component, airDrop: AirDrop): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.airDrop = airDrop;
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
