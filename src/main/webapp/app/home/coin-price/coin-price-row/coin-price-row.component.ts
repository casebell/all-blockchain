import { Component, Input, OnInit } from '@angular/core';
import { trigger, style, transition, animate } from '@angular/animations';
import { ExchangeRateService } from './exchange-rate.service';
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'abc-coin-price-row',
  templateUrl: './coin-price-row.component.html',
  styleUrls: ['coin-price-row.scss'],
    animations: [
        trigger('valueUpdated', [
            transition(
                ':enter', [
                    style({backgroundColor: '#eee', transform: 'translateY(100%)', opacity: 0}),
                    animate('500ms', style({backgroundColor: '#fff', transform: 'translateY(0)', opacity: 1}))
                ]
            ),
            transition(
                ':leave', [
                    style({backgroundColor: '#fff', transform: 'translateY(0)', 'opacity': 1}),
                    animate('500ms', style({backgroundColor: '#eee', transform: 'translateY(-100%)', opacity: 0}))
                ]
            )
        ])
    ]
})
export class CoinPriceRowComponent implements OnInit {
  @Input() coinRow;
  @Input('myCurrency') myCurrency;
  @Input('colsNum') colsNum;
  //CURRENCY_QUERY = 'https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22USDKRW%22%2C%20%22EURKRW%22%2C%22CNYKRW%22%2C%22JPYKRW%22%2C%22EURUSD%22%2C%22CNYUSD%22%2C%22JPYUSD%22)&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=';
  CURRENCY_QUERY = 'http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml';
  //select * from yahoo.finance.xchange where pair in ("USDKRW", "EURKRW","CNYKRW","JPYKRW","EURUSD","CNYUSD","JPYUSD")
  constructor(private exchangeRateService: ExchangeRateService) { }
  exchangeRate;

  ngOnInit() {
     // Observable.zip( this.exchangeRateService.getExchangeRate('KRW'),
     //     this.exchangeRateService.getExchangeRate('USD'),
     //     this.exchangeRateService.getExchangeRate('EUR'),
     //     this.exchangeRateService.getExchangeRate('CNY'),
     //  this.exchangeRateService.getExchangeRate('JPY'))
     //  .subscribe(([krw, usd, eur, cny, jpy]) => {
     //     this.exchangeRate = [];
     //     this.exchangeRate.push(krw);
     //     this.exchangeRate.push(usd);
     //     this.exchangeRate.push(eur);
     //     this.exchangeRate.push(cny);
     //     this.exchangeRate.push(jpy);
     //  });
  }

  coinRowAnimation(value){

  }
}
