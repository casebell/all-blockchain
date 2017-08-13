import { Component, Injectable, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs/Rx';
import { CoinPriceService } from './coin-price.service';
import { Currency } from '../../model/currency.model';
import { zip } from 'rxjs/observable/zip';
import { Subscription } from 'rxjs/Subscription';


@Component({
    selector: 'coin-all-coin-price',
    templateUrl: './coin-price.component.html',
    styleUrls: ['coin-price.scss']
})
export class CoinPriceComponent implements OnInit {
    /*     currencyLists = [
            { value: 'USD', viewValue: 'USD' },
            { value: 'KRW', viewValue: 'KRW' },
            { value: 'EUR', viewValue: 'EUR' },
            { value: 'JPN', viewValue: 'JPN' },
            { value: 'CNY', viewValue: 'CNY' }
        ]; */
    currencyLists = [
        { value: 'USD', viewValue: 'USD' },
        { value: 'KRW', viewValue: 'KRW' }
    ];
    timeLists = [
        { value: 15, viewValue: '15' },
        { value: 30, viewValue: '30' },
        { value: 60, viewValue: '60' },
        { value: 120, viewValue: '120' },
        { value: 180, viewValue: '180' },
        { value: 300, viewValue: '300' },
        { value: 600, viewValue: '600' }
    ];
    myCurrency: string;
    CURRENCY_QUERY = 'https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22USDKRW%22%2C%20%22USDCHF%22)&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=';
    //select * from yahoo.finance.xchange where pair in ("USDKRW", "EURKRW","CNYKRW","JPYKRW","EURUSD","CNYUSD","JPYUSD")
    currency: Currency;
    myUpdateTime = 15;

    bithumbRow = {
        market: 'Bithumb',
        currencies: 'KRW',
        coins: [
            { name: 'btc', price: 0, diff: 0, diffPercent: 0 },
            { name: 'eth', price: 0, diff: 0, diffPercent: 0 },
            { name: 'xrp', price: 0, diff: 0, diffPercent: 0 },
            { name: 'dash', price: 0, diff: 0, diffPercent: 0 },
            { name: 'ltc', price: 0, diff: 0, diffPercent: 0 },
            { name: 'etc', price: 0, diff: 0, diffPercent: 0 },
            { name: 'bch', price: 0, diff: 0, diffPercent: 0 },
            { name: 'zec', price: 0, diff: 0, diffPercent: 0 },
            { name: 'xmr', price: 0, diff: 0, diffPercent: 0 },
            { name: 'zec', price: 0, diff: 0, diffPercent: 0 }
        ]
    }

    korbitRow = {
        market: 'Korbit',
        currencies: 'KRW',
        coins: [
            { name: 'btc', price: 0, diff: 0, diffPercent: 0 },
            { name: 'eth', price: 0, diff: 0, diffPercent: 0 },
            { name: 'xrp', price: 0, diff: 0, diffPercent: 0 },
            { name: 'dash', price: 0, diff: 0, diffPercent: 0 },
            { name: 'ltc', price: 0, diff: 0, diffPercent: 0 },
            { name: 'etc', price: 0, diff: 0, diffPercent: 0 },
            { name: 'bch', price: 0, diff: 0, diffPercent: 0 },
            { name: 'zec', price: 0, diff: 0, diffPercent: 0 },
        { name: 'xmr', price: 0, diff: 0, diffPercent: 0 },
    { name: 'zec', price: 0, diff: 0, diffPercent: 0 }]
    }
    coinoneRow = {
        market: 'Coinone',
        currencies: 'KRW',
        coins: [
            { name: 'btc', price: 0, diff: 0, diffPercent: 0 },
            { name: 'eth', price: 0, diff: 0, diffPercent: 0 },
            { name: 'xrp', price: 0, diff: 0, diffPercent: 0 },
            { name: 'dash', price: 0, diff: 0, diffPercent: 0 },
            { name: 'ltc', price: 0, diff: 0, diffPercent: 0 },
            { name: 'etc', price: 0, diff: 0, diffPercent: 0 },
            { name: 'bch', price: 0, diff: 0, diffPercent: 0 },
            { name: 'zec', price: 0, diff: 0, diffPercent: 0 },
            { name: 'xmr', price: 0, diff: 0, diffPercent: 0 },
        { name: 'zec', price: 0, diff: 0, diffPercent: 0 }]
    };
    poloniexRow = {
        market: 'Poloniex',
        currencies: 'USD',
        coins: [
            { name: 'btc', price: 0, diff: 0, diffPercent: 0 },
            { name: 'eth', price: 0, diff: 0, diffPercent: 0 },
            { name: 'xrp', price: 0, diff: 0, diffPercent: 0 },
            { name: 'dash', price: 0, diff: 0, diffPercent: 0 },
            { name: 'ltc', price: 0, diff: 0, diffPercent: 0 },
            { name: 'etc', price: 0, diff: 0, diffPercent: 0 },
            { name: 'bch', price: 0, diff: 0, diffPercent: 0 },
            { name: 'zec', price: 0, diff: 0, diffPercent: 0 },
            { name: 'xmr', price: 0, diff: 0, diffPercent: 0 },
        { name: 'zec', price: 0, diff: 0, diffPercent: 0 }]
    };

    okCoinCnRow = {
        market: 'OKCoin cn',
        currencies: 'CNY',
        coins: [
            { name: 'btc', price: 0, diff: 0, diffPercent: 0 },
            { name: 'eth', price: 0, diff: 0, diffPercent: 0 },
            { name: 'xrp', price: 0, diff: 0, diffPercent: 0 },
            { name: 'dash', price: 0, diff: 0, diffPercent: 0 },
            { name: 'ltc', price: 0, diff: 0, diffPercent: 0 },
            { name: 'etc', price: 0, diff: 0, diffPercent: 0 },
            { name: 'bch', price: 0, diff: 0, diffPercent: 0 },
            { name: 'zec', price: 0, diff: 0, diffPercent: 0 },
            { name: 'xmr', price: 0, diff: 0, diffPercent: 0 },
        { name: 'zec', price: 0, diff: 0, diffPercent: 0 }]
    };
    bitflyerRow = {
        market: 'bitFlyer',
        currencies: 'JPY',
        coins: [
            { name: 'btc', price: 0, diff: 0, diffPercent: 0 },
            { name: 'eth', price: 0, diff: 0, diffPercent: 0 },
            { name: 'xrp', price: 0, diff: 0, diffPercent: 0 },
            { name: 'dash', price: 0, diff: 0, diffPercent: 0 },
            { name: 'ltc', price: 0, diff: 0, diffPercent: 0 },
            { name: 'etc', price: 0, diff: 0, diffPercent: 0 },
            { name: 'bch', price: 0, diff: 0, diffPercent: 0 },
            { name: 'zec', price: 0, diff: 0, diffPercent: 0 },
            { name: 'xmr', price: 0, diff: 0, diffPercent: 0 },
        { name: 'zec', price: 0, diff: 0, diffPercent: 0 }]
    };
    bittrexRow = {
        market: 'Bittrex',
        currencies: 'USD',
        coins: [
            { name: 'btc', price: 0, diff: 0, diffPercent: 0 },
            { name: 'eth', price: 0, diff: 0, diffPercent: 0 },
            { name: 'xrp', price: 0, diff: 0, diffPercent: 0 },
            { name: 'dash', price: 0, diff: 0, diffPercent: 0 },
            { name: 'ltc', price: 0, diff: 0, diffPercent: 0 },
            { name: 'etc', price: 0, diff: 0, diffPercent: 0 },
            { name: 'bch', price: 0, diff: 0, diffPercent: 0 },
            { name: 'zec', price: 0, diff: 0, diffPercent: 0 },
            { name: 'xmr', price: 0, diff: 0, diffPercent: 0 },
        { name: 'zec', price: 0, diff: 0, diffPercent: 0 }]
    };

    bithumbUnsubscribe: Subscription;
    coinoneUnsubscribe: Subscription;
    korbitUnsubscribe: Subscription;
    okCoinCnUnsubscribe: Subscription;
    bitflyerUnsubscribe: Subscription;
    poloniexUnsubscribe;
    bittrexUnsubscribe;

    constructor(private http: HttpClient, private coinPriceService: CoinPriceService) {
        this.myCurrency = 'KRW';
    }

    ngOnInit() {
        this.initialCoin();
        this.getBithumb();
        this.getKorbit();
        this.getPoloniexBitcoin();
        this.getCoinone();
        this.getOkCoinCn();
        this.getBitflyer();
        this.getBittrex();
    }

    initialCoin() {
        //bithumb
        this.coinPriceService.getBithumb()
            .subscribe(data => {
                for (var i = 0; i < data.length; i++) {
                    this.bithumbRow.coins[i].price = data[i].closing_price;
                }
            });
        //korbit 
        this.coinPriceService.getKorbit()
            .subscribe(data => {
                //btc
                this.korbitRow.coins[0].price = data[0].last;
                //eth
                this.korbitRow.coins[1].price = data[1].last;
                //etc
                this.korbitRow.coins[5].price = data[2].last;
                //xrp
                this.korbitRow.coins[2].price = data[3].last;
            });

        //coinone
        this.coinPriceService.getCoinone()
            .subscribe((data: any) => {
                //btc
                this.coinoneRow.coins[0].price = data.btc.last;
                //eth
                this.coinoneRow.coins[1].price = data.eth.last;
                //xrp
                this.coinoneRow.coins[2].price = data.xrp.last;
                //etc
                this.coinoneRow.coins[5].price = data.etc.last;
            });

        //poloniex
        this.coinPriceService.getPoloniex()
            .subscribe((data: any) => {
                this.poloniexRow.coins[0].price = data.USDT_BTC.last;
                this.poloniexRow.coins[1].price = data.USDT_ETH.last;
                this.poloniexRow.coins[2].price = data.USDT_XRP.last;
                this.poloniexRow.coins[3].price = data.USDT_DASH.last;
                this.poloniexRow.coins[4].price = data.USDT_LTC.last;
                this.poloniexRow.coins[5].price = data.USDT_ETC.last;
                this.poloniexRow.coins[7].price = data.USDT_ZEC.last;
                this.poloniexRow.coins[8].price = data.USDT_XMR.last;
            });

        //okCoinChina
        this.coinPriceService.getOkCoinCn()
            .subscribe(data => {
                this.okCoinCnRow.coins[0].price = data[0].last;
                this.okCoinCnRow.coins[1].price = data[1].last;
                this.okCoinCnRow.coins[4].price = data[2].last;
            }, error => { });
        //bitflyer
        this.coinPriceService.getBitflyer()
            .subscribe((data: any) => {
                //btc
                this.bitflyerRow.coins[0].price = data[0].ltp
            });

             //bittrex
        this.coinPriceService.getBittrex()
            .subscribe((data: any) => {
                  for (var i = 0; i < data.length; i++) {
                    this.bittrexRow.coins[i].price = data[i].last;
                }
            });
    

    }

    getPoloniexBitcoin() {
        this.poloniexUnsubscribe = Observable
            .interval(this.myUpdateTime * 1000)
            .timeInterval()
            .flatMap(() =>this.coinPriceService.getPoloniex())
            .subscribe((data: any) => {
                this.setPoloniex(data)
            });
    }

    getBithumb() {
        this.bithumbUnsubscribe = Observable
            .interval(this.myUpdateTime * 1000)
            .timeInterval()
            .flatMap(() => this.coinPriceService.getBithumb())
            .subscribe((data) => {
                this.setBithumb(data);
            })
    };

    getKorbit() {
        this.korbitUnsubscribe = Observable
            .interval(this.myUpdateTime * 1000)
            .timeInterval()
            .flatMap(() => this.coinPriceService.getKorbit())
            .subscribe(data => {
                this.setKorbit(data);
            })
    };


    getOkCoinCn() {
        this.okCoinCnUnsubscribe = Observable
            .interval(this.myUpdateTime * 1000)
            .timeInterval()
            .flatMap(() => this.coinPriceService.getOkCoinCn())
            .subscribe(data => {
                this.setOkCoinCn(data);
            })
    };

    getBitflyer() {
        this.bitflyerUnsubscribe = Observable
            .interval(this.myUpdateTime * 1000)
            .timeInterval()
            .flatMap(() => this.coinPriceService.getBitflyer())
            .subscribe(data => {
                this.setBitflyer(data);
            })
    };

    getCoinone() {
        this.coinoneUnsubscribe = Observable
            .interval(this.myUpdateTime * 1000)
            .timeInterval()
            .flatMap(() => this.coinPriceService.getCoinone())
            .subscribe((data: any) => {
                this.setCoinOne(data);
            });
    }

      getBittrex() {
        this.bittrexUnsubscribe = Observable
            .interval(this.myUpdateTime * 1000)
            .timeInterval()
            .flatMap(() => this.coinPriceService.getBittrex())
            .subscribe(data => {
                this.setBittrex(data);
            })
    };

    timeChange() {
        if (this.bithumbUnsubscribe != null)
            this.bithumbUnsubscribe.unsubscribe();
        if (this.coinoneUnsubscribe != null)
            this.coinoneUnsubscribe.unsubscribe();
        if (this.korbitUnsubscribe != null)
            this.korbitUnsubscribe.unsubscribe();
        if (this.poloniexUnsubscribe != null)
            this.poloniexUnsubscribe.unsubscribe();
        if (this.okCoinCnUnsubscribe != null)
            this.okCoinCnUnsubscribe.unsubscribe();
        if (this.bitflyerUnsubscribe != null)
            this.bitflyerUnsubscribe.unsubscribe();
        if (this.bittrexUnsubscribe != null)
            this.bittrexUnsubscribe.unsubscribe();
        this.getBithumb();
        this.getKorbit();
        this.getPoloniexBitcoin();
        this.getCoinone();
        this.getOkCoinCn();
        this.getBitflyer();
        this.getBittrex();
    }

    currencyChange(value) {
        console.log(value);
        console.log('currency change : ', this.myCurrency);

    }

    setBithumb(data) {
        for (var i = 0; i < data.length; i++) {
            this.bithumbRow.coins[i].diffPercent = data[i].closing_price * 100 / this.bithumbRow.coins[i].price - 100
            this.bithumbRow.coins[i].diff = data[i].closing_price - this.bithumbRow.coins[i].price;
            this.bithumbRow.coins[i].price = data[i].closing_price
        }
    }

    setKorbit(data) {
        //btc
        this.korbitRow.coins[0].diffPercent = data[0].last * 100 / this.korbitRow.coins[0].price - 100
        this.korbitRow.coins[0].diff = data[0].last - this.korbitRow.coins[0].price;
        this.korbitRow.coins[0].price = data[0].last
        //eth
        this.korbitRow.coins[1].diffPercent = data[1].last * 100 / this.korbitRow.coins[1].price - 100
        this.korbitRow.coins[1].diff = data[1].last - this.korbitRow.coins[1].price;
        this.korbitRow.coins[1].price = data[1].last
        //etc
        this.korbitRow.coins[5].diffPercent = data[2].last * 100 / this.korbitRow.coins[5].price - 100
        this.korbitRow.coins[5].diff = data[2].last - this.korbitRow.coins[5].price;
        this.korbitRow.coins[5].price = data[2].last
        //xrp
        this.korbitRow.coins[2].diffPercent = data[3].last * 100 / this.korbitRow.coins[2].price - 100
        this.korbitRow.coins[2].diff = data[3].last - this.korbitRow.coins[2].price;
        this.korbitRow.coins[2].price = data[3].last
    }

    setCoinOne(data) {
        //btc
        this.coinoneRow.coins[0].diffPercent = data.btc.last * 100 / this.coinoneRow.coins[0].price - 100
        this.coinoneRow.coins[0].diff = data.btc.last - this.coinoneRow.coins[0].price;
        this.coinoneRow.coins[0].price = data.btc.last
        //eth
        this.coinoneRow.coins[1].diffPercent = data.eth.last * 100 / this.coinoneRow.coins[1].price - 100
        this.coinoneRow.coins[1].diff = data.eth.last - this.coinoneRow.coins[1].price;
        this.coinoneRow.coins[1].price = data.eth.last
        //xrp
        this.coinoneRow.coins[2].diffPercent = data.xrp.last * 100 / this.coinoneRow.coins[2].price - 100
        this.coinoneRow.coins[2].diff = data.xrp.last - this.coinoneRow.coins[2].price;
        this.coinoneRow.coins[2].price = data.xrp.last
        //etc
        this.coinoneRow.coins[5].diffPercent = data.etc.last * 100 / this.coinoneRow.coins[5].price - 100
        this.coinoneRow.coins[5].diff = data.etc.last - this.coinoneRow.coins[5].price;
        this.coinoneRow.coins[5].price = data.etc.last
    }
    setPoloniex(data) {
        this.poloniexRow.coins[0].diffPercent = data.USDT_BTC.last * 100 / this.poloniexRow.coins[0].price - 100
        this.poloniexRow.coins[0].diff = data.USDT_BTC.last - this.poloniexRow.coins[0].price;
        this.poloniexRow.coins[0].price = data.USDT_BTC.last;

        this.poloniexRow.coins[1].diffPercent = data.USDT_ETH.last * 100 / this.poloniexRow.coins[1].price - 100
        this.poloniexRow.coins[1].diff = data.USDT_ETH.last - this.poloniexRow.coins[1].price;
        this.poloniexRow.coins[1].price = data.USDT_ETH.last;

        this.poloniexRow.coins[2].diffPercent = data.USDT_XRP.last * 100 / this.poloniexRow.coins[2].price - 100
        this.poloniexRow.coins[2].diff = data.USDT_XRP.last - this.poloniexRow.coins[2].price;
        this.poloniexRow.coins[2].price = data.USDT_XRP.last;

        this.poloniexRow.coins[3].diffPercent = data.USDT_DASH.last * 100 / this.poloniexRow.coins[3].price - 100
        this.poloniexRow.coins[3].diff = data.USDT_DASH.last - this.poloniexRow.coins[3].price;
        this.poloniexRow.coins[3].price = data.USDT_DASH.last;

        this.poloniexRow.coins[4].diffPercent = data.USDT_LTC.last * 100 / this.poloniexRow.coins[4].price - 100
        this.poloniexRow.coins[4].diff = data.USDT_LTC.last - this.poloniexRow.coins[4].price;
        this.poloniexRow.coins[4].price = data.USDT_LTC.last;

        this.poloniexRow.coins[5].diffPercent = data.USDT_ETC.last * 100 / this.poloniexRow.coins[5].price - 100
        this.poloniexRow.coins[5].diff = data.USDT_ETC.last - this.poloniexRow.coins[5].price;
        this.poloniexRow.coins[5].price = data.USDT_ETC.last;

        this.poloniexRow.coins[7].diffPercent = data.USDT_ZEC.last * 100 / this.poloniexRow.coins[7].price - 100
        this.poloniexRow.coins[7].diff = data.USDT_ZEC.last - this.poloniexRow.coins[7].price;
        this.poloniexRow.coins[7].price = data.USDT_ZEC.last;

        this.poloniexRow.coins[8].diffPercent = data.USDT_XMR.last * 100 / this.poloniexRow.coins[8].price - 100
        this.poloniexRow.coins[8].diff = data.USDT_XMR.last - this.poloniexRow.coins[8].price;
        this.poloniexRow.coins[8].price = data.USDT_XMR.last;
    }
    setOkCoinCn(data) {
        this.okCoinCnRow.coins[0].diffPercent = data[0].last * 100 / this.okCoinCnRow.coins[0].price - 100
        this.okCoinCnRow.coins[0].diff = data[0].last - this.okCoinCnRow.coins[0].price;
        this.okCoinCnRow.coins[0].price = data[0].last;

        this.okCoinCnRow.coins[1].diffPercent = data[1].last * 100 / this.okCoinCnRow.coins[1].price - 100
        this.okCoinCnRow.coins[1].diff = data[1].last - this.okCoinCnRow.coins[1].price;
        this.okCoinCnRow.coins[1].price = data[1].last;

        this.okCoinCnRow.coins[4].diffPercent = data[2].last * 100 / this.okCoinCnRow.coins[4].price - 100
        this.okCoinCnRow.coins[4].diff = data[2].last - this.okCoinCnRow.coins[4].price;
        this.okCoinCnRow.coins[4].price = data[2].last;
    }

    setBitflyer(data) {
        //btc
        this.bitflyerRow.coins[0].diffPercent = data[0].ltp * 100 / this.bitflyerRow.coins[0].price - 100
        this.bitflyerRow.coins[0].diff = data[0].ltp - this.bitflyerRow.coins[0].price;
        this.bitflyerRow.coins[0].price = data[0].ltp
    }

     setBittrex(data) {
        for (var i = 0; i < data.length; i++) {
            this.bittrexRow.coins[i].diffPercent = data[i].last * 100 / this.bittrexRow.coins[i].price - 100
            this.bittrexRow.coins[i].diff = data[i].last - this.bittrexRow.coins[i].price;
            this.bittrexRow.coins[i].price = data[i].last
        }
    }

}
