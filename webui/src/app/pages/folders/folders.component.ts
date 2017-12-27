import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {FolderService} from '../../services/api/folder.service';


@Component({
    selector   : 's-folders-pg',
    templateUrl: './folders.component.html',
    styleUrls  : [ './folders.scss'],
})

export class FoldersComponent implements OnInit {

    columns: any[];
    rows: any[];
    orderByStatusData: any[] = [];
    isLoading: boolean = false;
    constructor(private router: Router, private folderService: FolderService) { }

    ngOnInit(): void {
        var me = this;
        me.getPageData();
        this.columns = [
            {prop: 'number'      , name: 'Numéro dossier'       , width: 65  },
            {prop: 'status'      , name: 'Status'               , width: 85  },
            {prop: 'createDate'  , name: 'Date de création'     , width: 105 },
            {prop: 'modifDate'   , name: 'Date de modification' , width: 85  },
            {prop: 'closeDate'   , name: 'Date de clôture'      , width: 150 }
        ];
    }

    getPageData() {
        var me = this;
        me.isLoading = true;
        me.folderService.getFolder()
            .mergeMap(function(statusData) {
                me.orderByStatusData = statusData.items.map(function(v,i,a){
                    return {name:v.name, value:v.value}
                });
                console.log('Got Order Stats');
                return me.folderService.getFolder();
            })
            .subscribe(function(folderData){
                me.rows = folderData;
                me.isLoading = false;
                console.log('Got folder Data');
            });
    }
}
