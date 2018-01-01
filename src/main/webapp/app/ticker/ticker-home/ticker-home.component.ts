import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material';
import { AddTickerDialogComponent } from './add-ticker-dialog/add-ticker-dialog.component';
import { TickerService } from '../ticker.service';
import { Principal } from '../../shared';
import { MyTicker } from '../models/my-ticker.model';

@Component({
    selector: 'jhi-ticker-home',
    templateUrl: './ticker-home.component.html',
    styleUrls: ['ticker-home.scss']
})
export class TickerHomeComponent implements OnInit {

    currencyLists = [
        {value: 'USD', viewValue: 'USD'},
        {value: 'KRW', viewValue: 'KRW'}
    ];
    userId: number;
    langKey;
    myTickers: MyTicker[] = [];
    grid;
    myCurrency: string;
    constructor(public dialog: MatDialog,
                private principal: Principal,
                private tickerService: TickerService) {
        this.principal.identity().then((account) => {
            console.log('account',account);
            this.userId = account.id;
            if(account.langKey == 'ko') {
                this.myCurrency = 'KRW'
            } else if(account.langKey =='en') {
                this.myCurrency = 'USD'
            } else {
                this.myCurrency = 'KRW'
            }
        });
    }

    ngOnInit() {
        this.getTickers();
    }


    getTickers() {
        this.tickerService.getTickers(this.userId).subscribe((result) => {
            console.log('get Tickers : ', result);
            this.myTickers = result.json;
            console.log('  this.myTickers : ', this.myTickers);

        })
    }


    openAddTickerDialog(): void {
        let dialogRef = this.dialog.open(AddTickerDialogComponent);

        dialogRef.afterClosed().subscribe(result => {
            console.log('The dialog was closed');
            //  this.animal = result;
            if (result) {
                //Todo : patch my ticker
                this.getTickers();
            }
        });
    }

    currencyChange(value) {

    }

}
