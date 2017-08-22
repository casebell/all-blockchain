import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import { DataSource } from '@angular/cdk';
import { Observable } from 'rxjs/Observable';
import { Coin } from '../../model/coin.model';
import { CoinSidenavService, 
         CoinSidenavSource } from "./coin-sidenav.service";

@Component({
  selector: 'abc-coin-sidenav',
  templateUrl: './coin-sidenav.component.html',
  styleUrls: ['coin-sidenav.scss']
})
export class CoinSidenavComponent implements OnInit {

  displayedColumns = ['name'];
  coinDatabase = new CoinDatabase();
  dataSource: CoinSidenavSource | null;

  @ViewChild('filter') filter: ElementRef;

  constructor(private coinSidenavService:CoinSidenavService) { }

  ngOnInit() {
    this.dataSource = new ExampleDataSource(this.coinDatabase);
    Observable.fromEvent(this.filter.nativeElement, 'keyup')
      .debounceTime(150)
      .distinctUntilChanged()
      .subscribe(() => {
        if (!this.dataSource) { return; }
        this.dataSource.filter = this.filter.nativeElement.value;
      });
  }

}


/** Constants used to fill up our data base. */
const NAMES = ['BTC', 'ETH', 'ETC', 'BCC', 'LTC', 'ZEC'];

/** An example database that the data source uses to retrieve data for the table. */
export class CoinDatabase {
  /** Stream that emits whenever the data has been modified. */
  dataChange: BehaviorSubject<Coin[]> = new BehaviorSubject<Coin[]>([]);
  get data(): Coin[] { return this.dataChange.value; }

  constructor(private _coinSidenavService:CoinSidenavService) {
    // Fill up the database with 100 users.
    
  }

  /** Adds a new user to the database. */
  addUser() {
    const copiedData = this.data.slice();
    copiedData.push(this.createCoin());
    this.dataChange.next(copiedData);
  }

  /** Builds and returns a new User. */
  private createCoin() {
    const name = NAMES[Math.round(Math.random() * (NAMES.length - 1))] 
    
    return {
      id: this.data.length + 1,
      name: name,
    };
  }
}

/**
* Data source to provide what data should be rendered in the table. Note that the data source
* can retrieve its data in any way. In this case, the data source is provided a reference
* to a common data base, ExampleDatabase. It is not the data source's responsibility to manage
* the underlying data. Instead, it only needs to take the data and send the table exactly what
* should be rendered.
*/
export class ExampleDataSource extends DataSource<any> {
  _filterChange = new BehaviorSubject('');
  get filter(): string { return this._filterChange.value; }
  set filter(filter: string) { this._filterChange.next(filter); }

  constructor(private _coinSidenavService: CoinSidenavService) {
    super();
  }

  /** Connect function called by the table to retrieve one stream containing the data to render. */
  connect(): Observable<Coin[]> {
    const displayDataChanges = [
      this._exampleDatabase.dataChange,
      this._filterChange,
    ];

    return Observable.merge(...displayDataChanges).map(() => {
      return this._exampleDatabase.data.slice().filter((item: Coin) => {
        let searchStr = (item.name.toLowerCase());
        return searchStr.indexOf(this.filter.toLowerCase()) != -1;
      });
    });
  }

  disconnect() { }
}