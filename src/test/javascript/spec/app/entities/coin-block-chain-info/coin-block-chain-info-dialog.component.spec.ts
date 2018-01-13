/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { BlockchainTestModule } from '../../../test.module';
import { CoinBlockChainInfoDialogComponent } from '../../../../../../main/webapp/app/entities/coin/coin-block-chain-info-dialog.component';
import { CoinBlockChainInfoService } from '../../../../../../main/webapp/app/entities/coin/coin-block-chain-info.service';
import { CoinBlockChainInfo } from '../../../../../../main/webapp/app/entities/coin/coin-block-chain-info.model';

describe('Component Tests', () => {

    describe('CoinBlockChainInfo Management Dialog Component', () => {
        let comp: CoinBlockChainInfoDialogComponent;
        let fixture: ComponentFixture<CoinBlockChainInfoDialogComponent>;
        let service: CoinBlockChainInfoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlockchainTestModule],
                declarations: [CoinBlockChainInfoDialogComponent],
                providers: [
                    CoinBlockChainInfoService
                ]
            })
            .overrideTemplate(CoinBlockChainInfoDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CoinBlockChainInfoDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CoinBlockChainInfoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CoinBlockChainInfo(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.coin = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'coinListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CoinBlockChainInfo();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.coin = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'coinListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
