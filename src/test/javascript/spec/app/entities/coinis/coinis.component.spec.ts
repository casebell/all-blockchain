/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { BlockchainTestModule } from '../../../test.module';
import { CoinisComponent } from '../../../../../../main/webapp/app/entities/coinis/coinis.component';
import { CoinisService } from '../../../../../../main/webapp/app/entities/coinis/coinis.service';
import { Coinis } from '../../../../../../main/webapp/app/entities/coinis/coinis.model';

describe('Component Tests', () => {

    describe('Coinis Management Component', () => {
        let comp: CoinisComponent;
        let fixture: ComponentFixture<CoinisComponent>;
        let service: CoinisService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlockchainTestModule],
                declarations: [CoinisComponent],
                providers: [
                    CoinisService
                ]
            })
            .overrideTemplate(CoinisComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CoinisComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CoinisService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Coinis(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.coinis[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
