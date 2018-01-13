/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { BlockchainTestModule } from '../../../test.module';
import { BitfinexComponent } from '../../../../../../main/webapp/app/entities/bitfinex/bitfinex.component';
import { BitfinexService } from '../../../../../../main/webapp/app/entities/bitfinex/bitfinex.service';
import { Bitfinex } from '../../../../../../main/webapp/app/entities/bitfinex/bitfinex.model';

describe('Component Tests', () => {

    describe('Bitfinex Management Component', () => {
        let comp: BitfinexComponent;
        let fixture: ComponentFixture<BitfinexComponent>;
        let service: BitfinexService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlockchainTestModule],
                declarations: [BitfinexComponent],
                providers: [
                    BitfinexService
                ]
            })
            .overrideTemplate(BitfinexComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BitfinexComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BitfinexService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Bitfinex(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.bitfinexes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
