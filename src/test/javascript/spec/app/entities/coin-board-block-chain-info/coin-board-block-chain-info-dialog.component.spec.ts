/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { BlockchainTestModule } from '../../../test.module';
import { CoinBoardBlockChainInfoDialogComponent } from '../../../../../../main/webapp/app/entities/coin-board/coin-board-block-chain-info-dialog.component';
import { CoinBoardBlockChainInfoService } from '../../../../../../main/webapp/app/entities/coin-board/coin-board-block-chain-info.service';
import { CoinBoardBlockChainInfo } from '../../../../../../main/webapp/app/entities/coin-board/coin-board-block-chain-info.model';
//import { CoinBlockChainInfoService } from '../../../../../../main/webapp/app/entities/coin-block-chain-info';
import { UserService } from '../../../../../../main/webapp/app/shared';

describe('Component Tests', () => {

    describe('CoinBoardBlockChainInfo Management Dialog Component', () => {
        let comp: CoinBoardBlockChainInfoDialogComponent;
        let fixture: ComponentFixture<CoinBoardBlockChainInfoDialogComponent>;
        let service: CoinBoardBlockChainInfoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlockchainTestModule],
                declarations: [CoinBoardBlockChainInfoDialogComponent],
                providers: [
                    //CoinBlockChainInfoService,
                    UserService,
                    CoinBoardBlockChainInfoService
                ]
            })
            .overrideTemplate(CoinBoardBlockChainInfoDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CoinBoardBlockChainInfoDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CoinBoardBlockChainInfoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CoinBoardBlockChainInfo(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.coinBoard = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'coinBoardListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CoinBoardBlockChainInfo();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.coinBoard = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'coinBoardListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
