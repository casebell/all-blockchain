import { TestBed, inject } from '@angular/core/testing';
import { BitfinexWebsocketService } from './bitfinex-websocket.service';

describe('BitfinexWebsocketService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [BitfinexWebsocketService]
    });
  });

  it('should be created', inject([BitfinexWebsocketService], (service: BitfinexWebsocketService) => {
    expect(service).toBeTruthy();
  }));
});
