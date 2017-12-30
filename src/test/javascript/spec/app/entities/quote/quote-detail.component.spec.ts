/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { BlockchainTestModule } from '../../../test.module';
import { QuoteDetailComponent } from '../../../../../../main/webapp/app/entities/quote/quote-detail.component';
import { QuoteService } from '../../../../../../main/webapp/app/entities/quote/quote.service';
import { Quote } from '../../../../../../main/webapp/app/entities/quote/quote.model';

describe('Component Tests', () => {

    describe('Quote Management Detail Component', () => {
        let comp: QuoteDetailComponent;
        let fixture: ComponentFixture<QuoteDetailComponent>;
        let service: QuoteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlockchainTestModule],
                declarations: [QuoteDetailComponent],
                providers: [
                    QuoteService
                ]
            })
            .overrideTemplate(QuoteDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(QuoteDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuoteService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Quote(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.quote).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
