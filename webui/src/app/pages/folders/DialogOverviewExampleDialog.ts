import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {Component, Inject} from "@angular/core";

@Component({
    selector: 'dialog-overview-example-dialog',
    templateUrl: 'dialog-overview-example-dialog.html',
})
export class DialogOverviewExampleDialog {

    constructor(
        public dialogRef: MatDialogRef<DialogOverviewExampleDialog>,
        @Inject(MAT_DIALOG_DATA) public data: any) { }

    onNoClick(): void {
        this.dialogRef.close();
    }

}
