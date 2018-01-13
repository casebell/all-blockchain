/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { BlockchainTestModule } from '../../../test.module';
import { QuoteDialogComponent } from '../../../../../../main/webapp/app/entities/quote/quote-dialog.component';
import { QuoteService } from '../../../../../../main/webapp/app/entities/quote/quote.service';
import { Quote } from '../../../../../../main/webapp/app/entities/quote/quote.model';
import { MarketCoinService } from '../../../../../../main/webapp/app/entities/market-coin';

describe('Component Tests', () => {

    describe('Quote Management Dialog Component', () => {
        let comp: QuoteDialogComponent;
        let fixture: ComponentFixture<QuoteDialogComponent>;
        let service: QuoteService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlockchainTestModule],
                declarations: [QuoteDialogComponent],
                providers: [
                    MarketCoinService,
                    QuoteService
                ]
            })
            .overrideTemplate(QuoteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(QuoteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuoteService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Quote(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.quote = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'quoteListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Quote();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.quote = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'quoteListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
