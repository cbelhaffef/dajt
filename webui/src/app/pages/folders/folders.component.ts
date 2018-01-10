import {Component, OnInit, ViewChild, TemplateRef} from '@angular/core';
import {Router} from '@angular/router';
import {FolderService} from '../../services/api/folder.service';
import {NgForm} from '@angular/forms';


@Component({
    selector   : 's-folders-pg',
    templateUrl: './folders.component.html',
    styleUrls  : [ './folders.scss'],
})

export class FoldersComponent implements OnInit {
    @ViewChild('folderStatusCellTpl') folderStatusCellTpl: TemplateRef<any>;
    @ViewChild('folderIdTpl') folderIdTpl: TemplateRef<any>;
    @ViewChild(NgForm) f: NgForm;

    columns: any[];
    rows: any[];
    isLoading = false;
    folderStatus: any[];

    constructor(private router: Router, private folderService: FolderService) { }

    ngOnInit(): void {
        let me = this;
        me.getPageData();
        this.columns = [
            {prop: 'number'        , name: 'Numéro dossier'       , width: 105, cellTemplate: this.folderIdTpl  },
            {prop: 'status'        , name: 'Status'               , width: 85 , celleTemplate: this.folderStatusCellTpl },
            {prop: 'createDate'    , name: 'Date de création'     , width: 105 },
            {prop: 'lastModifDate' , name: 'Date de modification' , width: 110 },
            {prop: 'closeDate'     , name: 'Date de clôture'      , width: 105 },
            {prop: 'victim'     , name: 'Victim'      , width: 105 },
            {prop: 'guilty'     , name: 'Guilty'      , width: 105 }
        ];
        me.folderService.getFolderStatus()
            .subscribe(function(folderStatus) {
                me.folderStatus = folderStatus.items;
            });
    }

    getPageData(folderName?: string, status?: string) {
        let me = this;
        me.isLoading = true;
        me.folderService.getFolder(folderName, status)
            .subscribe(function(folderData) {
                me.rows = folderData;
                me.isLoading = false;
            });
    }

    onSubmit(f: NgForm) {
        f = this.f;
        console.log(f.controls['folderNumber'].value);
        console.log(f.controls['status'].value);
        this.getPageData(f.controls['folderNumber'].value, f.controls['status'].value);
    }

}
