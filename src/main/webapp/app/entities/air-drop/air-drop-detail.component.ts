import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { AirDrop } from './air-drop.model';
import { AirDropService } from './air-drop.service';

@Component({
    selector: 'jhi-air-drop-detail',
    templateUrl: './air-drop-detail.component.html'
})
export class AirDropDetailComponent implements OnInit, OnDestroy {

    airDrop: AirDrop;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private airDropService: AirDropService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAirDrops();
    }

    load(id) {
        this.airDropService.find(id)
            .subscribe((airDropResponse: HttpResponse<AirDrop>) => {
                this.airDrop = airDropResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAirDrops() {
        this.eventSubscriber = this.eventManager.subscribe(
            'airDropListModification',
            (response) => this.load(this.airDrop.id)
        );
    }
}
