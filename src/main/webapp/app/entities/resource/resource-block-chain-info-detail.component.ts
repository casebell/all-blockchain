import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { ResourceBlockChainInfo } from './resource-block-chain-info.model';
import { ResourceBlockChainInfoService } from './resource-block-chain-info.service';

@Component({
    selector: 'jhi-resource-block-chain-info-detail',
    templateUrl: './resource-block-chain-info-detail.component.html'
})
export class ResourceBlockChainInfoDetailComponent implements OnInit, OnDestroy {

    resource: ResourceBlockChainInfo;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private resourceService: ResourceBlockChainInfoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInResources();
    }

    load(id) {
        this.resourceService.find(id).subscribe((resource) => {
            this.resource = resource;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInResources() {
        this.eventSubscriber = this.eventManager.subscribe(
            'resourceListModification',
            (response) => this.load(this.resource.id)
        );
    }
}
