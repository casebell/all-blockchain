import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class ExchangeRateService {

  constructor(private http: HttpClient) { }

  getExchangeRate(): Observable<any>{
      console.log('get Exchange service');
      return this.http.get(`http://www.ecb.europa.eu/stats/eurofxref/eurofxref-daily.xml`)
  }

}
