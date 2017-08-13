import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { ResourceBlockChainInfo } from './resource-block-chain-info.model';
import { ResourceBlockChainInfoService } from './resource-block-chain-info.service';

@Injectable()
export class ResourceBlockChainInfoPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private resourceService: ResourceBlockChainInfoService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.resourceService.find(id).subscribe((resource) => {
                resource.createdAt = this.datePipe
                    .transform(resource.createdAt, 'yyyy-MM-ddThh:mm');
                resource.updatedAt = this.datePipe
                    .transform(resource.updatedAt, 'yyyy-MM-ddThh:mm');
                this.resourceModalRef(component, resource);
            });
        } else {
            return this.resourceModalRef(component, new ResourceBlockChainInfo());
        }
    }

    resourceModalRef(component: Component, resource: ResourceBlockChainInfo): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.resource = resource;
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
