import { Component, OnInit } from '@angular/core';
import {MatDialog, MatDialogRef} from '@angular/material';
import * as Muuri from 'muuri'
import { AddTickerDialogComponent } from './add-ticker-dialog/add-ticker-dialog.component';
@Component({
  selector: 'jhi-ticker-home',
  templateUrl: './ticker-home.component.html',
  styleUrls: ['ticker-home.scss']
})
export class TickerHomeComponent implements OnInit {

  constructor(public dialog: MatDialog) { }

  ngOnInit() {
      var grid = new Muuri('.grid', {
          dragEnabled: true
      });

  }

    openAddTickerDialog(): void {
        let dialogRef = this.dialog.open(AddTickerDialogComponent);

        dialogRef.afterClosed().subscribe(result => {
            console.log('The dialog was closed');
          //  this.animal = result;
        });
    }

}
