/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { BlockchainTestModule } from '../../../test.module';
import { MarketDetailComponent } from '../../../../../../main/webapp/app/entities/market/market-detail.component';
import { MarketService } from '../../../../../../main/webapp/app/entities/market/market.service';
import { Market } from '../../../../../../main/webapp/app/entities/market/market.model';

describe('Component Tests', () => {

    describe('Market Management Detail Component', () => {
        let comp: MarketDetailComponent;
        let fixture: ComponentFixture<MarketDetailComponent>;
        let service: MarketService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlockchainTestModule],
                declarations: [MarketDetailComponent],
                providers: [
                    MarketService
                ]
            })
            .overrideTemplate(MarketDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MarketDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MarketService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Market(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.market).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
