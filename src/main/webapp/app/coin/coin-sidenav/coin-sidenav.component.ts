import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { CoinSidenavService,
         CoinSidenavSource } from './coin-sidenav.service';

@Component({
  selector: 'abc-coin-sidenav',
  templateUrl: './coin-sidenav.component.html',
  styleUrls: ['coin-sidenav.scss']
})
export class CoinSidenavComponent implements OnInit {

  displayedColumns = ['name'];
  dataSource: CoinSidenavSource | null;

 // @ViewChild('filter') filter: ElementRef;

  constructor(private coinSidenavService:CoinSidenavService) { }

  ngOnInit() {

    this.coinSidenavService.findAll().subscribe(
        coins=>{
            console.log('coins : ', coins);
            this.dataSource = new CoinSidenavSource(coins)
        }
    );
/*    Observable.fromEvent(this.filter.nativeElement, 'keyup')
      .debounceTime(150)
      .distinctUntilChanged()
      .subscribe(() => {
        if (!this.dataSource) { return; }
        this.dataSource.filter = this.filter.nativeElement.value;
      });*/
  }

}
