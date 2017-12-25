import { Component, OnInit, Inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

import { Observable } from 'rxjs/Observable';
import { startWith } from 'rxjs/operators/startWith';
import { map } from 'rxjs/operators/map';
import { TickerService } from '../../ticker.service';
import { ResponseWrapper } from '../../../shared';
import { Market, MarketService } from '../../../entities/market';
import { CoinService } from '../../../coin/coin.service';
import { Coin } from '../../coin.model';
import { MarketCoinService } from '../../market-coin/market-coin.service';

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
    filteredCoins: Observable<any[]>;

    markets: Market[] = [];
    coins: Coin[] = [];

    selectedMarket:Market;
    constructor(private _formBuilder: FormBuilder,
                private marketService: MarketService,
                private coinService: CoinService,
                private marketCoinService: MarketCoinService,
                public dialogRef: MatDialogRef<AddTickerDialogComponent>,
                @Inject(MAT_DIALOG_DATA) public data: any) {}

    ngOnInit() {

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
            this.marketCoinService.findMarketCoinAll(this.selectedMarket.id).subscribe(
                coins => {
                    console.log('get all Coins', coins);
                    this.coins = coins;
                    this.filteredCoins = this.secondFormGroup.get('coinName').valueChanges
                        .pipe(
                            startWith(''),
                            map(coin => coin ? this.filterCoins(coin) : this.coins.slice())
                        );

                },
                (res) => console.log('error : ', res));
        }

    }
    filterMarkets(name: string) {
        return this.markets.filter(market =>
            market.name.toLowerCase().indexOf(name.toLowerCase()) === 0);
    }

    filterCoins(name: string) {
        return this.coins.filter(coin =>
            coin.name.toLowerCase().indexOf(name.toLowerCase()) === 0);
    }

}
