import {  Routes } from '@angular/router';
import { UserRouteAccessService } from '../../shared/index';
import { NoticeComponent } from "./notice.component";

export const noticeRoute: Routes = [{
    path: 'notice',
    component: NoticeComponent,
    data: {
        authorities: [],
        pageTitle: 'notice.title'
    },
    canActivate: [UserRouteAccessService]
}];

