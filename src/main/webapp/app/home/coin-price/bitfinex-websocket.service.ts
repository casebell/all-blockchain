import { Injectable, EventEmitter } from '@angular/core';
import * as Rx from 'rxjs/Rx'
const BITFINEX_WS_URL = 'wss://api.bitfinex.com/ws/2';
const BITFINEX_BTC_USD = {"event":"subscribe", "channel":"ticker", "symbol": "tBTCUSD"}
const BITFINEX_XRP_USD = {"event":"subscribe", "channel":"ticker", "symbol": "tXRPUSD"}
const BITFINEX_ETH_USD = {"event":"subscribe", "channel":"ticker", "symbol": "tETHUSD"}
const BITFINEX_DASH_USD = {"event":"subscribe", "channel":"ticker", "symbol": "tDSHUSD"}
const BITFINEX_LTC_USD = {"event":"subscribe", "channel":"ticker", "symbol": "tLTCUSD"}
const BITFINEX_ETC_USD = {"event":"subscribe", "channel":"ticker", "symbol": "tETCUSD"}
const BITFINEX_BCH_USD = {"event":"subscribe", "channel":"ticker", "symbol": "tBCHUSD"}
const BITFINEX_ZEC_USD = {"event":"subscribe", "channel":"ticker", "symbol": "tZECUSD"}
const BITFINEX_XMR_USD = {"event":"subscribe", "channel":"ticker", "symbol": "tXMRUSD"}
const BITFINEX_NEO_USD = {"event":"subscribe", "channel":"ticker", "symbol": "tNEOUSD"}

@Injectable()
export class BitfinexWebsocketService {

  private socket: WebSocket;
 
  private listener: EventEmitter<any> = new EventEmitter();

  constructor() { }

  public connect(){
    this.socket = new WebSocket(BITFINEX_WS_URL);

    this.socket.onopen = event => {
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
    
    }

    this.socket.onclose = event => {
      //  this.listener.emit({"type": "close", "data": event});
      
   
    }
    this.socket.onmessage = event => {
        this.listener.emit({"type": "message", "data": JSON.parse(event.data)});
    }
    this.socket.onerror = event => {
      this.socket.close();
      setTimeout(()=>{
        this.connect();
      },1000)
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
