import { Route } from '@angular/router';
import { UserRouteAccessService } from '../../shared/index';
import { NewsHomeComponent } from './news-home.component';

export const newsHomeRoute: Route = {
    path: 'home',
    component: NewsHomeComponent,
    data: {
        authorities: [],
        pageTitle: 'news.news-home.title'
    },
    canActivate: [UserRouteAccessService]
};
