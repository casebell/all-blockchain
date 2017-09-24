import {Injectable} from '@angular/core';
import {SERVER_API_URL} from '../../app.constants';
import {HttpClient} from '@angular/common/http';
import {Observable, Subject} from 'rxjs/Rx';

@Injectable()
export class CoinPriceService {

    private resourceUrl = SERVER_API_URL;
    public poloniexMessage: Subject<any>;
    public bitfinexMessage: Subject<any>;

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
    getBitfinex(): Observable<any> {
        return   this.http.get(`${this.resourceUrl}/coin-api/bitfinex`);
    }
    getKrakens(): Observable<any> {
        return   this.http.get(`${this.resourceUrl}/coin-api/krakens`);
    }
    getCoinis(): Observable<any> {
        return   this.http.get(`${this.resourceUrl}/coin-api/coinis`);
    }
    getYunbis(): Observable<any> {
        return   this.http.get(`https://yunbi.com//api/v2/tickers.json`);
    }
}
