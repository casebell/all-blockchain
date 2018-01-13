/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { BlockchainTestModule } from '../../../test.module';
import { BitfinexDetailComponent } from '../../../../../../main/webapp/app/entities/bitfinex/bitfinex-detail.component';
import { BitfinexService } from '../../../../../../main/webapp/app/entities/bitfinex/bitfinex.service';
import { Bitfinex } from '../../../../../../main/webapp/app/entities/bitfinex/bitfinex.model';

describe('Component Tests', () => {

    describe('Bitfinex Management Detail Component', () => {
        let comp: BitfinexDetailComponent;
        let fixture: ComponentFixture<BitfinexDetailComponent>;
        let service: BitfinexService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlockchainTestModule],
                declarations: [BitfinexDetailComponent],
                providers: [
                    BitfinexService
                ]
            })
            .overrideTemplate(BitfinexDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BitfinexDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BitfinexService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Bitfinex(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.bitfinex).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
