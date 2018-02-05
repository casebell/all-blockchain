import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { HttpResponse } from '@angular/common/http';
import { DatePipe } from '@angular/common';
import { ResourceBlockChainInfo } from './resource-block-chain-info.model';
import { ResourceBlockChainInfoService } from './resource-block-chain-info.service';

@Injectable()
export class ResourceBlockChainInfoPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private resourceService: ResourceBlockChainInfoService

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
                this.resourceService.find(id)
                    .subscribe((resourceResponse: HttpResponse<ResourceBlockChainInfo>) => {
                        const resource: ResourceBlockChainInfo = resourceResponse.body;
                        resource.createdAt = this.datePipe
                            .transform(resource.createdAt, 'yyyy-MM-ddTHH:mm:ss');
                        resource.updatedAt = this.datePipe
                            .transform(resource.updatedAt, 'yyyy-MM-ddTHH:mm:ss');
                        this.ngbModalRef = this.resourceModalRef(component, resource);
                        resolve(this.ngbModalRef);
                    });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.resourceModalRef(component, new ResourceBlockChainInfo());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    resourceModalRef(component: Component, resource: ResourceBlockChainInfo): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.resource = resource;
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
