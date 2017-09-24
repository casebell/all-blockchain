import { Injectable, EventEmitter } from '@angular/core';
import * as Rx from 'rxjs/Rx'

@Injectable()
export class WebsocketService {

  private socket: WebSocket;
  private listener: EventEmitter<any> = new EventEmitter();

  constructor() { }

  public connect(url,message){
    this.socket = new WebSocket(url);

    this.socket.onopen = event => {
      this.listener.emit({"type": "open", "data": event});
      this.send(JSON.stringify(message));
    }

    this.socket.onclose = event => {
        this.listener.emit({"type": "close", "data": event});
    }
    this.socket.onmessage = event => {
        this.listener.emit({"type": "message", "data": JSON.parse(event.data)});
    }
  }

  public send(data: string) {
      this.socket.send(data);
  }

  public close() {
      this.socket.close();
  }

  public getEventListener() {
      return this.listener;
  }

  /* create(url: string): Rx.Subject<MessageEvent> {
    let ws = new WebSocket(url);

    let observable = Rx.Observable.create(
      (obs: Rx.Observer<MessageEvent>) => {
        ws.onmessage = obs.next.bind(obs);
        ws.onerror = obs.error.bind(obs);
        ws.onclose = obs.complete.bind(obs);
        return ws.close.bind(ws);
      })
    let observer = {
      next:(data:Object) => {
        if(ws.readyState === WebSocket.OPEN){
          ws.send(JSON.stringify(data));
        }
      }
    }
    return Rx.Subject.create(observer, observable);  
  } */
}
