import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {FolderService} from '../../services/api/folder.service';
import {VictimService} from '../../services/api/victim.service';
import {NgForm} from '@angular/forms';
import {GuiltyService} from '../../services/api/guilty.service';
import {Court} from '../../models/court.model';
import {CourtService} from '../../services/api/court.service';
import {OfficeService} from '../../services/api/office.service';
import {MatDialog} from '@angular/material';
import {FoldersCreateDialogComponent} from './folders.create.dialog.component';

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
    @ViewChild(NgForm) filterFoldersForm: NgForm;
    @ViewChild(NgForm) createFolderForm: NgForm;



    rows: any[];
    isLoading = false;
    folderStatus: any[];

    validateLoading = false;
    loadingIndicator= true;
    reorderable = false;

    public showCreateFolderModal  = false;

    public court: Court;
    public number: string;
    public offence: string;
    public listVictims = [];
    public selectedVictims = [];

    public listGuilties = [];
    public selectedGuilties = [];

    public listCourts = [];
    public listOffices = [];

    animal: string;
    name: string;

    constructor(private router: Router,
                private folderService: FolderService,
                private victimService: VictimService,
                private guiltyService: GuiltyService,
                private courtService:  CourtService,
                private officeService: OfficeService,
                public dialog: MatDialog) { }

    ngOnInit(): void {
        let me = this;
        me.getFolders();
        me.folderService.getFolderStatus()
            .subscribe(function(folderStatus) {
                me.folderStatus = folderStatus.items;
        });

        me.victimService.getVictims()
            .subscribe(function(victims) {
                victims.forEach(function(v) {
                    me.listVictims.push({id: v.id, text: v.name});
                });
        });

        me.guiltyService.getGuilties()
            .subscribe(function(guilties) {
                guilties.forEach(function(g) {
                    me.listGuilties.push({id: g.id, text: g.name});
                });
            });

        me.courtService.getCourts()
            .subscribe(function(courts) {
                me.listCourts = courts;
            });

        me.officeService.getOffices()
            .subscribe(function(offices) {
                me.listOffices = offices;
            });
    }

    openDialog(): void {
        let dialogRef = this.dialog.open(FoldersCreateDialogComponent, {
            width: '60%',
            data: { name: this.name, animal: this.animal }
        });

        dialogRef.afterClosed().subscribe(result => {
            console.log('The dialog was closed');
            this.animal = result;
        });
    }

    onSubmitFilterFoldersForm(f: NgForm) {
        this.getFolders(f);
    }

    onSubmitCreateFolderForm(f: NgForm) {
        this.addFolder(f);
    }

    getFolders(f?:NgForm) {
        let me = this;
        me.isLoading = true;

        let folderNumber, office, status, victim, guilty;
        if(f && f.value){
            folderNumber = f.value.folderNumber;
            office = f.value.office;
            status = f.value.status;
            victim = f.value.victim;
            guilty = f.value.guilty;
        }
        me.folderService.getFolders(folderNumber, office, status,victim, guilty)
            .subscribe(function(folderData) {
                me.rows = folderData;
                me.isLoading = false;
            });
    }

    addFolder(f: NgForm) {
        let me = this;
        me.folderService.addFolder(f.value).subscribe(function(folder) {
          alert(folder);
      });
    }

    openModalCreateFolder(): void {
        this.showCreateFolderModal = true;
    }

    closeCreateFolderModal(): void {
        this.showCreateFolderModal = false;
    }
}
