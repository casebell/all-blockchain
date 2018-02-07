import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'airdrops-item',
  templateUrl: './airdrops-item.component.html',
  styles: []
})
export class AirdropsItemComponent implements OnInit {

  @Input() airDrop;
  constructor() { }
  ngOnInit() {
  }

}
