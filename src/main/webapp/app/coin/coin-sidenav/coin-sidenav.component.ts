import { Component, OnInit, ViewChild, ElementRef, OnDestroy } from '@angular/core';
import {
    CoinSidenavService,
    CoinSidenavSource
} from './coin-sidenav.service';
import { CoinService } from '../coin.service';
import { Subscription } from 'rxjs/Subscription';
import { MdSidenav } from '@angular/material';

@Component({
    selector: 'abc-coin-sidenav',
    templateUrl: './coin-sidenav.component.html',
    styleUrls: ['coin-sidenav.scss']
})
export class CoinSidenavComponent implements OnInit, OnDestroy {

    displayedColumns = ['name'];
    dataSource: CoinSidenavSource | null;
    buttonSubscription: Subscription;
    @ViewChild('sidenav') sidenav: MdSidenav;

    // @ViewChild('filter') filter: ElementRef;

    constructor(private coinSidenavService: CoinSidenavService,
                private coinService: CoinService) {
        this.buttonSubscription = this.coinService.sideNavOpenButtonChanged
            .subscribe(message => {
                if (message == 'open')
                    this.sidenav.open();
            });
    }

    ngOnInit() {

        this.coinSidenavService.findAll().subscribe(
            coins => {
                console.log('coins : ', coins);
                this.dataSource = new CoinSidenavSource(coins)
            }
        );
        /*    Observable.fromEvent(this.filter.nativeElement, 'keyup')
              .debounceTime(150)
              .distinctUntilChanged()
              .subscribe(() => {
                if (!this.dataSource) { return; }
                this.dataSource.filter = this.filter.nativeElement.value;
              });*/
    }

    ngOnDestroy(): void {
        this.buttonSubscription.unsubscribe();
    }

    sideNavCloseButton() {
        this.sidenav.close();
        this.coinService.sendSideNavCloseButton('close');
    }

}
