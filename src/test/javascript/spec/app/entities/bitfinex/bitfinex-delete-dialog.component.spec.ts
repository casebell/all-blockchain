/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { BlockchainTestModule } from '../../../test.module';
import { BitfinexDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/bitfinex/bitfinex-delete-dialog.component';
import { BitfinexService } from '../../../../../../main/webapp/app/entities/bitfinex/bitfinex.service';

describe('Component Tests', () => {

    describe('Bitfinex Management Delete Component', () => {
        let comp: BitfinexDeleteDialogComponent;
        let fixture: ComponentFixture<BitfinexDeleteDialogComponent>;
        let service: BitfinexService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlockchainTestModule],
                declarations: [BitfinexDeleteDialogComponent],
                providers: [
                    BitfinexService
                ]
            })
            .overrideTemplate(BitfinexDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BitfinexDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BitfinexService);
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
