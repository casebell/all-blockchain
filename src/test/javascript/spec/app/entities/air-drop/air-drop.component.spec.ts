/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BlockchainTestModule } from '../../../test.module';
import { AirDropComponent } from '../../../../../../main/webapp/app/entities/air-drop/air-drop.component';
import { AirDropService } from '../../../../../../main/webapp/app/entities/air-drop/air-drop.service';
import { AirDrop } from '../../../../../../main/webapp/app/entities/air-drop/air-drop.model';

describe('Component Tests', () => {

    describe('AirDrop Management Component', () => {
        let comp: AirDropComponent;
        let fixture: ComponentFixture<AirDropComponent>;
        let service: AirDropService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlockchainTestModule],
                declarations: [AirDropComponent],
                providers: [
                    AirDropService
                ]
            })
            .overrideTemplate(AirDropComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AirDropComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AirDropService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new AirDrop(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.airDrops[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
