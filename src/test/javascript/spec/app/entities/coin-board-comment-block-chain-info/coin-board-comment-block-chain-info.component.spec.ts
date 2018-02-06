/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BlockchainTestModule } from '../../../test.module';
import { CoinBoardCommentBlockChainInfoComponent } from '../../../../../../main/webapp/app/entities/coin-board-comment/coin-board-comment-block-chain-info.component';
import { CoinBoardCommentBlockChainInfoService } from '../../../../../../main/webapp/app/entities/coin-board-comment/coin-board-comment-block-chain-info.service';
import { CoinBoardCommentBlockChainInfo } from '../../../../../../main/webapp/app/entities/coin-board-comment/coin-board-comment-block-chain-info.model';

describe('Component Tests', () => {

    describe('CoinBoardCommentBlockChainInfo Management Component', () => {
        let comp: CoinBoardCommentBlockChainInfoComponent;
        let fixture: ComponentFixture<CoinBoardCommentBlockChainInfoComponent>;
        let service: CoinBoardCommentBlockChainInfoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlockchainTestModule],
                declarations: [CoinBoardCommentBlockChainInfoComponent],
                providers: [
                    CoinBoardCommentBlockChainInfoService
                ]
            })
            .overrideTemplate(CoinBoardCommentBlockChainInfoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CoinBoardCommentBlockChainInfoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CoinBoardCommentBlockChainInfoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new CoinBoardCommentBlockChainInfo(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.coinBoardComments[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
