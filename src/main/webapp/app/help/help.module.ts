import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BlockchainSharedModule } from '../shared';
import { RouterModule } from '@angular/router';
import { helpState } from './help.route';
import { TermsComponent } from './terms/terms.component';
import { MatTabsModule } from '@angular/material';
import { NoticeComponent } from './notice/notice.component';
import { QnaComponent } from './qna/qna.component';
import { AboutComponent } from './about/about.component';
import { QnaDetailComponent } from './qna/qna-detail.component';

@NgModule({
  imports: [
    BlockchainSharedModule,
    MatTabsModule,
    RouterModule.forRoot(helpState, { useHash: true })
  ],
  declarations: [TermsComponent,
                 NoticeComponent,
                 QnaComponent,
                 AboutComponent,
                 QnaDetailComponent],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlockchainHelpModule { }
