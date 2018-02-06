/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { BlockchainTestModule } from '../../../test.module';
import { CoinBoardBlockChainInfoDetailComponent } from '../../../../../../main/webapp/app/entities/coin-board/coin-board-block-chain-info-detail.component';
import { CoinBoardBlockChainInfoService } from '../../../../../../main/webapp/app/entities/coin-board/coin-board-block-chain-info.service';
import { CoinBoardBlockChainInfo } from '../../../../../../main/webapp/app/entities/coin-board/coin-board-block-chain-info.model';

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

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new CoinBoardBlockChainInfo(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.coinBoard).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
