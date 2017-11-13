/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { BlockchainTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CoinBlockChainInfoDetailComponent } from '../../../../../../main/webapp/app/entities/coin/coin-block-chain-info-detail.component';
import { CoinBlockChainInfoService } from '../../../../../../main/webapp/app/entities/coin/coin-block-chain-info.service';
import { CoinBlockChainInfo } from '../../../../../../main/webapp/app/entities/coin/coin-block-chain-info.model';

describe('Component Tests', () => {

    describe('CoinBlockChainInfo Management Detail Component', () => {
        let comp: CoinBlockChainInfoDetailComponent;
        let fixture: ComponentFixture<CoinBlockChainInfoDetailComponent>;
        let service: CoinBlockChainInfoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlockchainTestModule],
                declarations: [CoinBlockChainInfoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CoinBlockChainInfoService,
                    JhiEventManager
                ]
            }).overrideTemplate(CoinBlockChainInfoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CoinBlockChainInfoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CoinBlockChainInfoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CoinBlockChainInfo(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.coin).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
