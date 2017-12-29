import { Component, OnInit } from '@angular/core';
import {MatDialog, MatDialogRef} from '@angular/material';
import * as Muuri from 'muuri'
import { AddTickerDialogComponent } from './add-ticker-dialog/add-ticker-dialog.component';
import { TickerService } from '../ticker.service';
import { Principal } from '../../shared';
@Component({
  selector: 'jhi-ticker-home',
  templateUrl: './ticker-home.component.html',
  styleUrls: ['ticker-home.scss']
})
export class TickerHomeComponent implements OnInit {

  userId:number;
  constructor(public dialog: MatDialog,
              private principal: Principal,
              private tickerService: TickerService) {
      this.principal.identity().then((account) => {
          this.userId = account.id;
      });
  }

  ngOnInit() {
      var grid = new Muuri('.grid', {
          dragEnabled: true
      });
      this.getTickers();
  }


     getTickers() {
        this.tickerService.getTickers(this.userId).subscribe((result)=>{
            console.log('get Tickers : ', result);
        })
    }


    openAddTickerDialog(): void {
        let dialogRef = this.dialog.open(AddTickerDialogComponent);

        dialogRef.afterClosed().subscribe(result => {
            console.log('The dialog was closed');
          //  this.animal = result;
            if(result)
            {
                //Todo : patch my ticker
                this.getTickers();
            }
        });
    }

}
