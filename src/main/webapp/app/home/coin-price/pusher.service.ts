import * as Pusher from 'pusher-js';
import { Injectable, EventEmitter } from '@angular/core';
import * as Rx from 'rxjs/Rx'

@Injectable()
export class PuserService {
  private pusher;
  private BTCMessageListener: EventEmitter<any> = new EventEmitter();
  private ETHMessageListener: EventEmitter<any> = new EventEmitter();
  private LTCMessageListener: EventEmitter<any> = new EventEmitter();
  private XRPMessageListener: EventEmitter<any> = new EventEmitter();
  
  constructor() { }

  public connectBTC(pusherKey,pusherEvent,pusherCahnnel) {
    var pusher = new Pusher(pusherKey),
    tradesChannel = pusher.subscribe(pusherCahnnel)
    tradesChannel.bind(pusherEvent, (data) =>{
      this.BTCMessageListener.emit(data);
    });
  }

  public getBTCListener(){
    return this.BTCMessageListener;
  }

  public connectETH(pusherKey,pusherEvent,pusherCahnnel) {
    var pusher = new Pusher(pusherKey),
    tradesChannel = pusher.subscribe(pusherCahnnel)
    tradesChannel.bind(pusherEvent, (data) =>{
      this.ETHMessageListener.emit(data);
    });
  }

  public getETHListener(){
    return this.ETHMessageListener;
  }

  public connectXRP(pusherKey,pusherEvent,pusherCahnnel) {
    var pusher = new Pusher(pusherKey),
    tradesChannel = pusher.subscribe(pusherCahnnel)
    tradesChannel.bind(pusherEvent, (data) =>{
      this.XRPMessageListener.emit(data);
    });
  }

  public getXRPListener(){
    return this.XRPMessageListener;
  }

  public connectLTC(pusherKey,pusherEvent,pusherCahnnel) {
    var pusher = new Pusher(pusherKey),
    tradesChannel = pusher.subscribe(pusherCahnnel)
    tradesChannel.bind(pusherEvent, (data) =>{
      this.LTCMessageListener.emit(data);
    });
  }

  public getLTCListener(){
    return this.LTCMessageListener;
  }
}
