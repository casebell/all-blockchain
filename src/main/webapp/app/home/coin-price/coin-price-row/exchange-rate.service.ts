import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../../app.constants';

@Injectable()
export class ExchangeRateService {

  constructor(private http: HttpClient) { }

  getExchangeRate(): Observable<any>{
      return this.http.get(`${SERVER_API_URL}/api/coin-api/fixer`);
  }

}
