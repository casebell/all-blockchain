/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { BlockchainTestModule } from '../../../test.module';
import { KrakenComponent } from '../../../../../../main/webapp/app/entities/kraken/kraken.component';
import { KrakenService } from '../../../../../../main/webapp/app/entities/kraken/kraken.service';
import { Kraken } from '../../../../../../main/webapp/app/entities/kraken/kraken.model';

describe('Component Tests', () => {

    describe('Kraken Management Component', () => {
        let comp: KrakenComponent;
        let fixture: ComponentFixture<KrakenComponent>;
        let service: KrakenService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlockchainTestModule],
                declarations: [KrakenComponent],
                providers: [
                    KrakenService
                ]
            })
            .overrideTemplate(KrakenComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KrakenComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KrakenService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Kraken(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.krakens[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
