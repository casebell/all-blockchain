/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { BlockchainTestModule } from '../../../test.module';
import { ResourceBlockChainInfoDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/resource-block-chain-info/resource-block-chain-info-delete-dialog.component';
import { ResourceBlockChainInfoService } from '../../../../../../main/webapp/app/entities/resource-block-chain-info/resource-block-chain-info.service';

describe('Component Tests', () => {

    describe('ResourceBlockChainInfo Management Delete Component', () => {
        let comp: ResourceBlockChainInfoDeleteDialogComponent;
        let fixture: ComponentFixture<ResourceBlockChainInfoDeleteDialogComponent>;
        let service: ResourceBlockChainInfoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlockchainTestModule],
                declarations: [ResourceBlockChainInfoDeleteDialogComponent],
                providers: [
                    ResourceBlockChainInfoService
                ]
            })
            .overrideTemplate(ResourceBlockChainInfoDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ResourceBlockChainInfoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResourceBlockChainInfoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
