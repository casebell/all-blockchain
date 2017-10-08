import * as Pusher from 'pusher-js';
import { Injectable, EventEmitter } from '@angular/core';
import * as Rx from 'rxjs/Rx'
const BITSTAMP_PUSHER_KEY = 'de504dc5763aeef9ff52';
const BITSTAMP_PUSHER_EVENT = 'trade';
const BITSTAMP_PUSHER_BTCEUR_CHANNEL = 'live_trades_btceur';
const BITSTAMP_PUSHER_ETHEUR_CHANNEL = 'live_trades_etheur';
const BITSTAMP_PUSHER_LTCEUR_CHANNEL = 'live_trades_ltceur';
const BITSTAMP_PUSHER_XRPEUR_CHANNEL = 'live_trades_xrpeur';
const BITSTAMP_PUSHER_BTCUSD_CHANNEL = 'live_trades';
const BITSTAMP_PUSHER_ETHUSD_CHANNEL = 'live_trades_ethusd';
const BITSTAMP_PUSHER_LTCUSD_CHANNEL = 'live_trades_ltcusd';
const BITSTAMP_PUSHER_XRPUSD_CHANNEL = 'live_trades_xrpusd';
@Injectable()
export class PuserService {
  private pusher;
  private BTCUSDMessageListener: EventEmitter<any> = new EventEmitter();
  private BTCEURMessageListener: EventEmitter<any> = new EventEmitter();
  private ETHUSDMessageListener: EventEmitter<any> = new EventEmitter();
  private ETHEURMessageListener: EventEmitter<any> = new EventEmitter();
  private LTCUSDMessageListener: EventEmitter<any> = new EventEmitter();
  private LTCEURMessageListener: EventEmitter<any> = new EventEmitter();
  private XRPEURMessageListener: EventEmitter<any> = new EventEmitter();
  private XRPUSDMessageListener: EventEmitter<any> = new EventEmitter();
  
  constructor() { 
    this.pusher = new Pusher(BITSTAMP_PUSHER_KEY)
  }

  public connect() {
    
    var BTCUSDTradesChannel = this.pusher.subscribe(BITSTAMP_PUSHER_BTCUSD_CHANNEL)
    BTCUSDTradesChannel.bind(BITSTAMP_PUSHER_EVENT, (data) =>{
      this.BTCUSDMessageListener.emit(data);
    });

    var BTCEURTradesChannel = this.pusher.subscribe(BITSTAMP_PUSHER_BTCEUR_CHANNEL)
    BTCEURTradesChannel.bind(BITSTAMP_PUSHER_EVENT, (data) =>{
      this.BTCEURMessageListener.emit(data);
    });

    var ETHEURTradesChannel = this.pusher.subscribe(BITSTAMP_PUSHER_ETHEUR_CHANNEL)
    ETHEURTradesChannel.bind(BITSTAMP_PUSHER_EVENT, (data) =>{
      this.ETHEURMessageListener.emit(data);
    });

    var ETHUSDTradesChannel = this.pusher.subscribe(BITSTAMP_PUSHER_ETHUSD_CHANNEL)
    ETHUSDTradesChannel.bind(BITSTAMP_PUSHER_EVENT, (data) =>{
      this.ETHUSDMessageListener.emit(data);
    });

    var LTCEURTradesChannel = this.pusher.subscribe(BITSTAMP_PUSHER_LTCEUR_CHANNEL)
    LTCEURTradesChannel.bind(BITSTAMP_PUSHER_EVENT, (data) =>{
      this.LTCEURMessageListener.emit(data);
    });

    var LTCUSDTradesChannel = this.pusher.subscribe(BITSTAMP_PUSHER_LTCUSD_CHANNEL)
    LTCUSDTradesChannel.bind(BITSTAMP_PUSHER_EVENT, (data) =>{
      this.LTCUSDMessageListener.emit(data);
    });

    var XRPEURTradesChannel = this.pusher.subscribe(BITSTAMP_PUSHER_XRPEUR_CHANNEL)
    XRPEURTradesChannel.bind(BITSTAMP_PUSHER_EVENT, (data) =>{
      this.XRPEURMessageListener.emit(data);
    });

    var XRPUSDTradesChannel = this.pusher.subscribe(BITSTAMP_PUSHER_XRPUSD_CHANNEL)
    XRPUSDTradesChannel.bind(BITSTAMP_PUSHER_EVENT, (data) =>{
      this.XRPUSDMessageListener.emit(data);
    });
  }

  public getBTCUSDListener(){
    return this.BTCUSDMessageListener;
  }

  public getBTCEURListener(){
    return this.BTCEURMessageListener;
  }

  public getETHUSDListener(){
    return this.ETHUSDMessageListener;
  }

  public getETHEURListener(){
    return this.ETHEURMessageListener;
  }

  public getLTCUSDListener(){
    return this.LTCUSDMessageListener;
  }

  public getLTCEURListener(){
    return this.LTCEURMessageListener;
  }

  public getXRPUSDListener(){
    return this.XRPUSDMessageListener;
  }

  public getXRPEURListener(){
    return this.XRPEURMessageListener;
  }
}
