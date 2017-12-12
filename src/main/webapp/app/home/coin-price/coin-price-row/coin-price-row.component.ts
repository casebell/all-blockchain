import { Component, Input, OnInit } from '@angular/core';
import { trigger, style, transition, animate } from '@angular/animations';
import { ExchangeRateService } from './exchange-rate.service';
import * as _ from 'lodash';

@Component({
  selector: 'abc-coin-price-row',
  templateUrl: './coin-price-row.component.html',
  styleUrls: ['coin-price-row.scss'],
    animations: [
        trigger('valueUpdated', [
            transition(
                ':enter', [
                    style({backgroundColor: '#eee', transform: 'translateY(100%)', opacity: 0}),
                    animate('500ms', style({backgroundColor: '#e4e5e6', transform: 'translateY(0)', opacity: 1}))
                ]
            ),
            transition(
                ':leave', [
                    style({backgroundColor: '#e4e5e6', transform: 'translateY(0)', 'opacity': 1}),
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
  constructor(private exchangeRateService: ExchangeRateService) { }
  exchangeRate;

  ngOnInit() {
      this.exchangeRateService.getExchangeRate()
          .subscribe(res => {
                  this.exchangeRate = [];
                  this.exchangeRate.push(_.find(res,{'base':'KRW'}));
                  this.exchangeRate.push(_.find(res,{'base':'USD'}));
                  this.exchangeRate.push(_.find(res,{'base':'EUR'}));
                  this.exchangeRate.push(_.find(res,{'base':'CNY'}));
                  this.exchangeRate.push(_.find(res,{'base':'JPY'}));
          });
  }

  coinRowAnimation(value){

  }
}
