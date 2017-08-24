import {AfterViewInit, Component, ElementRef, Inject, OnInit} from '@angular/core';
import {DOCUMENT} from '@angular/common'
@Component({
  selector: 'abc-coin-home',
  templateUrl: './coin-home.component.html',
  styles: []
})
export class CoinHomeComponent implements OnInit,AfterViewInit {
//type="text/javascript" src="//widget.coindesk.com/bpiticker/coindesk-widget.min.js"
  constructor( @Inject(DOCUMENT) private document,
                private elementRef:ElementRef) { }

  ngOnInit() {
  }

  ngAfterViewInit() {
    let s = this.document.createElement("script");
    s.type = "text/javascript";
    s.src = "//widget.coindesk.com/bpiticker/coindesk-widget.min.js";
    this.elementRef.nativeElement.appendChild(s);
  }
}
