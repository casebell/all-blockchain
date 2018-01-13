/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { BlockchainTestModule } from '../../../test.module';
import { BitfinexDialogComponent } from '../../../../../../main/webapp/app/entities/bitfinex/bitfinex-dialog.component';
import { BitfinexService } from '../../../../../../main/webapp/app/entities/bitfinex/bitfinex.service';
import { Bitfinex } from '../../../../../../main/webapp/app/entities/bitfinex/bitfinex.model';
import { CoinBlockChainInfoService } from '../../../../../../main/webapp/app/entities/coin-block-chain-info';

describe('Component Tests', () => {

    describe('Bitfinex Management Dialog Component', () => {
        let comp: BitfinexDialogComponent;
        let fixture: ComponentFixture<BitfinexDialogComponent>;
        let service: BitfinexService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlockchainTestModule],
                declarations: [BitfinexDialogComponent],
                providers: [
                    CoinBlockChainInfoService,
                    BitfinexService
                ]
            })
            .overrideTemplate(BitfinexDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BitfinexDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BitfinexService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Bitfinex(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.bitfinex = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'bitfinexListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Bitfinex();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.bitfinex = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'bitfinexListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
