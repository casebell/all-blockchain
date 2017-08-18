import { Routes } from '@angular/router';
import { UserRouteAccessService } from '../../shared/index';
import { QnaComponent } from "./qna.component";
import { QnaDetailComponent } from "./qna-detail.component";



export const qnaRoute: Routes = [
    {
        path: 'qna',
        component: QnaComponent,
        data: {
            authorities: [],
            pageTitle: 'qna.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'qna/:id',
        component: QnaDetailComponent,
        data: {
            authorities: [],
            pageTitle: 'qna.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

