/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { BlockchainTestModule } from '../../../test.module';
import { CoinBlockChainInfoDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/coin-block-chain-info/coin-block-chain-info-delete-dialog.component';
import { CoinBlockChainInfoService } from '../../../../../../main/webapp/app/entities/coin-block-chain-info/coin-block-chain-info.service';

describe('Component Tests', () => {

    describe('CoinBlockChainInfo Management Delete Component', () => {
        let comp: CoinBlockChainInfoDeleteDialogComponent;
        let fixture: ComponentFixture<CoinBlockChainInfoDeleteDialogComponent>;
        let service: CoinBlockChainInfoService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlockchainTestModule],
                declarations: [CoinBlockChainInfoDeleteDialogComponent],
                providers: [
                    CoinBlockChainInfoService
                ]
            })
            .overrideTemplate(CoinBlockChainInfoDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CoinBlockChainInfoDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CoinBlockChainInfoService);
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
