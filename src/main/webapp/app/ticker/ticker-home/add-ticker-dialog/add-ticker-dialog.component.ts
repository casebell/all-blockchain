import { Component, OnInit,Inject } from '@angular/core';
import {FormBuilder, FormGroup, Validators,FormControl} from '@angular/forms';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material';

import {Observable} from 'rxjs/Observable';
import {startWith} from 'rxjs/operators/startWith';
import {map} from 'rxjs/operators/map';
import { TickerService } from '../../ticker.service';
import { ResponseWrapper } from '../../../shared';
import { Market, MarketService } from '../../../entities/market';

export class State {
    constructor(public name: string,
                public population: string,
                public flag: string
               ) { }
}


@Component({
  selector: 'jhi-add-ticker-dialog',
  templateUrl: './add-ticker-dialog.component.html',
  styles: []
})
export class AddTickerDialogComponent implements OnInit {
    firstFormGroup: FormGroup;
    secondFormGroup: FormGroup;
    isLinear = true;
    stateCtrl: FormControl;
    marketCtrl: FormControl;
    filteredStates: Observable<any[]>;
    filteredMarkets: Observable<any[]>;

    states: State[] = [
        {
            name: 'Arkansas',
            population: '2.978M',
            // https://commons.wikimedia.org/wiki/File:Flag_of_Arkansas.svg
            flag: 'https://upload.wikimedia.org/wikipedia/commons/9/9d/Flag_of_Arkansas.svg'
        },
        {
            name: 'California',
            population: '39.14M',
            // https://commons.wikimedia.org/wiki/File:Flag_of_California.svg
            flag: 'https://upload.wikimedia.org/wikipedia/commons/0/01/Flag_of_California.svg'
        },
        {
            name: 'Florida',
            population: '20.27M',
            // https://commons.wikimedia.org/wiki/File:Flag_of_Florida.svg
            flag: 'https://upload.wikimedia.org/wikipedia/commons/f/f7/Flag_of_Florida.svg'
        },
        {
            name: 'Texas',
            population: '27.47M',
            // https://commons.wikimedia.org/wiki/File:Flag_of_Texas.svg
            flag: 'https://upload.wikimedia.org/wikipedia/commons/f/f7/Flag_of_Texas.svg'
        }
    ];
    markets:Market[];
  constructor(private _formBuilder: FormBuilder,
              private marketService:MarketService,
              public dialogRef: MatDialogRef<AddTickerDialogComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
          this.stateCtrl = new FormControl();
          this.marketCtrl = new FormControl();
          this.filteredStates = this.stateCtrl.valueChanges
              .pipe(
              startWith(''),
              map(state => state ? this.filterStates(state) : this.states.slice())
          );
          this.filteredMarkets = this.marketCtrl.valueChanges
              .pipe(
              startWith(''),
              map(market => market ? this.filterMarkets(market) : this.markets.slice())
          );
  }

  ngOnInit() {

      this.marketService.query().subscribe(
          (res: ResponseWrapper) => {
              console.log('get all markets', res.json);
              this.markets = res.json;
          },
          (res: ResponseWrapper) => console.log("error : ",res.json)
      );
    this.firstFormGroup = this._formBuilder.group({
        firstCtrl: ['', Validators.required]
    });
    this.secondFormGroup = this._formBuilder.group({
        secondCtrl: ['', Validators.required]
    });
  }
    filterStates(name: string) {
        return this.states.filter(state =>
            state.name.toLowerCase().indexOf(name.toLowerCase()) === 0);
    }

    filterMarkets(name: string) {
        return this.markets.filter(market =>
            market.name.toLowerCase().indexOf(name.toLowerCase()) === 0);
    }

}
