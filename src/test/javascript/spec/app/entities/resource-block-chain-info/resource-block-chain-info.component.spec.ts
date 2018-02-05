/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BlockchainTestModule } from '../../../test.module';
import { ResourceBlockChainInfoComponent } from '../../../../../../main/webapp/app/entities/resource/resource-block-chain-info.component';
import { ResourceBlockChainInfoService } from '../../../../../../main/webapp/app/entities/resource/resource-block-chain-info.service';
import { ResourceBlockChainInfo } from '../../../../../../main/webapp/app/entities/resource/resource-block-chain-info.model';

describe('Component Tests', () => {

    describe('ResourceBlockChainInfo Management Component', () => {
        let comp: ResourceBlockChainInfoComponent;
        let fixture: ComponentFixture<ResourceBlockChainInfoComponent>;
        let service: ResourceBlockChainInfoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlockchainTestModule],
                declarations: [ResourceBlockChainInfoComponent],
                providers: [
                    ResourceBlockChainInfoService
                ]
            })
            .overrideTemplate(ResourceBlockChainInfoComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ResourceBlockChainInfoComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ResourceBlockChainInfoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new ResourceBlockChainInfo(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.resources[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
