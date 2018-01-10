import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

export class State {
    constructor(public name: string,
                public population: string,
                public flag: string) {
    }
}


@Component({
    selector: 'jhi-dialog',
    templateUrl: './dialog.component.html',
})
export class DialogComponent{


    constructor(
                public dialogRef: MatDialogRef<DialogComponent>,
                @Inject(MAT_DIALOG_DATA) public data: any) {}


    onNoClick(): void {
        this.dialogRef.close();
    }

}
