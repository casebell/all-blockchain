import {Injectable} from '@angular/core';
import {SERVER_API_URL} from '../../app.constants';
import {HttpClient} from '@angular/common/http';
import {Observable, Subject} from 'rxjs/Rx';

@Injectable()
export class CoinPriceService {
    public poloniexMessage: Subject<any>;
    public bitfinexMessage: Subject<any>;
    private upBitsMarketCoinIds: any = [1101,1102,1103,1104,1105,1106,1107,1108,1109,1110,1111,1112];

    constructor(private http: HttpClient) {
    }

    getBithumb(): Observable<any>{
        return this.http.get(`${SERVER_API_URL}/api/coin-api/bithumb`);
    }
    getKorbit(): Observable<any>{
        return this.http.get(`${SERVER_API_URL}/api/coin-api/korbit/`);
    }
    getOkCoinCn(): Observable<any>{
        return this.http.get(`${SERVER_API_URL}/api/coin-api/okcoin-cn/`);
    }
    getBitflyer(): Observable<any>{
        return this.http.get(`${SERVER_API_URL}/api/coin-api/bitflyer`);
    }
    getCoinone(): Observable<any> {
        return  this.http.get('https://api.coinone.co.kr/ticker?currency=all');

    }

    getPoloniex(): Observable<any> {
        return   this.http.get('https://poloniex.com/public?command=returnTicker');
    }

    getBittrex(): Observable<any> {
        return   this.http.get(`${SERVER_API_URL}/api/coin-api/bittrex`);
    }
    getBitfinex(): Observable<any> {
        return   this.http.get(`${SERVER_API_URL}/api/coin-api/bitfinex`);
    }
    getKrakens(): Observable<any> {
        return   this.http.get(`${SERVER_API_URL}/api/coin-api/krakens`);
    }
    getCoinis(): Observable<any> {
        return   this.http.get(`${SERVER_API_URL}/api/coin-api/coinis`);
    }
    getYunbis(): Observable<any> {
        return   this.http.get(`https://yunbi.com//api/v2/tickers.json`);
    }

    getUpbit(): Observable<any> {
        return   this.http.get(`${SERVER_API_URL}/api/coin-api/upbit/${this.upBitsMarketCoinIds}`);

    }
}
