import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';

@Injectable()
export class DataService {

  private openMessageSource = new BehaviorSubject<string>('default message');
  currentOpenMessage = this.openMessageSource.asObservable();

  constructor() { }
  changeOpenMessage(message: string) {
    this.openMessageSource.next(message)
  }
}
