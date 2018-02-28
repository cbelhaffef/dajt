import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {FormControl, NgForm, Validators} from '@angular/forms';
import {OfficeService} from '../../services/api/office.service';

@Component({
    selector: 'folders-create-dialog-component',
    templateUrl: 'folders.create.dialog.component.html',
    styleUrls : ['./folders.create.dialog.component.css']
})
export class FoldersCreateDialogComponent implements OnInit {

    public listOffices = [];
    @ViewChild(NgForm) createFolderForm: NgForm;

    officeControl = new FormControl('', [Validators.required]);

    constructor(
        public officeService: OfficeService,
        public dialogRef: MatDialogRef<FoldersCreateDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: any) { }

    ngOnInit(): void {
        let _self = this;
        _self.officeService.getOffices()
            .subscribe(function(offices) {
                _self.listOffices = offices;
            });
    }

    onNoClick(): void {
        this.dialogRef.close();
    }


}
