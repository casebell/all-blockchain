import { Component, Input, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'abc-coin-price-row',
  templateUrl: './coin-price-row.component.html',
  styleUrls: ['coin-price-row.scss']
})
export class CoinPriceRowComponent implements OnInit {
  @Input() coinRow;
  @Input('myCurrency') myCurrency;
  @Input('colsNum') colsNum;
  rowTest = []
  CURRENCY_QUERY = 'https://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.xchange%20where%20pair%20in%20(%22USDKRW%22%2C%20%22EURKRW%22%2C%22CNYKRW%22%2C%22JPYKRW%22%2C%22EURUSD%22%2C%22CNYUSD%22%2C%22JPYUSD%22)&format=json&diagnostics=true&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys&callback=';
  //select * from yahoo.finance.xchange where pair in ("USDKRW", "EURKRW","CNYKRW","JPYKRW","EURUSD","CNYUSD","JPYUSD")
  constructor(
    private http: HttpClient) { }
  exchangeRate
  ngOnInit() {
    this.rowTest = this.coinRow.coins;
    this.http.get(this.CURRENCY_QUERY)
      .subscribe((data: any) => {
        //console.log('currncy coin price row :', data);
        this.exchangeRate = data;
      });
  }

}
