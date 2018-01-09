import { Component, OnInit, Input, ViewEncapsulation } from '@angular/core';
import { TickerService } from '../../ticker.service';
import { Observable } from 'rxjs/Rx';
import { ExchangeRateService } from '../../../home/coin-price/coin-price-row/exchange-rate.service';
import { Quote } from '../../../entities/quote';
import * as _ from 'lodash';
import {Subscription} from 'rxjs/Subscription';
import { CoinPriceService } from '../../../home/coin-price/coin-price.service';
import { PuserService } from '../../../home/coin-price/pusher.service';
import { BitfinexWebsocketService } from '../../../home/coin-price/bitfinex-websocket.service';
import { GDAXWebsocketService } from '../../../home/coin-price/gdax-websocket.service';
import { OkcoincnWebsocketService } from '../../../home/coin-price/okcoincn-websocket.service';
import { BITFINEX_BCH_USD, BITFINEX_BTC_USD } from '../../../shared';

@Component({
    selector: 'coin-ticker-item',
    templateUrl: './ticker-item.component.html',
    styleUrls: ['ticker-item.scss']
})
export class TickerItemComponent implements OnInit {
    @Input() myTicker;
    @Input() myCurrency;
    exchangeRate;
    quote: Quote = new Quote();
    diff = 0;
    checkFirst = true;
    poloniexUnsubscribe: Subscription;
    percentChange: any;
    // bitfinex websocket channelId;
    bitfinexChannelId: any;


    constructor(private tickerService: TickerService, private exchangeRateService: ExchangeRateService,
                private coinPriceService: CoinPriceService,
                private bitfinexWebsocketService: BitfinexWebsocketService,
                private gdaxWebsocketService: GDAXWebsocketService,
                private okcoincnWebsocketService: OkcoincnWebsocketService,
                private pusherService: PuserService) {
    }

    ngOnInit() {

        this.getExchangeRate();
        if (this.myTicker != null) {

            switch (this.myTicker.apiType) {
                case  'REST_SERVER':
                    this.tickerService.getQuote(this.myTicker.marketCoinId)
                        .subscribe(<PushSubscriptionOptionsInit>(quote) => {
                            this.quote = quote;
                        });

                    Observable
                        .interval(5 * 1000)
                        .timeInterval()
                        .flatMap(() => this.tickerService.getQuote(this.myTicker.marketCoinId))
                        .subscribe(<PushSubscriptionOptionsInit>(quote) => {
                           console.log('get Tickers : ', quote);
                            this.diff = this.quote.lastPrice - quote.lastPrice;
                            this.quote = quote;
                        });
                    break;
                case  'REST_CLIENT':
                        switch(this.myTicker.marketName)
                        {
                            case 'Poloniex':
                                this.coinPriceService.getPoloniex().subscribe((data: any) => {
                                this.setPoloniex(data);
                                this.checkFirst =false;
                            });
                                this.poloniexUnsubscribe = Observable
                                    .interval(5* 1000)
                                    .timeInterval()
                                    .flatMap(() => this.coinPriceService.getPoloniex())
                                    .subscribe((data: any) => {
                                        this.setPoloniex(data)
                                    });
                                break;

                        }
                    break;
                case 'SOCKET':
                    switch(this.myTicker.marketName)
                    {
                        case 'Bitfinex':
                            this.getBitfinex();

                            break;
                        case 'BitstampUS':
                            break;
                        case 'GDAX':
                            break;
                        case 'BitstampEU':
                            break;
                    }
                    break;
            }
        }
    }

