import {Component, OnInit} from '@angular/core';
import 'rxjs/add/operator/switchMap';
import {ActivatedRoute} from '@angular/router';
import {FolderService} from '../../services/api/folder.service';
import {Folder} from '../../models/folder.model';
import {Office} from '../../models/office.model';
import {NgForm} from '@angular/forms';

@Component({
    selector   : 's-folders-pg',
    templateUrl: './folder_details.component.html',
    styleUrls  : [ './folder_details.scss'],
})

export class FolderDetailsComponent implements OnInit{

    public folder: Folder;

    public isLoading: boolean = false;

    constructor(private activateRoute: ActivatedRoute,
                private folderService: FolderService){
    }

    ngOnInit(): void {
        const me = this;
        me.isLoading = true;
        me.folderService.getFolderDetails(me.activateRoute.snapshot.params.id)
            .subscribe(function(folder) {
                me.folder = folder;
                me.isLoading = false;
            });
    }

    addFolder(f: NgForm) {
        const me = this;
        me.isLoading = true;
        me.folderService.addFolder(f.value)
            .subscribe(function(folder) {
                alert(folder);
                me.isLoading = false;
            });
    }

}
