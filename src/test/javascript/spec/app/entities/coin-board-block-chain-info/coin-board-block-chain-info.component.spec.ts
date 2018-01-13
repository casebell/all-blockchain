/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { BlockchainTestModule } from '../../../test.module';
import { CoinBoardBlockChainInfoComponent } from '../../../../../../main/webapp/app/entities/coin-board/coin-board-block-chain-info.component';
import { CoinBoardBlockChainInfoService } from '../../../../../../main/webapp/app/entities/coin-board/coin-board-block-chain-info.service';
import { CoinBoardBlockChainInfo } from '../../../../../../main/webapp/app/entities/coin-board/coin-board-block-chain-info.model';

describe('Component Tests', () => {

    describe('CoinBoardBlockChainInfo Management Component', () => {
        let comp: CoinBoardBlockChainInfoComponent;
        let fixture: ComponentFixture<CoinBoardBlockChainInfoComponent>;
        let service: CoinBoardBlockChainInfoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlockchainTestModule],
                declarations: [CoinBoardBlockChainInfoComponent],
                providers: [
                    CoinBoardBlockChainInfoService
                ]
            })
            .overrideTemplate(CoinBoardBlockChainInfoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CoinBoardBlockChainInfoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CoinBoardBlockChainInfoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new CoinBoardBlockChainInfo(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.coinBoards[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
