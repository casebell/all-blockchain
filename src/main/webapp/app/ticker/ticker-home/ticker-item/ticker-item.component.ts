import { Component, OnInit, Input,ViewEncapsulation } from '@angular/core';
import { TickerService } from '../../ticker.service';
import {Observable} from 'rxjs/Rx';

@Component({
  selector: 'coin-ticker-item',
  templateUrl: './ticker-item.component.html',
  styleUrls: ['ticker-item.scss']
})
export class TickerItemComponent implements OnInit {
  @Input() myTicker;
  @Input() myCurrency;
  constructor( private tickerService: TickerService) { }

  ngOnInit() {
    console.log('myTicker :', this.myTicker);
    if(this.myTicker != null){
       switch(this.myTicker.apiType)
       {
           case  "REST_SERVER":
               Observable
                   .interval(5 * 1000)
                   .timeInterval()
                   .flatMap(() => this.tickerService.getQuote(this.myTicker.marketCoinId))
                   .subscribe((result) => {
                       console.log('get Tickers : ', result);
                   });
               // this.tickerService.getTickers(this.myTicker.marketCoinId).subscribe((result) => {
               //     console.log('get Tickers : ', result);
               //
               //
               // });
               break;
           case  "REST_CLIENT":
               break;
           case "SOCKET":
               break;
       }
    }
  }

}
