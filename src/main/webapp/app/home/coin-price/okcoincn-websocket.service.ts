import { Injectable, EventEmitter } from '@angular/core';
const OKCOINCN_WS_URL = 'wss://real.okcoin.cn:10440/websocket/okcoinapi';
const REQUEST = {
    "event": "addChannel",
    "channel": "ok_sub_spotcny_btc_ticker"
};

@Injectable()
export class OkcoincnWebsocketService {

  private socket: WebSocket;

  private listener: EventEmitter<any> = new EventEmitter();

  constructor() { }

  public connect(){
    this.socket = new WebSocket('wss://real.okcoin.cn:10440/websocket/okcoinapi');

    this.socket.onopen = event => {
        this.send(JSON.stringify(REQUEST));
    };

    this.socket.onclose = event => {
      //  this.listener.emit({"type": "close", "data": event});


    };

    this.socket.onmessage = event => {
        this.listener.emit({"type": "message", "data": JSON.parse(event.data)});
    };

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
