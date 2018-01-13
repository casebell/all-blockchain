/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { BlockchainTestModule } from '../../../test.module';
import { KrakenDetailComponent } from '../../../../../../main/webapp/app/entities/kraken/kraken-detail.component';
import { KrakenService } from '../../../../../../main/webapp/app/entities/kraken/kraken.service';
import { Kraken } from '../../../../../../main/webapp/app/entities/kraken/kraken.model';

describe('Component Tests', () => {

    describe('Kraken Management Detail Component', () => {
        let comp: KrakenDetailComponent;
        let fixture: ComponentFixture<KrakenDetailComponent>;
        let service: KrakenService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlockchainTestModule],
                declarations: [KrakenDetailComponent],
                providers: [
                    KrakenService
                ]
            })
            .overrideTemplate(KrakenDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(KrakenDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(KrakenService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Kraken(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.kraken).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
