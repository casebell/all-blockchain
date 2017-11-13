import { Component, OnDestroy, OnInit } from '@angular/core';
import { CoinService } from './coin.service';
import { Subscription } from 'rxjs/Subscription';

@Component({
    selector: 'abc-coin',
    templateUrl: './coin.component.html',
    styleUrls: ['coin.scss']

})
export class CoinComponent implements OnInit, OnDestroy {

    buttonSubscription: Subscription;
    closeButton = false;

    constructor(private coinService: CoinService) {
        this.buttonSubscription = this.coinService.sideNavCloseButtonChanged
            .subscribe((message) => {
                if (message == 'close')
                    this.closeButton = true;
            })
    }

    ngOnInit() {
        this.closeButton = false;
    }

    ngOnDestroy(): void {
        this.buttonSubscription.unsubscribe();
    }

    sideNaveOpenButton() {
        this.closeButton = false;
        this.coinService.sendSideNavOpenButton('open');
    }
}
