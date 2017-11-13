import * as Wampy from 'wampy';

//declare  var Autobahn:any;
//import '../../../content/lib/autobahn/autobahn.js';

import { Injectable, EventEmitter } from '@angular/core';

const POLONIEX_WS_URL = 'wss://api.poloniex.com';

@Injectable()
export class PoloniexWebsocketService {

  private ws;

  private listener: EventEmitter<any> = new EventEmitter();

  constructor() {
  }

  public connect(){
      this.ws = new Wampy('wss://api.poloniex.com',  {realm: 'realm1'});
      this.ws.subscribe('ticker', (data) => {
          console.log(data);

      });

   /* this.socket.onmessage = event => {
        console.log('on message poloniex : ', event);
       // this.listener.emit({"type": "message", "data": JSON.parse(event.data)});
    }*/

  }

/*  public send(data: string) {
      this.socket.send(data);
  }

  public socketClose() {
      this.socket.close();
  }

  public getEventListener() {
      return this.listener;
  }*/
}
