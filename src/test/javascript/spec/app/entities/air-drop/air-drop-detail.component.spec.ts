/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { BlockchainTestModule } from '../../../test.module';
import { AirDropDetailComponent } from '../../../../../../main/webapp/app/entities/air-drop/air-drop-detail.component';
import { AirDropService } from '../../../../../../main/webapp/app/entities/air-drop/air-drop.service';
import { AirDrop } from '../../../../../../main/webapp/app/entities/air-drop/air-drop.model';

describe('Component Tests', () => {

    describe('AirDrop Management Detail Component', () => {
        let comp: AirDropDetailComponent;
        let fixture: ComponentFixture<AirDropDetailComponent>;
        let service: AirDropService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlockchainTestModule],
                declarations: [AirDropDetailComponent],
                providers: [
                    AirDropService
                ]
            })
            .overrideTemplate(AirDropDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AirDropDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AirDropService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new AirDrop(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.airDrop).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
