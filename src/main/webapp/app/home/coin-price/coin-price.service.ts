import {Injectable} from '@angular/core';
import {API_SERVER_URL} from '../../app.constants';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Rx';

@Injectable()
export class CoinPriceService {

    private resourceUrl = API_SERVER_URL;

    constructor(private http: HttpClient) {

    }

    getBithumb(): Observable<any>{
        return this.http.get(`${this.resourceUrl}/coin-api/bithumb`);
    }
    getKorbit(): Observable<any>{
        return this.http.get(`${this.resourceUrl}/coin-api/korbit/`);
    }
    getOkCoinCn(): Observable<any>{
        return this.http.get(`${this.resourceUrl}/coin-api/okcoin-cn/`);
    }
    getBitflyer(): Observable<any>{
        return this.http.get(`${this.resourceUrl}/coin-api/bitflyer`);
    }
    getCoinone(): Observable<any> {
        return  this.http.get('https://api.coinone.co.kr/ticker?currency=all');

    }

    getPoloniex(): Observable<any> {
        return   this.http.get('https://poloniex.com/public?command=returnTicker');
    }

    getBittrex(): Observable<any> {
        return   this.http.get(`${this.resourceUrl}/coin-api/bittrex`);
    }
    getKraken(market:string): Observable<any> {
        return   this.http.get(`https://api.kraken.com/0/public/Ticker?pair=${market}`);
    }
    getKrakens(): Observable<any> {
        return   this.http.get(`${this.resourceUrl}/coin-api/krakens`);
    }
    getCoinis(): Observable<any> {
        return   this.http.get(`${this.resourceUrl}/coin-api/coinis`);
    }
}
