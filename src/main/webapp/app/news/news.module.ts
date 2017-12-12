import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BlockchainSharedModule } from '../shared';
import { NewsHomeComponent } from './news-home/news-home.component';
import { NewsComponent } from './news.component';
import { newsState } from './news.route';

@NgModule({
    imports: [
        BlockchainSharedModule,
        RouterModule.forChild(newsState)
    ],
    declarations: [NewsComponent,
                   NewsHomeComponent],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewsModule {}
