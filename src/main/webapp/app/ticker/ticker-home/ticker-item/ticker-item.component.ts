import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { TickerService } from '../../ticker.service';
import { Observable } from 'rxjs/Rx';
import { ExchangeRateService } from '../../../home/coin-price/coin-price-row/exchange-rate.service';
import { Quote } from '../../../entities/quote';
import * as _ from 'lodash';
import { Subscription } from 'rxjs/Subscription';
import { CoinPriceService } from '../../../home/coin-price/coin-price.service';
import { PuserService } from '../../../home/coin-price/pusher.service';
import { BitfinexWebsocketService } from '../../../home/coin-price/bitfinex-websocket.service';
import { GDAXWebsocketService } from '../../../home/coin-price/gdax-websocket.service';
import { OkcoincnWebsocketService } from '../../../home/coin-price/okcoincn-websocket.service';
import { MatDialog } from '@angular/material';
import {
    BITFINEX_BCH_USD, BITFINEX_BTC_USD, BITFINEX_BTG_USD, BITFINEX_DASH_USD, BITFINEX_EOS_USD, BITFINEX_ETC_USD,
    BITFINEX_ETH_USD,
    BITFINEX_LTC_USD,
    BITFINEX_NEO_USD,
    BITFINEX_XMR_USD,
    BITFINEX_XRP_USD, BITFINEX_ZEC_USD
} from '../../../shared';
import { AddTickerDialogComponent } from '../add-ticker-dialog/add-ticker-dialog.component';
import { DialogComponent } from '../dialog.component';

@Component({
    selector: 'coin-ticker-item',
    templateUrl: './ticker-item.component.html',
    styleUrls: ['ticker-item.scss']
})
export class TickerItemComponent implements OnInit {
    @Input() myTicker;
    @Input() myCurrency;
    @Output() close: EventEmitter<any> = new EventEmitter();
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
                private dialog: MatDialog,
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
    deleteTicker(tickerId){
        let dialogRef = this.dialog.open(DialogComponent,{
            width:'250px',
            data: {title:'DELETE COIN',content:'정말 코인을 삭제 하시겠습니까?'}
        });

        dialogRef.afterClosed().subscribe(result => {
            console.log('The dialog was closed', result);
            //  this.animal = result;
            if (result) {
                this.deleteTickerConfirm(tickerId);
            }
        });
    }
    deleteTickerConfirm (tickerId) {
        this.tickerService.delete(tickerId).subscribe(()=> {
            this.close.emit(true);
        });
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
                this.bitfinexWebsocketService.tickerConnect(BITFINEX_ETH_USD);
                break;
            case 'xrp':
                this.bitfinexWebsocketService.tickerConnect(BITFINEX_XRP_USD);

                break;
            case 'dash':
                this.bitfinexWebsocketService.tickerConnect(BITFINEX_DASH_USD);
                break;
            case 'ltc':
                this.bitfinexWebsocketService.tickerConnect(BITFINEX_LTC_USD);
                break;
            case 'etc':
                this.bitfinexWebsocketService.tickerConnect(BITFINEX_ETC_USD);
                break;
            case 'bch':
                this.bitfinexWebsocketService.tickerConnect(BITFINEX_BCH_USD);
                break;
            case 'zec':
                this.bitfinexWebsocketService.tickerConnect(BITFINEX_ZEC_USD);
                break;
            case 'xmr':
                this.bitfinexWebsocketService.tickerConnect(BITFINEX_XMR_USD);
                break;
            case 'neo':
                this.bitfinexWebsocketService.tickerConnect(BITFINEX_NEO_USD);
                break;
            case 'btg':
                this.bitfinexWebsocketService.tickerConnect(BITFINEX_BTG_USD);
                break;
            case 'eos':
                this.bitfinexWebsocketService.tickerConnect(BITFINEX_EOS_USD);
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
                this.setPoloQuote(data.USDT_BTC.last, data.USDT_BTC.high24hr, data.USDT_BTC.low24hr, data.USDT_BTC.lowestAsk,data.USDT_BTC.highestBid,data.USDT_BTC.percentChange, data.USDT_BTC.quoteVolume);
                break;
            case 'eth':
                this.setPoloQuote(data.USDT_ETH.last, data.USDT_ETH.high24hr, data.USDT_ETH.low24hr, data.USDT_ETH.lowestAsk,data.USDT_ETH.highestBid,data.USDT_ETH.percentChange, data.USDT_BTC.quoteVolume);
                break;
            case 'xrp':
                this.setPoloQuote(data.USDT_XRP.last, data.USDT_XRP.high24hr, data.USDT_XRP.low24hr, data.USDT_XRP.lowestAsk,data.USDT_XRP.highestBid,data.USDT_XRP.percentChange, data.USDT_BTC.quoteVolume);
                break;
            case 'dash':
                this.setPoloQuote(data.USDT_DASH.last, data.USDT_DASH.high24hr, data.USDT_DASH.low24hr, data.USDT_DASH.lowestAsk,data.USDT_DASH.highestBid,data.USDT_DASH.percentChange, data.USDT_BTC.quoteVolume);
                break;
            case 'ltc':
                this.setPoloQuote(data.USDT_LTC.last, data.USDT_LTC.high24hr, data.USDT_LTC.low24hr, data.USDT_LTC.lowestAsk,data.USDT_LTC.highestBid,data.USDT_LTC.percentChange, data.USDT_BTC.quoteVolume);
                break;
            case 'etc':
                this.setPoloQuote(data.USDT_ETC.last, data.USDT_ETC.high24hr, data.USDT_ETC.low24hr, data.USDT_ETC.lowestAsk,data.USDT_ETC.highestBid,data.USDT_ETC.percentChange, data.USDT_BTC.quoteVolume);
                break;
            case 'bch':
                this.setPoloQuote(data.USDT_BCH.last, data.USDT_BCH.high24hr, data.USDT_BCH.low24hr, data.USDT_BCH.lowestAsk,data.USDT_BCH.highestBid,data.USDT_BCH.percentChange, data.USDT_BTC.quoteVolume);
                break;
            case 'zec':
                this.setPoloQuote(data.USDT_ZEC.last, data.USDT_ZEC.high24hr, data.USDT_ZEC.low24hr, data.USDT_ZEC.lowestAsk,data.USDT_ZEC.highestBid,data.USDT_ZEC.percentChange, data.USDT_BTC.quoteVolume);
                break;
            case 'xmr':
                this.setPoloQuote(data.USDT_XMR.last, data.USDT_XMR.high24hr, data.USDT_XMR.low24hr, data.USDT_XMR.lowestAsk,data.USDT_XMR.highestBid,data.USDT_XMR.percentChange, data.USDT_BTC.quoteVolume);
                break;
            case 'rep':
                this.setPoloQuote(data.USDT_REP.last, data.USDT_REP.high24hr, data.USDT_REP.low24hr, data.USDT_REP.lowestAsk,data.USDT_REP.highestBid,data.USDT_REP.percentChange, data.USDT_BTC.quoteVolume);
                break;
        }


    }

    setPoloQuote(lastPrice, highPrice: any, lowPrice: any,buyPrice,sellPrice, percentChange: any,volume) {

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
        this.quote.volume = volume

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
        switch(this.myTicker.market)
        {
            case 'Bitfinex' :
                this.bitfinexWebsocketService.socketClose();
                break;
        }
    }
}
