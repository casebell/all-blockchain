import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BlockchainSharedModule } from '../shared';
import { NewsHomeComponent } from './news-home/news-home.component';
import { MatInputModule } from '@angular/material/input';
import { newsState } from './news.route';
import { MatTableModule } from '@angular/material/table';
import { MatButtonModule } from '@angular/material/button';
import { NewsComponent } from './news.component';
@NgModule({
    imports: [
        BlockchainSharedModule,
        RouterModule.forChild(newsState),
        MatTableModule,
        MatInputModule,
        MatButtonModule,
    ],
    declarations: [        NewsComponent,
                   NewsHomeComponent],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class NewsModule {}
