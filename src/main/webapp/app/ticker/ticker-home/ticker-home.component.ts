import { Component, OnInit } from '@angular/core';
import * as Muuri from 'muuri'
@Component({
  selector: 'jhi-ticker-home',
  templateUrl: './ticker-home.component.html',
  styleUrls: ['ticker-home.scss']
})
export class TickerHomeComponent implements OnInit {

  constructor() { }

  ngOnInit() {
      var grid = new Muuri('.grid', {
          dragEnabled: true
      });

  }

}
