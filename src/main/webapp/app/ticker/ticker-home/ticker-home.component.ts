import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material';
import { AddTickerDialogComponent } from './add-ticker-dialog/add-ticker-dialog.component';
import { TickerService } from '../ticker.service';
import { Principal } from '../../shared';
import { MyTicker } from '../models/my-ticker.model';
import { Observable } from "rxjs/Observable";
import { JhiAlertService } from 'ng-jhipster';

import { ObservableMedia } from '@angular/flex-layout';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import "rxjs/add/operator/map";
import "rxjs/add/operator/takeWhile";
import "rxjs/add/operator/startWith";
import { Ticker } from '../ticker.model';

@Component({
    selector: 'jhi-ticker-home',
    templateUrl: './ticker-home.component.html',
    styleUrls: ['ticker-home.scss']
})
export class TickerHomeComponent implements OnInit {
    public cols: Observable<number>;

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
                private jhiAlertService: JhiAlertService,
                private principal: Principal,
                private tickerService: TickerService,
                private observableMedia: ObservableMedia) {
        this.principal.identity().then((account) => {
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
        const grid = new Map([
            ["xs", 1],
            ["sm", 2],
            ["md", 3],
            ["lg", 4],
            ["xl", 5]
        ]);
        let start: number;
        grid.forEach((cols, mqAlias) => {
            if (this.observableMedia.isActive(mqAlias)) {
                start = cols;
            }
        });
        this.cols = this.observableMedia.asObservable()
            .map(change => {
                return grid.get(change.mqAlias);
            })
            .startWith(start);
        this.getTickers();
    }


    getTickers() {
        this.tickerService.getTickers(this.userId).subscribe((res:HttpResponse<Ticker[]>) => {
            this.myTickers = res.body

        }, (res: HttpErrorResponse) => this.onError(res.message))
    }


    openAddTickerDialog(): void {
        let dialogRef = this.dialog.open(AddTickerDialogComponent);

        dialogRef.afterClosed().subscribe(result => {
            //  this.animal = result;
            if (result) {
                //Todo : patch my ticker
                this.getTickers();
            }
        });
    }

    onClose(event){
        if(event)
            this.getTickers();
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }


    currencyChange(value) {

    }

}
