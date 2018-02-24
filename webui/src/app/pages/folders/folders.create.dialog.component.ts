import {Component, Inject} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';

@Component({
    selector: 'folders-create-dialog-component',
    templateUrl: 'folders.create.dialog.component.html',
})
export class FoldersCreateDialogComponent {

    constructor(
        public dialogRef: MatDialogRef<FoldersCreateDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any) { }

    onNoClick(): void {
        this.dialogRef.close();
    }

}
