import { Component, OnInit } from '@angular/core';
import * as _ from 'lodash';
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
       this.exchangeRateService.getExchangeRate()
          .subscribe(res => {
                  console.log('getExchangeRate : ', res);
              this.exchangeRateKR = _.find(res,{'base':'KRW'});
              this.exchangeRateUS = _.find(res,{'base':'USD'});
              this.exchangeRateEU = _.find(res,{'base':'EUR'});
              this.exchangeRateCN = _.find(res,{'base':'CNY'});
              this.exchangeRateJP = _.find(res,{'base':'JPY'});
          });

  };
}

//-DABC_DB_HOST=jdbc:postgresql://220.230.124.6:15432/abc -DABC_DB_PORT=15432 -DABC_DB_USERNAME=allblockchain -DABC_DB_PASSWORD=abc@11@25@%ian -DABC_MAIL_HOST=smtp.gmail.com -DABC_MAIL_PORT=587 -DABC_MAIL_USERNAME=iansoftdevmail -DABC_MAIL_PASSWORD=$TB{\;-Fh!7x_yHxj -DABC_GOOGLE_ID=857286757641-041c7tcon0fhdeee3tnacn7ugvrhvc01.apps.googleusercontent.com -DABC_GOOGLE_SECRET=yMq6GvZEqvzXbfuRUGsRyjLd -DABC_FACEBOOK_ID=2012166295736598 -DABC_FACEBOOK_SECRET=62b685e985bc91439833110f1f19d224 -DABC_TWITTER_ID=qwo5kF40bV1x6W3Wp7UcAXiKM -DABC_TWITTER_SECRET=Gt8fkFa5cW5E1Dxd1A6VaOFtJppiVw4LWyglPMmXzwT6JI52gV -DJWT_SECRET=5800Y4a0E6EcjY4atdvvZaZAFjbw55800046529aa9
