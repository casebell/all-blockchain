import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class ExchangeRateService {

  constructor(private http:HttpClient) { }

  getExchangeRate(market):Observable<any>{
      return this.http.get(`http://api.fixer.io/latest?base=${market}`)
  }

}
