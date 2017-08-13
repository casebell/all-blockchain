import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ResourceBlockChainInfo } from './resource-block-chain-info.model';
import { ResourceBlockChainInfoPopupService } from './resource-block-chain-info-popup.service';
import { ResourceBlockChainInfoService } from './resource-block-chain-info.service';

@Component({
    selector: 'jhi-resource-block-chain-info-delete-dialog',
    templateUrl: './resource-block-chain-info-delete-dialog.component.html'
})
export class ResourceBlockChainInfoDeleteDialogComponent {

    resource: ResourceBlockChainInfo;

    constructor(
        private resourceService: ResourceBlockChainInfoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.resourceService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'resourceListModification',
                content: 'Deleted an resource'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-resource-block-chain-info-delete-popup',
    template: ''
})
export class ResourceBlockChainInfoDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private resourcePopupService: ResourceBlockChainInfoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.resourcePopupService
                .open(ResourceBlockChainInfoDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
