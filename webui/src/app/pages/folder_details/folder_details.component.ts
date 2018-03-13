import {Component, OnInit} from '@angular/core';
import 'rxjs/add/operator/switchMap';
import {ActivatedRoute} from '@angular/router';
import {FolderService} from '../../services/api/folder.service';
import {Folder} from '../../models/folder.model';
import {Office} from '../../models/office.model';
import {NgForm} from '@angular/forms';
import {CourtService} from '../../services/api/court.service';

@Component({
    selector   : 's-folders-pg',
    templateUrl: './folder_details.component.html',
    styleUrls  : [ './folder_details.scss'],
})

export class FolderDetailsComponent implements OnInit {

    public folder: Folder = new Folder();

    public isLoading = false;

    public filteredStatus: any[] = [];
    public selectedStatus: string;
    public queryStatus: string;

    public filteredCourts: any[] = [];
    public selectedCourt: string;
    public queryCourt: string;

    public showPencilStatus: boolean;
    public showInputStatus: boolean;
    public showPencilCourt: boolean;
    public showInputCourt: boolean;
    public showPencilSendingType: boolean;
    public showInputSendingType: boolean;

    constructor(private activateRoute: ActivatedRoute,
                private folderService: FolderService,
                private courtService: CourtService) {
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

    filterStatus(event) {
        let _self = this;
        let query = event.query;
        _self.folderService.getFolderStatus(query).subscribe(function(courts) {
            _self.filteredCourts = _self.filterItem(query, courts);
            _self.queryCourt = query;
        });
    }

    filterCourts(event) {
        let _self = this;
        let query = event.query;
        _self.courtService.getCourts(query).subscribe(function(courts) {
            _self.filteredCourts = _self.filterItem(query, courts);
            _self.queryCourt = query;
        });
    }

    filterItem(query, items: any[]): any[] {
        let filtered: any[] = [];
        for (let i = 0; i < items.length; i++) {
            let guilty = items[i];
            if (guilty.name.toLowerCase().indexOf(query.toLowerCase()) === 0) {
                filtered.push(guilty);
            }
        }
        return filtered;
    }
}
