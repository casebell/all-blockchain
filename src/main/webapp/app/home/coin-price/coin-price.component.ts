import {Component, OnInit} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Rx';
import {CoinPriceService} from './coin-price.service';
import {Currency} from '../../model/currency.model';
import {Subscription} from 'rxjs/Subscription';
import {zip} from 'rxjs/observable/zip';
import { CoinPrice } from '../../model/coin-price.model.';
import * as _ from 'lodash';
import {ExchangeRateService} from "./coin-price-row/exchange-rate.service";

@Component({
    selector: 'abc-coin-price',
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
        {value: 'USD', viewValue: 'USD'},
        {value: 'KRW', viewValue: 'KRW'}
    ];
    timeLists = [
        {value: 5, viewValue: '5'},
        {value: 10, viewValue: '10'},
        {value: 15, viewValue: '15'},
        {value: 30, viewValue: '30'},
        {value: 60, viewValue: '60'},
        {value: 120, viewValue: '120'},
        {value: 180, viewValue: '180'},
        {value: 300, viewValue: '300'},
        {value: 600, viewValue: '600'}
    ];
    myCurrency: string;
    currency: Currency;
    myUpdateTime = 5;

    coins = [
        {name: 'btc', price: 0, diff: 0, diffPercent: 0},
        {name: 'eth', price: 0, diff: 0, diffPercent: 0},
        {name: 'xrp', price: 0, diff: 0, diffPercent: 0},
        {name: 'dash', price: 0, diff: 0, diffPercent: 0},
        {name: 'ltc', price: 0, diff: 0, diffPercent: 0},
        {name: 'etc', price: 0, diff: 0, diffPercent: 0},
        {name: 'bch', price: 0, diff: 0, diffPercent: 0},
        {name: 'zec', price: 0, diff: 0, diffPercent: 0},
        {name: 'xmr', price: 0, diff: 0, diffPercent: 0},
        {name: 'neo', price: 0, diff: 0, diffPercent: 0},
        {name: 'qtum', price: 0, diff: 0, diffPercent: 0}
    ];

    bithumbRow :CoinPrice;
    korbitRow :CoinPrice;
    coinoneRow :CoinPrice;
    poloniexRow :CoinPrice;
    okCoinCnRow :CoinPrice;
    bitflyerRow :CoinPrice;
    bittrexRow  :CoinPrice;
    coinisRow  :CoinPrice;
    krakenRow :CoinPrice;
    yunbiRow :CoinPrice;
    bitfinexRow :CoinPrice;

    bithumbUnsubscribe: Subscription;
    coinoneUnsubscribe: Subscription;
    korbitUnsubscribe: Subscription;
    okCoinCnUnsubscribe: Subscription;
    bitflyerUnsubscribe: Subscription;
    krakenUnsubscribe: Subscription;
    poloniexUnsubscribe: Subscription;
    bittrexUnsubscribe: Subscription;
    coinisUnsubscribe: Subscription;
    yunbiUnsubscribe: Subscription;
    bitfinexUnsubscribe: Subscription;

    constructor(private http: HttpClient,
                private coinPriceService: CoinPriceService) {
        this.myCurrency = 'KRW';
        this.initialCoinRow();
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
        this.getKraken();
        this.getCoinis();
    }

    initialCoinRow(){
        this.bithumbRow = {
            market: 'Bithumb',
            currencies : 'KRW',
            coins : _.cloneDeep(this.coins)
        };
        this.korbitRow = {
            market: 'Korbit',
            currencies : 'KRW',
            coins :_.cloneDeep(this.coins)
        };
        this.coinoneRow = {
            market: 'Coinone',
            currencies : 'KRW',
            coins : _.cloneDeep(this.coins)
        };
        this.coinisRow = {
            market: 'Coinis',
            currencies : 'KRW',
            coins : _.cloneDeep(this.coins)
        };
        this.poloniexRow = {
            market: 'Poloniex',
            currencies : 'USD',
            coins : _.cloneDeep(this.coins)
        };
        this.okCoinCnRow = {
            market: 'OKCoin',
            currencies : 'CNY',
            coins : _.cloneDeep(this.coins)
        };
        this.bitflyerRow = {
            market: 'BitFlyer',
            currencies : 'JPY',
            coins : _.cloneDeep(this.coins)
        };
        this.bittrexRow = {
            market: 'Bittrex',
            currencies : 'USD',
            coins : _.cloneDeep(this.coins)
        };
        this.bitfinexRow = {
            market: 'Bitfinex',
            currencies : 'USD',
            coins : _.cloneDeep(this.coins)
        };
        this.krakenRow = {
            market: 'Kraken',
            currencies : 'EUR',
            coins : _.cloneDeep(this.coins)
        };
        this.yunbiRow = {
            market: 'Yunbi',
            currencies : 'CNY',
            coins : _.cloneDeep(this.coins)
        };
    }
    initialCoin() {
        // bithumb
        this.coinPriceService.getBithumb()
            .subscribe(data => {
                for (let i = 0; i < data.length-1; i++) {
                    this.bithumbRow.coins[i].price = data[i].closing_price;
                }
                this.bithumbRow.coins[8].price = data[7].closing_price;
            });
        // korbit
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

        // coinone
        this.coinPriceService.getCoinone()
            .subscribe((data: any) => {
                // btc
                this.coinoneRow.coins[0].price = data.btc.last;
                // eth
                this.coinoneRow.coins[1].price = data.eth.last;
                // xrp
                this.coinoneRow.coins[2].price = data.xrp.last;
                // etc
                this.coinoneRow.coins[5].price = data.etc.last;
                // bch
                this.coinoneRow.coins[6].price = data.bch.last;

                this.coinoneRow.coins[10].price = data.qtum.last;
            });

        // poloniex
        this.coinPriceService.getPoloniex()
            .subscribe((data: any) => {
                this.poloniexRow.coins[0].price = data.USDT_BTC.last;
                this.poloniexRow.coins[1].price = data.USDT_ETH.last;
                this.poloniexRow.coins[2].price = data.USDT_XRP.last;
                this.poloniexRow.coins[3].price = data.USDT_DASH.last;
                this.poloniexRow.coins[4].price = data.USDT_LTC.last;
                this.poloniexRow.coins[5].price = data.USDT_ETC.last;
                this.poloniexRow.coins[6].price = data.USDT_BCH.last;
                this.poloniexRow.coins[7].price = data.USDT_ZEC.last;
                this.poloniexRow.coins[8].price = data.USDT_XMR.last;
            });

        // okCoinChina
        this.coinPriceService.getOkCoinCn()
            .subscribe(data => {
                this.okCoinCnRow.coins[0].price = data[0].last;
                this.okCoinCnRow.coins[1].price = data[1].last;
                this.okCoinCnRow.coins[4].price = data[2].last;
                this.okCoinCnRow.coins[5].price = data[3].last;
                this.okCoinCnRow.coins[6].price = data[4].last;
            }, error => {
            });
        // bitflyer
        this.coinPriceService.getBitflyer()
            .subscribe((data: any) => {
                //btc
                this.bitflyerRow.coins[0].price = data[0].ltp
            });

        // bittrex
        this.coinPriceService.getBittrex()
            .subscribe((data: any) => {
                for (let i = 0; i < data.length; i++) {
                    this.bittrexRow.coins[i].price = data[i].last;
                }
            });

        this.coinPriceService.getKrakens()
            .subscribe((data: any) => {
                for (let i = 0; i < data.length; i++) {
                    this.krakenRow.coins[i].price = data[i].last;
                }
            });

        this.coinPriceService.getCoinis()
            .subscribe((data: any) => {
                this.coinisRow.coins[0].price = data[0].closeprice;
                this.coinisRow.coins[3].price = data[1].closeprice;
                this.coinisRow.coins[4].price = data[2].closeprice;
                this.coinisRow.coins[7].price = data[3].closeprice;
                this.coinisRow.coins[8].price = data[4].closeprice;
            });


        this.coinPriceService.getYunbis()
            .subscribe((data:any) => {

                this.yunbiRow.coins[0].price = data.btccny.ticker.last;
                this.yunbiRow.coins[1].price = data.ethcny.ticker.last;
                this.yunbiRow.coins[5].price = data.etccny.ticker.last;
                this.yunbiRow.coins[6].price = data.bcccny.ticker.last;
                this.yunbiRow.coins[7].price = data.zeccny.ticker.last;
                this.yunbiRow.coins[9].price = data.anscny.ticker.last;
                this.yunbiRow.coins[10].price = data.qtumcny.ticker.last;
            })

        this.coinPriceService.getBitfinex()
            .subscribe((data)=>{
                for (let i = 0; i < data.length; i++) {
                    this.bitfinexRow.coins[i].price = data[i].last_price;
                }
            })

        // Observable.zip(this.coinPriceService.getBitfinex('BTCEUR'),
        //     this.coinPriceService.getBitfinex('ETHEUR'),
        //     this.coinPriceService.getBitfinex('XRPEUR'),
        //     this.coinPriceService.getBitfinex('DASHEUR'),
        //     this.coinPriceService.getBitfinex('LTCEUR'),
        //     this.coinPriceService.getBitfinex('ETCEUR'),
        //     this.coinPriceService.getBitfinex('BCHEUR'),
        //     this.coinPriceService.getBitfinex('ZECEUR'),
        //     this.coinPriceService.getBitfinex('XMREUR'))
        //     .subscribe(([btc, eth, xrp, dash, ltc, etc, bch, zec, xmr]) => {
        //
        //         this.krakenRow.coins[0].price = btc.result.XXBTZEUR.c[0];
        //         this.krakenRow.coins[1].price = eth.result.XETHZEUR.c[0];
        //         this.krakenRow.coins[2].price = xrp.result.XXRPZEUR.c[0];
        //         this.krakenRow.coins[3].price = dash.result.DASHEUR.c[0];
        //         this.krakenRow.coins[4].price = ltc.result.XLTCZEUR.c[0];
        //         this.krakenRow.coins[5].price = etc.result.XETCZEUR.c[0];
        //         this.krakenRow.coins[6].price = bch.result.BCHEUR.c[0];
        //         this.krakenRow.coins[7].price = zec.result.XZECZEUR.c[0];
        //         this.krakenRow.coins[8].price = xmr.result.XXMRZEUR.c[0];
        //     });
    }

    getPoloniexBitcoin() {
        this.poloniexUnsubscribe = Observable
            .interval(this.myUpdateTime * 1000)
            .timeInterval()
            .flatMap(() => this.coinPriceService.getPoloniex())
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

    getKraken() {
        this.krakenUnsubscribe = Observable
            .interval(this.myUpdateTime * 1000)
            .timeInterval()
            .flatMap(() => this.coinPriceService.getKrakens())
            .subscribe(data => {
                this.setKraken(data);
            })
        };

    getCoinis() {
        this.coinisUnsubscribe = Observable
            .interval(this.myUpdateTime * 1000)
            .timeInterval()
            .flatMap(() => this.coinPriceService.getCoinis())
            .subscribe(data => {
                this.setCoinis(data);
            })

    };

    getYunbi() {
        this.yunbiUnsubscribe = Observable
            .interval(this.myUpdateTime * 1000)
            .timeInterval()
            .flatMap(() => this.coinPriceService.getYunbis())
            .subscribe(data => {
                this.setYunbis(data);
            })

    };

    getBitfinex() {
        this.bitfinexUnsubscribe = Observable
            .interval(this.myUpdateTime * 1000)
            .timeInterval()
            .flatMap(() => this.coinPriceService.getBitfinex())
            .subscribe(data => {
                this.setBitfinex(data);
            })

    };

    // this.bitfinexUnsubscribe = Observable
    //         .interval(this.myUpdateTime * 1000)
    //         .timeInterval()
    //         .flatMap(() => zip(this.coinPriceService.getBitfinex('BTCEUR'),
    //             this.coinPriceService.getBitfinex('ETHEUR'),
    //             this.coinPriceService.getBitfinex('XRPEUR'),
    //             this.coinPriceService.getBitfinex('DASHEUR'),
    //             this.coinPriceService.getBitfinex('LTCEUR'),
    //             this.coinPriceService.getBitfinex('ETCEUR'),
    //             this.coinPriceService.getBitfinex('BCHEUR'),
    //             this.coinPriceService.getBitfinex('ZECEUR'),
    //             this.coinPriceService.getBitfinex('XMREUR'),
    //             this.coinPriceService.getBitfinex('ETHEUR')))
    //         .subscribe(([btc, eth, xrp, dash, ltc, etc, bch, zec, xmr]) => {
    //             this.setKraken(btc, eth, xrp, dash, ltc, etc, bch, zec, xmr);
    //         });

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
        if (this.krakenUnsubscribe != null)
            this.krakenUnsubscribe.unsubscribe();
        if (this.coinisUnsubscribe != null)
            this.coinisUnsubscribe.unsubscribe();
        if (this.yunbiUnsubscribe != null)
            this.yunbiUnsubscribe.unsubscribe();
        if (this.bitfinexUnsubscribe != null)
            this.bitfinexUnsubscribe.unsubscribe();
        this.getBithumb();
        this.getKorbit();
        this.getPoloniexBitcoin();
        this.getCoinone();
        this.getOkCoinCn();
        this.getBitflyer();
        this.getBittrex();
        this.getKraken();
        this.getCoinis();
        this.getYunbi();
        this.getBitfinex();
    }

    currencyChange(value) {

    }

    setBithumb(data) {
        for (let i = 0; i < data.length-1; i++) {
            this.bithumbRow.coins[i].diffPercent = data[i].closing_price * 100 / this.bithumbRow.coins[i].price - 100
            this.bithumbRow.coins[i].diff = data[i].closing_price - this.bithumbRow.coins[i].price;
            this.bithumbRow.coins[i].price = data[i].closing_price
        }
        //xmr
        this.bithumbRow.coins[8].diffPercent = data[7].closing_price * 100 / this.bithumbRow.coins[8].price - 100
        this.bithumbRow.coins[8].diff = data[7].closing_price - this.bithumbRow.coins[8].price;
        this.bithumbRow.coins[8].price = data[7].closing_price
    }

    setKorbit(data) {
        // btc
        this.korbitRow.coins[0].diffPercent = data[0].last * 100 / this.korbitRow.coins[0].price - 100
        this.korbitRow.coins[0].diff = data[0].last - this.korbitRow.coins[0].price;
        this.korbitRow.coins[0].price = data[0].last
        // eth
        this.korbitRow.coins[1].diffPercent = data[1].last * 100 / this.korbitRow.coins[1].price - 100
        this.korbitRow.coins[1].diff = data[1].last - this.korbitRow.coins[1].price;
        this.korbitRow.coins[1].price = data[1].last
        // etc
        this.korbitRow.coins[5].diffPercent = data[2].last * 100 / this.korbitRow.coins[5].price - 100
        this.korbitRow.coins[5].diff = data[2].last - this.korbitRow.coins[5].price;
        this.korbitRow.coins[5].price = data[2].last
        // xrp
        this.korbitRow.coins[2].diffPercent = data[3].last * 100 / this.korbitRow.coins[2].price - 100
        this.korbitRow.coins[2].diff = data[3].last - this.korbitRow.coins[2].price;
        this.korbitRow.coins[2].price = data[3].last
    }

    setCoinOne(data) {
        // btc
        this.coinoneRow.coins[0].diffPercent = data.btc.last * 100 / this.coinoneRow.coins[0].price - 100
        this.coinoneRow.coins[0].diff = data.btc.last - this.coinoneRow.coins[0].price;
        this.coinoneRow.coins[0].price = data.btc.last
        // eth
        this.coinoneRow.coins[1].diffPercent = data.eth.last * 100 / this.coinoneRow.coins[1].price - 100
        this.coinoneRow.coins[1].diff = data.eth.last - this.coinoneRow.coins[1].price;
        this.coinoneRow.coins[1].price = data.eth.last
        // xrp
        this.coinoneRow.coins[2].diffPercent = data.xrp.last * 100 / this.coinoneRow.coins[2].price - 100
        this.coinoneRow.coins[2].diff = data.xrp.last - this.coinoneRow.coins[2].price;
        this.coinoneRow.coins[2].price = data.xrp.last
        // etc
        this.coinoneRow.coins[5].diffPercent = data.etc.last * 100 / this.coinoneRow.coins[5].price - 100
        this.coinoneRow.coins[5].diff = data.etc.last - this.coinoneRow.coins[5].price;
        this.coinoneRow.coins[5].price = data.etc.last
        // bch
        this.coinoneRow.coins[6].diffPercent = data.bch.last * 100 / this.coinoneRow.coins[6].price - 100
        this.coinoneRow.coins[6].diff = data.bch.last - this.coinoneRow.coins[6].price;
        this.coinoneRow.coins[6].price = data.bch.last

        this.coinoneRow.coins[10].diffPercent = data.qtum.last * 100 / this.coinoneRow.coins[10].price - 100
        this.coinoneRow.coins[10].diff = data.qtum.last - this.coinoneRow.coins[10].price;
        this.coinoneRow.coins[10].price = data.qtum.last
    }

    setPoloniex(data) {
        this.poloniexRow.coins[0].diffPercent = data.USDT_BTC.last * 100 / this.poloniexRow.coins[0].price - 100;
        this.poloniexRow.coins[0].diff = data.USDT_BTC.last - this.poloniexRow.coins[0].price;
        this.poloniexRow.coins[0].price = data.USDT_BTC.last;

        this.poloniexRow.coins[1].diffPercent = data.USDT_ETH.last * 100 / this.poloniexRow.coins[1].price - 100;
        this.poloniexRow.coins[1].diff = data.USDT_ETH.last - this.poloniexRow.coins[1].price;
        this.poloniexRow.coins[1].price = data.USDT_ETH.last;

        this.poloniexRow.coins[2].diffPercent = data.USDT_XRP.last * 100 / this.poloniexRow.coins[2].price - 100;
        this.poloniexRow.coins[2].diff = data.USDT_XRP.last - this.poloniexRow.coins[2].price;
        this.poloniexRow.coins[2].price = data.USDT_XRP.last;

        this.poloniexRow.coins[3].diffPercent = data.USDT_DASH.last * 100 / this.poloniexRow.coins[3].price - 100;
        this.poloniexRow.coins[3].diff = data.USDT_DASH.last - this.poloniexRow.coins[3].price;
        this.poloniexRow.coins[3].price = data.USDT_DASH.last;

        this.poloniexRow.coins[4].diffPercent = data.USDT_LTC.last * 100 / this.poloniexRow.coins[4].price - 100;
        this.poloniexRow.coins[4].diff = data.USDT_LTC.last - this.poloniexRow.coins[4].price;
        this.poloniexRow.coins[4].price = data.USDT_LTC.last;

        this.poloniexRow.coins[5].diffPercent = data.USDT_ETC.last * 100 / this.poloniexRow.coins[5].price - 100;
        this.poloniexRow.coins[5].diff = data.USDT_ETC.last - this.poloniexRow.coins[5].price;
        this.poloniexRow.coins[5].price = data.USDT_ETC.last;

        this.poloniexRow.coins[6].diffPercent = data.USDT_BCH.last * 100 / this.poloniexRow.coins[6].price - 100;
        this.poloniexRow.coins[6].diff = data.USDT_BCH.last - this.poloniexRow.coins[6].price;
        this.poloniexRow.coins[6].price = data.USDT_BCH.last;

        this.poloniexRow.coins[7].diffPercent = data.USDT_ZEC.last * 100 / this.poloniexRow.coins[7].price - 100;
        this.poloniexRow.coins[7].diff = data.USDT_ZEC.last - this.poloniexRow.coins[7].price;
        this.poloniexRow.coins[7].price = data.USDT_ZEC.last;

        this.poloniexRow.coins[8].diffPercent = data.USDT_XMR.last * 100 / this.poloniexRow.coins[8].price - 100;
        this.poloniexRow.coins[8].diff = data.USDT_XMR.last - this.poloniexRow.coins[8].price;
        this.poloniexRow.coins[8].price = data.USDT_XMR.last;
    }

    setOkCoinCn(data) {
        this.okCoinCnRow.coins[0].diffPercent = data[0].last * 100 / this.okCoinCnRow.coins[0].price - 100;
        this.okCoinCnRow.coins[0].diff = data[0].last - this.okCoinCnRow.coins[0].price;
        this.okCoinCnRow.coins[0].price = data[0].last;

        this.okCoinCnRow.coins[1].diffPercent = data[1].last * 100 / this.okCoinCnRow.coins[1].price - 100;
        this.okCoinCnRow.coins[1].diff = data[1].last - this.okCoinCnRow.coins[1].price;
        this.okCoinCnRow.coins[1].price = data[1].last;

        this.okCoinCnRow.coins[4].diffPercent = data[2].last * 100 / this.okCoinCnRow.coins[4].price - 100;
        this.okCoinCnRow.coins[4].diff = data[2].last - this.okCoinCnRow.coins[4].price;
        this.okCoinCnRow.coins[4].price = data[2].last;

        this.okCoinCnRow.coins[5].diffPercent = data[3].last * 100 / this.okCoinCnRow.coins[5].price - 100;
        this.okCoinCnRow.coins[5].diff = data[3].last - this.okCoinCnRow.coins[5].price;
        this.okCoinCnRow.coins[5].price = data[3].last;

        this.okCoinCnRow.coins[6].diffPercent = data[4].last * 100 / this.okCoinCnRow.coins[6].price - 100;
        this.okCoinCnRow.coins[6].diff = data[4].last - this.okCoinCnRow.coins[6].price;
        this.okCoinCnRow.coins[6].price = data[4].last;
    }

    setBitflyer(data) {
        // btc
        this.bitflyerRow.coins[0].diffPercent = data[0].ltp * 100 / this.bitflyerRow.coins[0].price - 100;
        this.bitflyerRow.coins[0].diff = data[0].ltp - this.bitflyerRow.coins[0].price;
        this.bitflyerRow.coins[0].price = data[0].ltp
    }

    setBittrex(data) {
        for (let i = 0; i < data.length; i++) {
            this.bittrexRow.coins[i].diffPercent = data[i].last * 100 / this.bittrexRow.coins[i].price - 100;
            this.bittrexRow.coins[i].diff = data[i].last - this.bittrexRow.coins[i].price;
            this.bittrexRow.coins[i].price = data[i].last
        }
    }
    setBitfinex(data) {
        for (let i = 0; i < data.length; i++) {
            this.bitfinexRow.coins[i].diffPercent = data[i].last_price * 100 / this.bitfinexRow.coins[i].price - 100;
            this.bitfinexRow.coins[i].diff = data[i].last_price - this.bitfinexRow.coins[i].price;
            this.bitfinexRow.coins[i].price = data[i].last_price
        }
    }

    setKraken(data) {
        for (let i = 0; i < data.length; i++) {
            this.krakenRow.coins[i].diffPercent = data[i].last * 100 / this.krakenRow.coins[i].price - 100;
            this.krakenRow.coins[i].diff = data[i].last - this.krakenRow.coins[i].price;
            this.krakenRow.coins[i].price = data[i].last
        }
    }

    setCoinis(data) {
        //3478
        this.coinisRow.coins[0].diffPercent = data[0].closeprice * 100 / this.coinisRow.coins[0].price - 100;
        this.coinisRow.coins[0].diff = data[0].closeprice - this.coinisRow.coins[0].price;
        this.coinisRow.coins[0].price = data[0].closeprice;

        this.coinisRow.coins[3].diffPercent = data[1].closeprice * 400 / this.coinisRow.coins[3].price - 100;
        this.coinisRow.coins[3].diff = data[1].closeprice - this.coinisRow.coins[3].price;
        this.coinisRow.coins[3].price = data[1].closeprice

        this.coinisRow.coins[4].diffPercent = data[2].closeprice * 100 / this.coinisRow.coins[4].price - 100;
        this.coinisRow.coins[4].diff = data[2].closeprice - this.coinisRow.coins[4].price;
        this.coinisRow.coins[4].price = data[2].closeprice;

        this.coinisRow.coins[7].diffPercent = data[3].closeprice * 100 / this.coinisRow.coins[7].price - 100;
        this.coinisRow.coins[7].diff = data[3].closeprice - this.coinisRow.coins[7].price;
        this.coinisRow.coins[7].price = data[3].closeprice;

        this.coinisRow.coins[8].diffPercent = data[4].closeprice * 100 / this.coinisRow.coins[8].price - 100;
        this.coinisRow.coins[8].diff = data[4].closeprice - this.coinisRow.coins[8].price;
        this.coinisRow.coins[8].price = data[4].closeprice;
    }

    setYunbis(data) {
        this.yunbiRow.coins[0].diffPercent = data.btccny.ticker.last * 100 / this.yunbiRow.coins[0].price - 100;
        this.yunbiRow.coins[0].diff = data.btccny.ticker.last - this.yunbiRow.coins[0].price;
        this.yunbiRow.coins[0].price = data.btccny.ticker.last;

        this.yunbiRow.coins[1].diffPercent = data.ethcny.ticket.last * 400 / this.yunbiRow.coins[1].price - 100;
        this.yunbiRow.coins[1].diff = data.ethcny.ticker.last - this.yunbiRow.coins[1].price;
        this.yunbiRow.coins[1].price = data.ethcny.ticker.last;

        this.yunbiRow.coins[5].diffPercent = data.etccny.ticker.last * 100 / this.yunbiRow.coins[5].price - 100;
        this.yunbiRow.coins[5].diff = data.etccny.ticker.last - this.yunbiRow.coins[5].price;
        this.yunbiRow.coins[5].price = data.etccny.ticker.last;

        this.yunbiRow.coins[6].diffPercent = data.bcccny.ticker.last * 100 / this.yunbiRow.coins[6].price - 100;
        this.yunbiRow.coins[6].diff = data.bcccny.ticker.last - this.yunbiRow.coins[6].price;
        this.yunbiRow.coins[6].price = data.bcccny.ticker.last;

        this.yunbiRow.coins[7].diffPercent = data.zeccny.ticker.last * 100 / this.yunbiRow.coins[7].price - 100;
        this.yunbiRow.coins[7].diff = data.zeccny.ticker.last - this.yunbiRow.coins[7].price;
        this.yunbiRow.coins[7].price = data.zeccny.ticker.last;

        this.yunbiRow.coins[9].diffPercent = data.anscny.ticker.last * 100 / this.yunbiRow.coins[9].price - 100;
        this.yunbiRow.coins[9].diff = data.anscny.ticker.last - this.yunbiRow.coins[9].price;
        this.yunbiRow.coins[9].price = data.anscny.ticker.last;

        this.yunbiRow.coins[10].diffPercent = data.qtumcny.ticker.last * 100 / this.yunbiRow.coins[10].price - 100;
        this.yunbiRow.coins[10].diff = data.qtumcny.ticker.last - this.yunbiRow.coins[10].price;
        this.yunbiRow.coins[10].price = data.qtumcny.ticker.last;
    }

    /*
    private setKraken(btc: any, eth: any, xrp: any, dash: any, ltc: any, etc: any, bch: any, zec: any, xmr: any) {
        this.krakenRow.coins[0].diffPercent = btc.result.XXBTZEUR.c[0] * 100 / this.krakenRow.coins[0].price - 100;
        this.krakenRow.coins[0].diff = btc.result.XXBTZEUR.c[0] - this.krakenRow.coins[0].price;
        this.krakenRow.coins[0].price = btc.result.XXBTZEUR.c[0];

        this.krakenRow.coins[1].diffPercent = eth.result.XETHZEUR.c[0] * 100 / this.krakenRow.coins[1].price - 100;
        this.krakenRow.coins[1].diff = eth.result.XETHZEUR.c[0] - this.krakenRow.coins[1].price;
        this.krakenRow.coins[1].price = eth.result.XETHZEUR.c[0];

        this.krakenRow.coins[2].diffPercent = xrp.result.XXRPZEUR.c[0] * 100 / this.krakenRow.coins[2].price - 100;
        this.krakenRow.coins[2].diff = xrp.result.XXRPZEUR.c[0] - this.krakenRow.coins[2].price;
        this.krakenRow.coins[2].price = xrp.result.XXRPZEUR.c[0];

        this.krakenRow.coins[3].diffPercent = dash.result.DASHEUR.c[0] * 100 / this.krakenRow.coins[3].price - 100;
        this.krakenRow.coins[3].diff = dash.result.DASHEUR.c[0] - this.krakenRow.coins[3].price;
        this.krakenRow.coins[3].price = dash.result.DASHEUR.c[0];

        this.krakenRow.coins[4].diffPercent = ltc.result.XLTCZEUR.c[0] * 100 / this.krakenRow.coins[4].price - 100;
        this.krakenRow.coins[4].diff = ltc.result.XLTCZEUR.c[0] - this.krakenRow.coins[4].price;
        this.krakenRow.coins[4].price = ltc.result.XLTCZEUR.c[0];

        this.krakenRow.coins[5].diffPercent = etc.result.XETCZEUR.c[0] * 100 / this.krakenRow.coins[5].price - 100;
        this.krakenRow.coins[5].diff = etc.result.XETCZEUR.c[0] - this.krakenRow.coins[5].price;
        this.krakenRow.coins[5].price = etc.result.XETCZEUR.c[0];

        this.krakenRow.coins[6].diffPercent = bch.result.BCHEUR.c[0] * 100 / this.krakenRow.coins[6].price - 100;
        this.krakenRow.coins[6].diff = bch.result.BCHEUR.c[0] - this.krakenRow.coins[6].price;
        this.krakenRow.coins[6].price = bch.result.BCHEUR.c[0];

        this.krakenRow.coins[7].diffPercent = zec.result.XZECZEUR.c[0] * 100 / this.krakenRow.coins[7].price - 100;
        this.krakenRow.coins[7].diff = zec.result.XZECZEUR.c[0] - this.krakenRow.coins[7].price;
        this.krakenRow.coins[7].price = zec.result.XZECZEUR.c[0];

        this.krakenRow.coins[8].diffPercent = xmr.result.XXMRZEUR.c[0] * 100 / this.krakenRow.coins[8].price - 100;
        this.krakenRow.coins[8].diff = xmr.result.XXMRZEUR.c[0] - this.krakenRow.coins[8].price;
        this.krakenRow.coins[8].price = xmr.result.XXMRZEUR.c[0];
    }*/
}
