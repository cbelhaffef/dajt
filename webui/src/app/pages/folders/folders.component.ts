import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {FolderService} from '../../services/api/folder.service';
import {VictimService} from '../../services/api/victim.service';
import {NgForm} from '@angular/forms';
import {GuiltyService} from '../../services/api/guilty.service';
import {Court} from '../../models/court.model';
import {CourtService} from '../../services/api/court.service';


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
    @ViewChild(NgForm) mf: NgForm;


    rows: any[];
    isLoading = false;
    folderStatus: any[];

    validateLoading = false;
    loadingIndicator= true;
    reorderable = false;

    basic = false;

    public court: Court;
    public number: string;
    public offence: string;
    public listVictims = [];
    public selectedVictims = [];

    public listGuilties = [];
    public selectedGuilties = [];

    public listCourts = [];

    constructor(private router: Router,
                private folderService: FolderService,
                private victimService: VictimService,
                private guiltyService: GuiltyService,
                private courtService:  CourtService) { }

    ngOnInit(): void {
        let me = this;
        me.getPageData();
        me.folderService.getFolderStatus()
            .subscribe(function(folderStatus) {
                me.folderStatus = folderStatus.items;
        });
        me.victimService.getVictims()
            .subscribe(function(victims) {
                victims.forEach(function(v) {
                    me.listVictims.push({id: v.id , text: v.lastName + ' ' + v.firstName});
                });
        });

        me.guiltyService.getGuilties()
            .subscribe(function(guilties) {
                guilties.forEach(function(g) {
                    me.listGuilties.push({id: g.id , text : g.lastName + ' ' + g.firstName});
                });
            });
        me.courtService.getCourts()
            .subscribe(function(courts) {
                me.listCourts = courts;
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

    addFolder(mf: NgForm) {
        let me = this;
        mf = me.mf;
        me.folderService.addFolder(mf.value).subscribe(function(folder) {
          alert(folder);
          me.closeModal();
      });
    }

    closeModal() {
        this.basic = false;
    }


    parsePeople(people: any[]) {
        return people.map(function(elem) {
            return elem.firstName + ' ' + elem.lastName;
        }).join(' <br> ');
    }
}
