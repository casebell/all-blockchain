import { Component, OnInit, Input,ViewEncapsulation } from '@angular/core';
import { TickerService } from '../../ticker.service';
import {Observable} from 'rxjs/Rx';
import { ExchangeRateService } from '../../../home/coin-price/coin-price-row/exchange-rate.service';
import { Quote } from '../../../entities/quote';
import * as _ from 'lodash';

@Component({
  selector: 'coin-ticker-item',
  templateUrl: './ticker-item.component.html',
  styleUrls: ['ticker-item.scss']
})
export class TickerItemComponent implements OnInit {
  @Input() myTicker;
  @Input() myCurrency;
  exchangeRate;
  quote:Quote;
  diff = 0;
    constructor( private tickerService: TickerService,private exchangeRateService: ExchangeRateService) { }

  ngOnInit() {

      this.getExchangeRate();
    if(this.myTicker != null){

       switch(this.myTicker.apiType)
       {
           case  "REST_SERVER":
               Observable
                   .interval(15 * 1000)
                   .timeInterval()
                   .flatMap(() => this.tickerService.getQuote(this.myTicker.marketCoinId))
                   .subscribe(<PushSubscriptionOptionsInit>(quote) => {
                       console.log('get Tickers : ', quote);
                       if(this.quote != null)
                       {
                           this.diff = this.quote.lastPrice - quote.lastPrice;
                       }
                       this.quote = quote;
                   });
               break;
           case  "REST_CLIENT":
               break;
           case "SOCKET":
               break;
       }
    }
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
