import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { JhiAlertService } from 'ng-jhipster';

import { Observable } from 'rxjs/Observable';
import { startWith } from 'rxjs/operators/startWith';
import { map } from 'rxjs/operators/map';
import { TickerService } from '../../ticker.service';
import { Principal } from '../../../shared';
import { Market, MarketService } from '../../../entities/market';
import { CoinService } from '../../../coin/coin.service';
import { Coin } from '../../coin.model';
import { MarketCoinService } from '../../market-coin/market-coin.service';
import { MarketCoin } from '../../market-coin/market-coin.model';

export class State {
    constructor(public name: string,
                public population: string,
                public flag: string) {
    }
}


@Component({
    selector: 'jhi-add-ticker-dialog',
    templateUrl: './add-ticker-dialog.component.html',
    styleUrls: ['add-ticker-dialog.scss']
})
export class AddTickerDialogComponent implements OnInit {
    firstFormGroup: FormGroup;
    secondFormGroup: FormGroup;
    isLinear = true;
    filteredMarkets: Observable<any[]>;
    //filteredCoins: Observable<any[]>;

    markets: Market[] = [];
    marketCoins: MarketCoin[] = [];

    selectedMarket:Market;
    selectedMarketCoins:MarketCoin[]=[];
    userId;

    constructor(private _formBuilder: FormBuilder,
                private marketService: MarketService,
                private coinService: CoinService,
                private principal: Principal,
                private jhiAlertService: JhiAlertService,
                private marketCoinService: MarketCoinService,
                private tickerService: TickerService,
                public dialogRef: MatDialogRef<AddTickerDialogComponent>,
                @Inject(MAT_DIALOG_DATA) public data: any) {}

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.userId = account.id;
        });

        this.marketService.query().subscribe(
            (res: HttpResponse<MarketCoin[]>) => {
                this.markets = res.body;
                this.filteredMarkets = this.firstFormGroup.get('marketName').valueChanges
                    .pipe(
                        startWith(''),
                        map(market => market ? this.filterMarkets(market) : this.markets.slice())
                    );

            },
            (res: HttpErrorResponse) => this.onError(res.message));

        this.firstFormGroup = this._formBuilder.group({
            marketName: ['', Validators.required]
        });

        this.secondFormGroup = this._formBuilder.group({
            coinName: ['', Validators.required]
        });


    }

    selectMarket(event, market){
        if(event.isUserInput)
        {
            this.selectedMarket = market;
            this.marketCoinService.findMarketCoinAll(this.selectedMarket.id,this.userId).subscribe(
                (res: HttpResponse<MarketCoin[]>) => {
                    this.selectedMarketCoins = [];
                    this.marketCoins = res.body;

                },
                (res) => console.log('error : ', res));
        }

    }

    selectCoin(event, coin){
        if(event.isUserInput && event.source.selected)
        {   if(this.selectedMarketCoins.indexOf(coin) === -1)
                this.selectedMarketCoins.push(coin);
        }

        if(event.isUserInput && !event.source.selected)
        {
            const index = this.selectedMarketCoins.indexOf(coin);
            if(index !== -1)
                this.selectedMarketCoins.splice(index,1);
        }

    }
    filterMarkets(name: string) {
        return this.markets.filter(market =>
            market.name.toLowerCase().indexOf(name.toLowerCase()) === 0);
    }

    addCoin() {
        this.tickerService.addTickers(this.userId,this.selectedMarketCoins).subscribe(
            (result)=>{
                //console.log('result : ',result);

                this.dialogRef.close(true);
            }
        )
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
