/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { BlockchainTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CoinBoardCommentBlockChainInfoDetailComponent } from '../../../../../../main/webapp/app/entities/coin-board-comment/coin-board-comment-block-chain-info-detail.component';
import { CoinBoardCommentBlockChainInfoService } from '../../../../../../main/webapp/app/entities/coin-board-comment/coin-board-comment-block-chain-info.service';
import { CoinBoardCommentBlockChainInfo } from '../../../../../../main/webapp/app/entities/coin-board-comment/coin-board-comment-block-chain-info.model';

describe('Component Tests', () => {

    describe('CoinBoardCommentBlockChainInfo Management Detail Component', () => {
        let comp: CoinBoardCommentBlockChainInfoDetailComponent;
        let fixture: ComponentFixture<CoinBoardCommentBlockChainInfoDetailComponent>;
        let service: CoinBoardCommentBlockChainInfoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlockchainTestModule],
                declarations: [CoinBoardCommentBlockChainInfoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CoinBoardCommentBlockChainInfoService,
                    JhiEventManager
                ]
            }).overrideTemplate(CoinBoardCommentBlockChainInfoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CoinBoardCommentBlockChainInfoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CoinBoardCommentBlockChainInfoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CoinBoardCommentBlockChainInfo(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.coinBoardComment).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
