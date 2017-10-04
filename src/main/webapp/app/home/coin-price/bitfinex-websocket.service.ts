import { Injectable, EventEmitter } from '@angular/core';
import * as Rx from 'rxjs/Rx'

@Injectable()
export class BitfinexWebsocketService {

  private btcSocket: WebSocket;
  private ethSocket: WebSocket;
  private xrpSocket: WebSocket;
  private dashSocket: WebSocket;
  private ltcSocket: WebSocket;
  private etcSocket: WebSocket;
  private bchSocket: WebSocket;
  private zecSocket: WebSocket;
  private xmrSocket: WebSocket;
  private neoSocket: WebSocket;


  private btcListener: EventEmitter<any> = new EventEmitter();
  private ethListener: EventEmitter<any> = new EventEmitter();
  private xrpListener: EventEmitter<any> = new EventEmitter();
  private dashListener: EventEmitter<any> = new EventEmitter();
  private ltcListener: EventEmitter<any> = new EventEmitter();
  private etcListener: EventEmitter<any> = new EventEmitter();
  private bchListener: EventEmitter<any> = new EventEmitter();
  private zecListener: EventEmitter<any> = new EventEmitter();
  private xmrListener: EventEmitter<any> = new EventEmitter();
  private neoListener: EventEmitter<any> = new EventEmitter();

  constructor() { }

  public connectBTC(url,message){
    this.btcSocket = new WebSocket(url);

    this.btcSocket.onopen = event => {
    //  this.btcListener.emit({"type": "open", "data": event});
      this.btcSend(JSON.stringify(message));
    }

    this.btcSocket.onclose = event => {
      //  this.btcListener.emit({"type": "close", "data": event});
    }
    this.btcSocket.onmessage = event => {
        this.btcListener.emit({"type": "message", "data": JSON.parse(event.data)});
    }
  }

  public btcSend(data: string) {
      this.btcSocket.send(data);
  }

  public btcClose() {
      this.btcSocket.close();
  }

  public btcGetEventListener() {
      return this.btcListener;
  }

  public connectETH(url,message){
    this.ethSocket = new WebSocket(url);

    this.ethSocket.onopen = event => {
      this.ethListener.emit({"type": "open", "data": event});
      this.ethSend(JSON.stringify(message));
    }

    this.ethSocket.onclose = event => {
        this.ethListener.emit({"type": "close", "data": event});
    }
    this.ethSocket.onmessage = event => {
        this.ethListener.emit({"type": "message", "data": JSON.parse(event.data)});
    }
  }
  public ethSend(data: string) {
      this.ethSocket.send(data);
  }

  public ethClose() {
      this.ethSocket.close();
  }
  public ethGetEventListener() {
      return this.ethListener;
  }

  public connectXRP(url,message){
    this.xrpSocket = new WebSocket(url);

    this.xrpSocket.onopen = event => {
      this.xrpSend(JSON.stringify(message));
    }
    this.xrpSocket.onclose = event => {
    }
    this.xrpSocket.onmessage = event => {
      this.xrpListener.emit({"type": "message", "data": JSON.parse(event.data)});      
    }
  }
  public xrpSend(data: string) {
      this.xrpSocket.send(data);
  }

  public xrpClose() {
      this.xrpSocket.close();
  }
  public xrpGetEventListener() {
      return this.xrpListener;
  }

  // dash
  public connectDASH(url,message){
    this.dashSocket = new WebSocket(url);

    this.dashSocket.onopen = event => {
      this.dashSend(JSON.stringify(message));
    }
    this.dashSocket.onclose = event => {
    }
    this.dashSocket.onmessage = event => {
      this.dashListener.emit({"type": "message", "data": JSON.parse(event.data)});      
    }
  }
  public dashSend(data: string) {
      this.dashSocket.send(data);
  }

  public dashClose() {
      this.dashSocket.close();
  }
  public dashGetEventListener() {
      return this.dashListener;
  }

  // ltc
  public connectLTC(url,message){
    this.ltcSocket = new WebSocket(url);

    this.ltcSocket.onopen = event => {
      this.ltcSend(JSON.stringify(message));
    }
    this.ltcSocket.onclose = event => {
    }
    this.ltcSocket.onmessage = event => {
      this.ltcListener.emit({"type": "message", "data": JSON.parse(event.data)});      
    }
  }
  public ltcSend(data: string) {
      this.ltcSocket.send(data);
  }

  public ltcClose() {
      this.ltcSocket.close();
  }
  public ltcGetEventListener() {
      return this.ltcListener;
  }

  // etc
  public connectETC(url,message){
    this.etcSocket = new WebSocket(url);

    this.etcSocket.onopen = event => {
      this.etcSend(JSON.stringify(message));
    }
    this.etcSocket.onclose = event => {
    }
    this.etcSocket.onmessage = event => {
      this.etcListener.emit({"type": "message", "data": JSON.parse(event.data)});      
    }
  }
  public etcSend(data: string) {
      this.etcSocket.send(data);
  }

  public etcClose() {
      this.etcSocket.close();
  }
  public etcGetEventListener() {
      return this.etcListener;
  }

  // bch
  public connectBCH(url,message){
    this.bchSocket = new WebSocket(url);

    this.bchSocket.onopen = event => {
      this.bchSend(JSON.stringify(message));
    }
    this.bchSocket.onclose = event => {
    }
    this.bchSocket.onmessage = event => {
      this.bchListener.emit({"type": "message", "data": JSON.parse(event.data)});      
    }
  }
  public bchSend(data: string) {
      this.bchSocket.send(data);
  }

  public bchClose() {
      this.bchSocket.close();
  }
  public bchGetEventListener() {
      return this.bchListener;
  }

  // zec
  public connectZEC(url,message){
    this.zecSocket = new WebSocket(url);

    this.zecSocket.onopen = event => {
      this.zecSend(JSON.stringify(message));
    }
    this.zecSocket.onclose = event => {
    }
    this.zecSocket.onmessage = event => {
      this.zecListener.emit({"type": "message", "data": JSON.parse(event.data)});      
    }
  }
  public zecSend(data: string) {
      this.zecSocket.send(data);
  }

  public zecClose() {
      this.zecSocket.close();
  }
  public zecGetEventListener() {
      return this.zecListener;
  }

  //xmr
  public connectXMR(url,message){
    this.xmrSocket = new WebSocket(url);

    this.xmrSocket.onopen = event => {
      this.xmrSend(JSON.stringify(message));
    }
    this.xmrSocket.onclose = event => {
    }
    this.xmrSocket.onmessage = event => {
      this.xmrListener.emit({"type": "message", "data": JSON.parse(event.data)});      
    }
  }
  public xmrSend(data: string) {
      this.xmrSocket.send(data);
  }

  public xmrClose() {
      this.xmrSocket.close();
  }
  public xmrGetEventListener() {
      return this.xmrListener;
  }

  //neo
  public connectNEO(url,message){
    this.neoSocket = new WebSocket(url);

    this.neoSocket.onopen = event => {
      this.neoSend(JSON.stringify(message));
    }
    this.neoSocket.onclose = event => {
    }
    this.neoSocket.onmessage = event => {
      this.neoListener.emit({"type": "message", "data": JSON.parse(event.data)});      
    }
  }
  public neoSend(data: string) {
      this.neoSocket.send(data);
  }

  public neoClose() {
      this.neoSocket.close();
  }
  public neoGetEventListener() {
      return this.neoListener;
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
