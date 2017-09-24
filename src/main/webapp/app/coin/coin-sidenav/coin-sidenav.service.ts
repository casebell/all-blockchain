import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SERVER_API_URL } from '../../app.constants';
import { Observable } from 'rxjs/Observable';
import { Coin } from '../../model/coin.model';
import { DataSource } from '@angular/cdk/collections';

@Injectable()
export class CoinSidenavService {

  private resourceUrl = SERVER_API_URL;

  constructor(private http: HttpClient) {

  }

  findAll() :Observable<any> {
    return this.http.get(`${this.resourceUrl}/coins`);
  }
}


export class CoinSidenavSource extends DataSource<Coin> {

      constructor(private coins: Coin[]) {
          super();
      }

      /** Connect function called by the table to retrieve one stream containing the data to render. */
      connect(): Observable<Coin[]> {
          return Observable.of(this.coins);
      }

      disconnect() {}
  }
