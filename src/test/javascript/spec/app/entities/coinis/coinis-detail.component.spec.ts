/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { BlockchainTestModule } from '../../../test.module';
import { CoinisDetailComponent } from '../../../../../../main/webapp/app/entities/coinis/coinis-detail.component';
import { CoinisService } from '../../../../../../main/webapp/app/entities/coinis/coinis.service';
import { Coinis } from '../../../../../../main/webapp/app/entities/coinis/coinis.model';

describe('Component Tests', () => {

    describe('Coinis Management Detail Component', () => {
        let comp: CoinisDetailComponent;
        let fixture: ComponentFixture<CoinisDetailComponent>;
        let service: CoinisService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlockchainTestModule],
                declarations: [CoinisDetailComponent],
                providers: [
                    CoinisService
                ]
            })
            .overrideTemplate(CoinisDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CoinisDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CoinisService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Coinis(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.coinis).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
