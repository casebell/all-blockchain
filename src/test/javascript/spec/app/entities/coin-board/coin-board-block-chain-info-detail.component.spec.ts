/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { BlockchainTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
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
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CoinBoardBlockChainInfoService,
                    JhiEventManager
                ]
            }).overrideTemplate(CoinBoardBlockChainInfoDetailComponent, '')
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

            spyOn(service, 'find').and.returnValue(Observable.of(new CoinBoardBlockChainInfo(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.coinBoard).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
