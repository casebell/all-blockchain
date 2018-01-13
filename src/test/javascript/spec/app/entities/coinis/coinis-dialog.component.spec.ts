/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { BlockchainTestModule } from '../../../test.module';
import { CoinisDialogComponent } from '../../../../../../main/webapp/app/entities/coinis/coinis-dialog.component';
import { CoinisService } from '../../../../../../main/webapp/app/entities/coinis/coinis.service';
import { Coinis } from '../../../../../../main/webapp/app/entities/coinis/coinis.model';

describe('Component Tests', () => {

    describe('Coinis Management Dialog Component', () => {
        let comp: CoinisDialogComponent;
        let fixture: ComponentFixture<CoinisDialogComponent>;
        let service: CoinisService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlockchainTestModule],
                declarations: [CoinisDialogComponent],
                providers: [
                    CoinisService
                ]
            })
            .overrideTemplate(CoinisDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CoinisDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CoinisService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Coinis(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.coinis = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'coinisListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Coinis();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.coinis = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'coinisListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
