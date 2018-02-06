import { Injectable, EventEmitter } from '@angular/core';
const GDAX_WS_URL = 'wss://ws-feed.gdax.com';
const REQUEST = {
    'type': 'subscribe',
    'product_ids': [
        'BTC-USD',
        'ETH-USD',
        'LTC-USD'
    ],
    'channels': ['ticker']
};

@Injectable()
export class GDAXWebsocketService {

  private socket: WebSocket;

  private listener: EventEmitter<any> = new EventEmitter();

  constructor() { }
    public tickerConnect(coin){
        this.socket = new WebSocket(GDAX_WS_URL);

        this.socket.onopen = (event) => {
            this.send(JSON.stringify(REQUEST));
        }

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

  public connect(){
    this.socket = new WebSocket(GDAX_WS_URL);

    this.socket.onopen = (event) => {
        this.send(JSON.stringify(REQUEST));
    }

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
