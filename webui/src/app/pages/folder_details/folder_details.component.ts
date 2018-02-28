import {Component, OnInit} from '@angular/core';
import 'rxjs/add/operator/switchMap';
import {ActivatedRoute} from '@angular/router';
import {FolderService} from '../../services/api/folder.service';
import {Folder} from '../../models/folder.model';
import {Office} from '../../models/office.model';

@Component({
    selector   : 's-folders-pg',
    templateUrl: './folder_details.component.html',
    styleUrls  : [ './folder_details.scss'],
})

export class FolderDetailsComponent implements OnInit{

    public folder: Folder;

    constructor(private activateRoute: ActivatedRoute,
                private folderService: FolderService){
    }

    ngOnInit(): void {
        let me = this;
        me.folderService.getFolderDetails(me.activateRoute.snapshot.params.id)
            .subscribe(function(folder) {
                me.folder = folder;
            });
    }


}
