/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { BlockchainTestModule } from '../../../test.module';
import { MarketComponent } from '../../../../../../main/webapp/app/entities/market/market.component';
import { MarketService } from '../../../../../../main/webapp/app/entities/market/market.service';
import { Market } from '../../../../../../main/webapp/app/entities/market/market.model';

describe('Component Tests', () => {

    describe('Market Management Component', () => {
        let comp: MarketComponent;
        let fixture: ComponentFixture<MarketComponent>;
        let service: MarketService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlockchainTestModule],
                declarations: [MarketComponent],
                providers: [
                    MarketService
                ]
            })
            .overrideTemplate(MarketComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MarketComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarketService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Market(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.markets[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
