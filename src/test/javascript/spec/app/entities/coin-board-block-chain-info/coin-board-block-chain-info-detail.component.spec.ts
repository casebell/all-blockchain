/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { BlockchainTestModule } from '../../../test.module';
import { CoinBoardBlockChainInfoDetailComponent } from '../../../../../../main/webapp/app/entities/coin-board-block-chain-info/coin-board-block-chain-info-detail.component';
import { CoinBoardBlockChainInfoService } from '../../../../../../main/webapp/app/entities/coin-board-block-chain-info/coin-board-block-chain-info.service';
import { CoinBoardBlockChainInfo } from '../../../../../../main/webapp/app/entities/coin-board-block-chain-info/coin-board-block-chain-info.model';

describe('Component Tests', () => {

    describe('CoinBoardBlockChainInfo Management Detail Component', () => {
        let comp: CoinBoardBlockChainInfoDetailComponent;
        let fixture: ComponentFixture<CoinBoardBlockChainInfoDetailComponent>;
        let service: CoinBoardBlockChainInfoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlockchainTestModule],
                declarations: [CoinBoardBlockChainInfoDetailComponent],
                providers: [
                    CoinBoardBlockChainInfoService
                ]
            })
            .overrideTemplate(CoinBoardBlockChainInfoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CoinBoardBlockChainInfoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CoinBoardBlockChainInfoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new CoinBoardBlockChainInfo(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.coinBoard).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
