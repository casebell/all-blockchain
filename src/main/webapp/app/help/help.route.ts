import { Routes } from '@angular/router';
import { termsRoute } from './terms/terms.route'
import { qnaRoute } from './qna/qna.route';
import { noticeRoute } from './notice/notice.route';
import { aboutRoute } from './about/about.route';

const HELP_ROUTES = [
    termsRoute,
    ...qnaRoute,
    ...noticeRoute,
    aboutRoute
];

export const helpState: Routes = [{
    path: '',
    children: HELP_ROUTES
}];
