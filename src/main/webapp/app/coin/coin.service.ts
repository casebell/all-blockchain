import { Injectable } from '@angular/core';
import { Subject } from 'rxjs/Subject';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class CoinService {

     sideNavCloseButtonChanged: Subject<string> = new Subject();
     sideNavOpenButtonChanged: Subject<string> = new Subject();

    constructor() {
    }

    sendSideNavCloseButton(message) {
        this.sideNavCloseButtonChanged.next(message);
    }

    sendSideNavOpenButton(message) {
        this.sideNavOpenButtonChanged.next(message);
    }

 /*   getSidenavOpenButton(): Observable<any> {
        return this.sideNavOpenButtonChanged.asObservable();
    }
    getSidenavCloseButton(): Observable<any> {
        return this.sideNavCloseButtonChanged.asObservable();
    }*/

}
