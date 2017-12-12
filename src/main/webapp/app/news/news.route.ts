import {  Routes } from '@angular/router';
import { NewsComponent } from './news.component';
import { newsHomeRoute } from './news-home/news-home.route';
const NEWS_ROUTES = [
    newsHomeRoute
];

export const newsState: Routes = [{
    path: 'news',
    component: NewsComponent,
    children: NEWS_ROUTES
}];
