import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

import { Observable } from 'rxjs/Observable';
import { startWith } from 'rxjs/operators/startWith';
import { map } from 'rxjs/operators/map';
import { TickerService } from '../../ticker.service';
import { Principal, ResponseWrapper } from '../../../shared';
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
                private marketCoinService: MarketCoinService,
                private tickerService: TickerService,
                public dialogRef: MatDialogRef<AddTickerDialogComponent>,
                @Inject(MAT_DIALOG_DATA) public data: any) {}

    ngOnInit() {
        this.principal.identity().then((account) => {
            this.userId = account.id;
        });

        this.marketService.query().subscribe(
            (res: ResponseWrapper) => {
                this.markets = res.json;
                this.filteredMarkets = this.firstFormGroup.get('marketName').valueChanges
                    .pipe(
                        startWith(''),
                        map(market => market ? this.filterMarkets(market) : this.markets.slice())
                    );

            },
            (res: ResponseWrapper) => console.log('error : ', res.json));

        this.firstFormGroup = this._formBuilder.group({
            marketName: ['', Validators.required]
        });

        this.secondFormGroup = this._formBuilder.group({
            coinName: ['', Validators.required]
        });


    }

    selectMarket(event, market){
        console.log('event : ', event);
        console.log('event : ', event.isUserInput);
        console.log('market : ' , market);
        if(event.isUserInput)
        {
            this.selectedMarket = market;
            this.marketCoinService.findMarketCoinAll(this.selectedMarket.id,this.userId).subscribe(
                marketCoins => {
                    this.selectedMarketCoins = [];
                    console.log('get all Coins', marketCoins);
                    this.marketCoins = marketCoins;

                },
                (res) => console.log('error : ', res));
        }

    }

    selectCoin(event, coin){
        console.log('event : ', event);
       // console.log('event : ', event.isUserInput);
        console.log('market : ' , coin);
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
        console.log('selectedMarketCoins',this.selectedMarketCoins);
        this.tickerService.addTickers(this.userId,this.selectedMarketCoins).subscribe(
            (result)=>{
                //console.log('result : ',result);
                this.data=true;
                this.dialogRef.close();
            }
        )
    }

}
