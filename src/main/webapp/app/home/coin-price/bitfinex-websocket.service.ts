import { Injectable, EventEmitter } from '@angular/core';
import {
    BITFINEX_BCH_USD,
    BITFINEX_BTC_USD, BITFINEX_DASH_USD, BITFINEX_ETC_USD, BITFINEX_ETH_USD, BITFINEX_LTC_USD, BITFINEX_NEO_USD,
    BITFINEX_XMR_USD,
    BITFINEX_XRP_USD, BITFINEX_ZEC_USD
} from '../../shared';
const BITFINEX_WS_URL = 'wss://api.bitfinex.com/ws/2';


@Injectable()
export class BitfinexWebsocketService {

  private socket: WebSocket;

  private listener: EventEmitter<any> = new EventEmitter();

  constructor() { }

  public connect(){
    this.socket = new WebSocket(BITFINEX_WS_URL);

    this.socket.onopen = (event) => {
    //  this.listener.emit({"type": "open", "data": event});
      this.send(JSON.stringify(BITFINEX_BTC_USD));
      this.send(JSON.stringify(BITFINEX_ETH_USD));
      this.send(JSON.stringify(BITFINEX_XRP_USD));
      this.send(JSON.stringify(BITFINEX_DASH_USD));
      this.send(JSON.stringify(BITFINEX_LTC_USD));
      this.send(JSON.stringify(BITFINEX_ETC_USD));
      this.send(JSON.stringify(BITFINEX_BCH_USD));
      this.send(JSON.stringify(BITFINEX_ZEC_USD));
      this.send(JSON.stringify(BITFINEX_XMR_USD));
      this.send(JSON.stringify(BITFINEX_NEO_USD));

    };

    this.socket.onclose = (event) => {
      //  this.listener.emit({"type": "close", "data": event});

    };
    this.socket.onmessage = (event) => {
        this.listener.emit({'type': 'message', 'data': JSON.parse(event.data)});
    };
    this.socket.onerror = (event) => {
      this.socket.close();
      setTimeout(() => {
        this.connect();
      }, 1000)
    }
  }

    public tickerConnect(coin){
        this.socket = new WebSocket(BITFINEX_WS_URL);

        this.socket.onopen = (event) => {
            //  this.listener.emit({"type": "open", "data": event});
            this.send(JSON.stringify(coin));

        };

        this.socket.onclose = (event) => {
            //  this.listener.emit({"type": "close", "data": event});

        }
        this.socket.onmessage = (event) => {
            this.listener.emit({'type': 'message', 'data': JSON.parse(event.data)});
        }
        this.socket.onerror = (event) => {
            this.socket.close();
            setTimeout(() => {
                this.connect();
            }, 1000)
        }
    }

  public send(data: string) {
      this.socket.send(data);
  }

  public socketClose() {
      this.socket.close();
  }

  public getEventListener() {
      return this.listener;
  }
}
