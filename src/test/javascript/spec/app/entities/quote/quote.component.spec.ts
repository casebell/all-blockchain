/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BlockchainTestModule } from '../../../test.module';
import { QuoteComponent } from '../../../../../../main/webapp/app/entities/quote/quote.component';
import { QuoteService } from '../../../../../../main/webapp/app/entities/quote/quote.service';
import { Quote } from '../../../../../../main/webapp/app/entities/quote/quote.model';

describe('Component Tests', () => {

    describe('Quote Management Component', () => {
        let comp: QuoteComponent;
        let fixture: ComponentFixture<QuoteComponent>;
        let service: QuoteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlockchainTestModule],
                declarations: [QuoteComponent],
                providers: [
                    QuoteService
                ]
            })
            .overrideTemplate(QuoteComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(QuoteComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuoteService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Quote(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.quotes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
