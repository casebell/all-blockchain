/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { BlockchainTestModule } from '../../../test.module';
import { CoinBoardCommentBlockChainInfoDialogComponent } from '../../../../../../main/webapp/app/entities/coin-board-comment-block-chain-info/coin-board-comment-block-chain-info-dialog.component';
import { CoinBoardCommentBlockChainInfoService } from '../../../../../../main/webapp/app/entities/coin-board-comment-block-chain-info/coin-board-comment-block-chain-info.service';
import { CoinBoardCommentBlockChainInfo } from '../../../../../../main/webapp/app/entities/coin-board-comment-block-chain-info/coin-board-comment-block-chain-info.model';
import { CoinBoardBlockChainInfoService } from '../../../../../../main/webapp/app/entities/coin-board-block-chain-info';
import { UserService } from '../../../../../../main/webapp/app/shared';

describe('Component Tests', () => {

    describe('CoinBoardCommentBlockChainInfo Management Dialog Component', () => {
        let comp: CoinBoardCommentBlockChainInfoDialogComponent;
        let fixture: ComponentFixture<CoinBoardCommentBlockChainInfoDialogComponent>;
        let service: CoinBoardCommentBlockChainInfoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlockchainTestModule],
                declarations: [CoinBoardCommentBlockChainInfoDialogComponent],
                providers: [
                    CoinBoardBlockChainInfoService,
                    UserService,
                    CoinBoardCommentBlockChainInfoService
                ]
            })
            .overrideTemplate(CoinBoardCommentBlockChainInfoDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CoinBoardCommentBlockChainInfoDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CoinBoardCommentBlockChainInfoService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CoinBoardCommentBlockChainInfo(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.coinBoardComment = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'coinBoardCommentListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new CoinBoardCommentBlockChainInfo();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.coinBoardComment = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'coinBoardCommentListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
