import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {FolderService} from '../../services/api/folder.service';
import {VictimService} from '../../services/api/victim.service';
import {NgForm} from '@angular/forms';
import {GuiltyService} from '../../services/api/guilty.service';
import {CourtService} from '../../services/api/court.service';
import {OfficeService} from '../../services/api/office.service';
import {MatDialog} from '@angular/material';
import {FoldersCreateDialogComponent} from './folders.create.dialog.component';
import {UserService} from '../../services/api/user.service';
import {User} from '../../models/user.model';
import {Folder} from '../../models/folder.model';
import {SpinnerService} from '../../services/spinner.service';
import {OverlayPanel} from 'primeng/primeng';

@Component({
    selector   : 's-folders-pg',
    templateUrl: './folders.component.html',
    styleUrls  : [ './folders.scss'],
})

export class FoldersComponent implements OnInit {
    @ViewChild(NgForm) filterFoldersForm: NgForm;
    @ViewChild(OverlayPanel) usersOPanel: OverlayPanel;
    @ViewChild(NgForm) createFolderForm: NgForm;

    public listFolders = [];
    public selectedFolders = [];

    public listFolderStatus = [];

    public listCourts = [];
    public listOffices = [];

    animal: string;
    name: string;

    public listUsers = [];
    public selectedUser: User;


    constructor(private router: Router,
                private spinnerService: SpinnerService,
                private folderService: FolderService,
                private victimService: VictimService,
                private guiltyService: GuiltyService,
                private courtService:  CourtService,
                private officeService: OfficeService,
                private userService: UserService,
                public dialog: MatDialog) { }

    ngOnInit(): void {
        const me = this;
        me.getFolders();
        me.folderService.getFolderStatus()
            .subscribe(function(folderStatus) {
                me.listFolderStatus = folderStatus.items;
        });

        me.courtService.getCourts()
            .subscribe(function(courts) {
                me.listCourts = courts;
            });

        me.officeService.getOffices()
            .subscribe(function(offices) {
                me.listOffices = offices;
            });

        me.userService.getUsers()
            .subscribe(function(users) {
               for (const u of users) {
                   me.listUsers.push({label: u.firstName + ' ' + u.lastName , value : u});
               }
            });
    }

    openCreateFolderDialog(): void {
        const dialogRef = this.dialog.open(FoldersCreateDialogComponent, {
            width: '40%',
            direction: 'rtl',
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

    getFolders(f?: NgForm) {
        const me = this;
        me.spinnerService.showSpinner();

        let folderNumber, office, status, victim, guilty;
        if (f && f.value) {
            folderNumber = f.value.folderNumber;
            office = f.value.office;
            status = f.value.status;
            victim = f.value.victim;
            guilty = f.value.guilty;
        }
        me.folderService.getFolders(folderNumber, office, status, victim, guilty)
            .subscribe(function(folderData) {
                me.listFolders = folderData;
                me.spinnerService.hideSpinner();
            });
    }

    assignUser(folders: Folder[], user: User): void {
        const me =  this;
        me.spinnerService.showSpinner();
        this.folderService.assignUser(folders.map(f => f.id ), user).subscribe(jsonResp => {
            me.usersOPanel.hide();
            me.selectedUser = null;
            me.getFolders(me.filterFoldersForm);
        });
    }

    onAfterHideUsersOPanel(): void {
        this.selectedUser = null;
    }
}
