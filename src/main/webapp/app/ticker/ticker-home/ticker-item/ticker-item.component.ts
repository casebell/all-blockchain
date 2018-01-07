import { Component, OnInit, Input, ViewEncapsulation } from '@angular/core';
import { TickerService } from '../../ticker.service';
import { Observable } from 'rxjs/Rx';
import { ExchangeRateService } from '../../../home/coin-price/coin-price-row/exchange-rate.service';
import { Quote } from '../../../entities/quote';
import * as _ from 'lodash';
import {Subscription} from 'rxjs/Subscription';
import { CoinPriceService } from '../../../home/coin-price/coin-price.service';

@Component({
    selector: 'coin-ticker-item',
    templateUrl: './ticker-item.component.html',
    styleUrls: ['ticker-item.scss']
})
export class TickerItemComponent implements OnInit {
    @Input() myTicker;
    @Input() myCurrency;
    exchangeRate;
    quote: Quote;
    diff = 0;

    poloniexUnsubscribe: Subscription;
    percentChange: any;


    constructor(private tickerService: TickerService, private exchangeRateService: ExchangeRateService,
                private coinPriceService: CoinPriceService) {
    }

    ngOnInit() {

        this.getExchangeRate();
        if (this.myTicker != null) {

            switch (this.myTicker.apiType) {
                case  'REST_SERVER':
                    this.tickerService.getQuote(this.myTicker.marketCoinId)
                        .subscribe(<PushSubscriptionOptionsInit>(quote) => {
                            if (this.quote != null) {
                                this.diff = this.quote.lastPrice - quote.lastPrice;
                            }
                            this.quote = quote;
                        });

                    Observable
                        .interval(15 * 1000)
                        .timeInterval()
                        .flatMap(() => this.tickerService.getQuote(this.myTicker.marketCoinId))
                        .subscribe(<PushSubscriptionOptionsInit>(quote) => {
                            console.log('get Tickers : ', quote);
                            if (this.quote != null) {
                                this.diff = this.quote.lastPrice - quote.lastPrice;
                            }
                            this.quote = quote;
                        });
                    break;
                case  'REST_CLIENT':
                        switch(this.myTicker.marketName )
                        {
                            case 'Poloniex':
                                this.poloniexUnsubscribe = Observable
                                    .interval(15* 1000)
                                    .timeInterval()
                                    .flatMap(() => this.coinPriceService.getPoloniex())
                                    .subscribe((data: any) => {
                                        this.setPoloniex(data)
                                    });
                                break;

                        }
                    break;
                case 'SOCKET':
                    break;
            }
        }
    }

    setPoloniex(data) {
        switch (this.myTicker.coinName)
        {
            case 'btc':
                this.quote.lastPrice = data.USDT_BTC.last;
                this.setPoloQuote(data.USDT_BTC.last, data.USDT_BTC.high24hr, data.USDT_BTC.low24hr, data.USDT_BTC.lowestAsk,data.USDT_BTC.highestBid,data.USDT_BTC.percentChange);
                break;
            case 'eth':
                break;
            case 'xrp':
                break;
            case 'dash':
                break;
            case 'ltc':
                break;
            case 'etc':
                break;
            case 'bch':
                break;
            case 'zec':
                break;
            case 'xmr':
                break;
            case 'rep':
                break;
        }


    }

    setPoloQuote(lastPrice, highPrice: any, lowPrice: any,buyPrice,sellPrice, percentChange: any) {
        this.diff = this.quote.lastPrice - lastPrice;
        this.percentChange = percentChange;
        this.quote.lastPrice = lastPrice;
        this.quote.highPrice = highPrice;
        this.quote.lowPrice = lowPrice;
        this.quote.buyPrice = buyPrice;
        this.quote.sellPrice = sellPrice;

    }

    private getExchangeRate() {
        this.exchangeRateService.getExchangeRate()
            .subscribe(res => {
                this.exchangeRate = [];
                this.exchangeRate.push(_.find(res, {'base': 'KRW'}));
                this.exchangeRate.push(_.find(res, {'base': 'USD'}));
                this.exchangeRate.push(_.find(res, {'base': 'EUR'}));
                this.exchangeRate.push(_.find(res, {'base': 'CNY'}));
                this.exchangeRate.push(_.find(res, {'base': 'JPY'}));
            });
    }
}
