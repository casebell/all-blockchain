/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { BlockchainTestModule } from '../../../test.module';
import { ResourceBlockChainInfoDialogComponent } from '../../../../../../main/webapp/app/entities/resource/resource-block-chain-info-dialog.component';
import { ResourceBlockChainInfoService } from '../../../../../../main/webapp/app/entities/resource/resource-block-chain-info.service';
import { ResourceBlockChainInfo } from '../../../../../../main/webapp/app/entities/resource/resource-block-chain-info.model';
//import { CoinBlockChainInfoService } from '../../../../../../main/webapp/app/entities/coin-block-chain-info';

describe('Component Tests', () => {

    describe('ResourceBlockChainInfo Management Dialog Component', () => {
        let comp: ResourceBlockChainInfoDialogComponent;
        let fixture: ComponentFixture<ResourceBlockChainInfoDialogComponent>;
        let service: ResourceBlockChainInfoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlockchainTestModule],
                declarations: [ResourceBlockChainInfoDialogComponent],
                providers: [
               //     CoinBlockChainInfoService,
                    ResourceBlockChainInfoService
                ]
            })
            .overrideTemplate(ResourceBlockChainInfoDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ResourceBlockChainInfoDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResourceBlockChainInfoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ResourceBlockChainInfo(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.resource = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'resourceListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new ResourceBlockChainInfo();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.resource = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'resourceListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
