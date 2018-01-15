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
    @ViewChild('folderNumberTpl') folderNumberTpl: TemplateRef<any>;
    @ViewChild('folderVictimsTpl') folderVictimsTpl: TemplateRef<any>;
    @ViewChild('folderGuiltiesTpl') folderGuiltiesTpl: TemplateRef<any>;
    @ViewChild(NgForm) f: NgForm;

    columns: any[];
    rows: any[];
    isLoading = false;
    folderStatus: any[];

    validateLoading = false;
    loadingIndicator= true;
    reorderable = false;

    constructor(private router: Router, private folderService: FolderService) { }

    ngOnInit(): void {
        let me = this;
        me.getPageData();
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

    parsePeople(people: any[]) {
        return people.map(function(elem) {
            return elem.firstName + ' ' + elem.lastName;
        }).join(' <br> ');
    }
}
