import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import {ExchangeRateService} from '../coin-price/coin-price-row/exchange-rate.service';
@Component({
  selector: 'abc-exchange-rate',
  templateUrl: './exchange-rate.component.html',
  styleUrls: ['exchange-rate.scss']
})
export class ExchangeRateComponent implements OnInit {

  constructor( private exchangeRateService: ExchangeRateService ) {}

  exchangeRateKR;
  exchangeRateUS;
  exchangeRateEU;
  exchangeRateCN;
  exchangeRateJP;

  ngOnInit() {
      let parseString = require('xml2js').parseString;
       this.exchangeRateService.getExchangeRate()
          .subscribe((xml) => {

              parseString(xml, (err,result)=>{
                  console.log('xml result : ', result)
              })

          });
      // Observable.zip( this.exchangeRateService.getExchangeRate('KRW'),
      //     this.exchangeRateService.getExchangeRate('USD'),
      //     this.exchangeRateService.getExchangeRate('EUR'),
      //     this.exchangeRateService.getExchangeRate('CNY'),
      //     this.exchangeRateService.getExchangeRate('JPY'))
      //     .subscribe(([krw, usd, eur, cny, jpy]) => {
      //
      //         this.exchangeRateKR = krw;
      //         this.exchangeRateUS = usd;
      //         this.exchangeRateEU = eur;
      //         this.exchangeRateCN = cny;
      //         this.exchangeRateJP = jpy;
      //
      //     });
  }

}
