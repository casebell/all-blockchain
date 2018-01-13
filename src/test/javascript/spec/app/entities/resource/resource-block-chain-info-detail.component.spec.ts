/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { BlockchainTestModule } from '../../../test.module';
import { ResourceBlockChainInfoDetailComponent } from '../../../../../../main/webapp/app/entities/resource/resource-block-chain-info-detail.component';
import { ResourceBlockChainInfoService } from '../../../../../../main/webapp/app/entities/resource/resource-block-chain-info.service';
import { ResourceBlockChainInfo } from '../../../../../../main/webapp/app/entities/resource/resource-block-chain-info.model';

describe('Component Tests', () => {

    describe('ResourceBlockChainInfo Management Detail Component', () => {
        let comp: ResourceBlockChainInfoDetailComponent;
        let fixture: ComponentFixture<ResourceBlockChainInfoDetailComponent>;
        let service: ResourceBlockChainInfoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlockchainTestModule],
                declarations: [ResourceBlockChainInfoDetailComponent],
                providers: [
                    ResourceBlockChainInfoService
                ]
            })
            .overrideTemplate(ResourceBlockChainInfoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ResourceBlockChainInfoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResourceBlockChainInfoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new ResourceBlockChainInfo(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.resource).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
