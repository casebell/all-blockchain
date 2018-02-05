/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BlockchainTestModule } from '../../../test.module';
import { CoinBlockChainInfoComponent } from '../../../../../../main/webapp/app/entities/coin/coin-block-chain-info.component';
import { CoinBlockChainInfoService } from '../../../../../../main/webapp/app/entities/coin/coin-block-chain-info.service';
import { CoinBlockChainInfo } from '../../../../../../main/webapp/app/entities/coin/coin-block-chain-info.model';

describe('Component Tests', () => {

    describe('CoinBlockChainInfo Management Component', () => {
        let comp: CoinBlockChainInfoComponent;
        let fixture: ComponentFixture<CoinBlockChainInfoComponent>;
        let service: CoinBlockChainInfoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlockchainTestModule],
                declarations: [CoinBlockChainInfoComponent],
                providers: [
                    CoinBlockChainInfoService
                ]
            })
            .overrideTemplate(CoinBlockChainInfoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CoinBlockChainInfoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CoinBlockChainInfoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new CoinBlockChainInfo(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.coins[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