    getBitfinex(){
        this.quote.lastPrice = 0;
        this.quote.currency = 'USD';
        switch (this.myTicker.coinName)
        {
            case 'btc':
                this.bitfinexWebsocketService.tickerConnect(BITFINEX_BTC_USD);
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
            case 'neo':

                break;
        }

        this.bitfinexWebsocketService.getEventListener().subscribe((event) => {
            console.log('event', event);
            if (Array.isArray(event.data)) {
                const res = event.data[1];
                console.log('later event', event);
                if (res != 'hb')
                    this.setBitfinex(res[6],res[8],res[9],res[0],res[3],res[7]);
            }
        });
    }
    setBitfinex(lastPrice, highPrice: any, lowPrice: any,buyPrice,sellPrice, volume: any,) {

        if(this.quote.lastPrice !==0)
        {
            this.diff = this.quote.lastPrice - lastPrice;
        }

        this.quote.lastPrice = lastPrice;
        this.quote.highPrice = highPrice;
        this.quote.lowPrice = lowPrice;
        this.quote.buyPrice = buyPrice;
        this.quote.sellPrice = sellPrice;
        this.quote.volume = volume;

    }
    setPoloniex(data) {
        this.quote.currency = 'USD';
        switch (this.myTicker.coinName)
        {
            case 'btc':
                this.setPoloQuote(data.USDT_BTC.last, data.USDT_BTC.high24hr, data.USDT_BTC.low24hr, data.USDT_BTC.lowestAsk,data.USDT_BTC.highestBid,data.USDT_BTC.percentChange);
                break;
            case 'eth':
                this.setPoloQuote(data.USDT_ETH.last, data.USDT_ETH.high24hr, data.USDT_ETH.low24hr, data.USDT_ETH.lowestAsk,data.USDT_ETH.highestBid,data.USDT_ETH.percentChange);
                break;
            case 'xrp':
                this.setPoloQuote(data.USDT_XRP.last, data.USDT_XRP.high24hr, data.USDT_XRP.low24hr, data.USDT_XRP.lowestAsk,data.USDT_XRP.highestBid,data.USDT_XRP.percentChange);
                break;
            case 'dash':
                this.setPoloQuote(data.USDT_DASH.last, data.USDT_DASH.high24hr, data.USDT_DASH.low24hr, data.USDT_DASH.lowestAsk,data.USDT_DASH.highestBid,data.USDT_DASH.percentChange);
                break;
            case 'ltc':
                this.setPoloQuote(data.USDT_LTC.last, data.USDT_LTC.high24hr, data.USDT_LTC.low24hr, data.USDT_LTC.lowestAsk,data.USDT_LTC.highestBid,data.USDT_LTC.percentChange);
                break;
            case 'etc':
                this.setPoloQuote(data.USDT_ETC.last, data.USDT_ETC.high24hr, data.USDT_ETC.low24hr, data.USDT_ETC.lowestAsk,data.USDT_ETC.highestBid,data.USDT_ETC.percentChange);
                break;
            case 'bch':
                this.setPoloQuote(data.USDT_BCH.last, data.USDT_BCH.high24hr, data.USDT_BCH.low24hr, data.USDT_BCH.lowestAsk,data.USDT_BCH.highestBid,data.USDT_BCH.percentChange);
                break;
            case 'zec':
                this.setPoloQuote(data.USDT_ZEC.last, data.USDT_ZEC.high24hr, data.USDT_ZEC.low24hr, data.USDT_ZEC.lowestAsk,data.USDT_ZEC.highestBid,data.USDT_ZEC.percentChange);
                break;
            case 'xmr':
                this.setPoloQuote(data.USDT_XMR.last, data.USDT_XMR.high24hr, data.USDT_XMR.low24hr, data.USDT_XMR.lowestAsk,data.USDT_XMR.highestBid,data.USDT_XMR.percentChange);
                break;
            case 'rep':
                this.setPoloQuote(data.USDT_REP.last, data.USDT_REP.high24hr, data.USDT_REP.low24hr, data.USDT_REP.lowestAsk,data.USDT_REP.highestBid,data.USDT_REP.percentChange);
                break;
        }


    }

    setPoloQuote(lastPrice, highPrice: any, lowPrice: any,buyPrice,sellPrice, percentChange: any) {

        if(!this.checkFirst)
        {
            this.diff = this.quote.lastPrice - lastPrice;
        }


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

    ngOnDestroy(): void {
        this.bitfinexWebsocketService.socketClose();
    }
}
