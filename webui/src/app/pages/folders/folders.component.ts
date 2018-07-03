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
import {Dropdown, OverlayPanel} from 'primeng/primeng';
import {Action} from 'app/models/action.model';
import {ActionService} from '../../services/api/action.service';

@Component( {
    selector   :  's-folders-pg',
    templateUrl:  './folders.component.html',
    styleUrls  :  [ './folders.scss'],
})

export class FoldersComponent implements OnInit {
    @ViewChild(NgForm) filterFoldersForm:  NgForm;
    @ViewChild(OverlayPanel) overlayPanel:  OverlayPanel;
    @ViewChild('assignUserDropdown') assignUserDropdown:  Dropdown;
    @ViewChild('addActionDropdown')  addActionDropdown:  Dropdown;
    @ViewChild('changeStatusDropdown')  changeStatusDropdown:  Dropdown;
    @ViewChild(NgForm) createFolderForm:  NgForm;

    public listFolders = [];
    public selectedFolders = [];

    public listFolderStatus = [];

    public listCourts = [];
    public listOffices = [];

    public folderCreated:  Folder;
    public hideMessage = true;

    animal:  string;
    name:  string;

    public listUsers = [];
    public selectedUser:  User;
    public showAssignUserOPanel = false;

    public listActions = [];
    public selectedAction:  Action;
    public showAddActionOPanel = false;

    public listStatus = [];
    public selectedStatus:  string;
    public showChangeStatusOPanel = false;

    constructor(private router:  Router,
                private spinnerService:  SpinnerService,
                private folderService:  FolderService,
                private victimService:  VictimService,
                private guiltyService:  GuiltyService,
                private courtService:   CourtService,
                private officeService:  OfficeService,
                private userService:  UserService,
                private actionService:  ActionService,
                public dialog:  MatDialog) { }

    ngOnInit():  void {
        let _self = this;
        _self.getFolders();
        _self.folderService.getFolderStatus('')
            .subscribe(function(folderStatus) {
                _self.listFolderStatus = folderStatus.items;
                for (let s of folderStatus.items) {
                    _self.listStatus.push( {label:  s , value:  s});
                }
        });

        _self.courtService.getCourts()
            .subscribe(function(courts) {
                _self.listCourts = courts;
            });

        _self.officeService.getOffices()
            .subscribe(function(offices) {
                _self.listOffices = offices;
            });

        _self.userService.getUsers()
            .subscribe(function(users) {
               for (let u of users) {
                   _self.listUsers.push( {label:  u.firstName + ' ' + u.lastName , value :  u});
               }
            });
        _self.actionService.getActions()
            .subscribe(function(actions) {
                for (let a of actions) {
                    _self.listActions.push( {label:  a.name , value :  a});
                }
            });
    }

    openCreateFolderDialog():  void {
        let _self = this;
        let dialogRef = _self.dialog.open(FoldersCreateDialogComponent, {
            width:  '60%',
            direction:  'rtl',
            data:   { name:  _self.name, animal:  _self.animal }
        });

        dialogRef.afterClosed().subscribe( result => {
            console.log('The dialog was closed');
            if (result !== undefined && result != null) {
                    _self.folderCreated = result;
                    _self.hideMessage = false;
            }

        });
    }

    onSubmitFilterFoldersForm(f:  NgForm) {
        this.getFolders(f);
    }

    getFolders(f?:  NgForm) {
        let _self = this;
        _self.spinnerService.showSpinner();

        let folderNumber, office, status, victim, guilty;
        if (f && f.value) {
            folderNumber = f.value.folderNumber;
            office = f.value.office;
            status = f.value.status;
            victim = f.value.victim;
            guilty = f.value.guilty;
        }
        _self.folderService.getFolders(folderNumber, office, status, victim, guilty)
            .subscribe(function(folderData) {
                _self.listFolders = folderData;
                _self.selectedFolders = [];
                _self.spinnerService.hideSpinner();
            });
    }

    assignUser(folders:  Folder[], user:  User):  void {
        let _self =  this;
        _self.spinnerService.showSpinner();
        this.folderService.assignUser(folders.map(f => f.id ), user).subscribe(jsonResp => {
            _self.overlayPanel.hide();
            _self.getFolders(_self.filterFoldersForm);
        });
    }

    addAction(folders:  Folder[], action:  Action):  void {
        let _self =  this;
        _self.spinnerService.showSpinner();
        this.folderService.addActionToListOfFolders(folders.map(f => f.id ), action).subscribe(jsonResp => {
            _self.overlayPanel.hide();
            _self.getFolders(_self.filterFoldersForm);
        });
    }

    changeStatus(folders:  Folder[], status:  string):  void {
        let _self =  this;
        _self.spinnerService.showSpinner();
        this.folderService.changeStatusToListOfFolders(folders.map(f => f.id ), status).subscribe(jsonResp => {
            _self.overlayPanel.hide();
            _self.getFolders(_self.filterFoldersForm);
        });
    }

    showAssignUserDropdown():  void {
       this.selectedUser = null;
       this.showAssignUserOPanel = true;
       if (this.assignUserDropdown) {
           this.assignUserDropdown.value = null;
       }
       this.showAddActionOPanel = false;
       this.showChangeStatusOPanel = false;
    }

    showAddActionDropdown():  void {
        this.selectedAction = null;
        this.showAddActionOPanel = true;
        if (this.addActionDropdown) {
            this.addActionDropdown.value = null;
        }
        this.showAssignUserOPanel = false;
        this.showChangeStatusOPanel = false;
    }

    showChangeFolderStatusDropdown():  void {
        this.selectedStatus = null;
        this.showChangeStatusOPanel = true;
        if (this.changeStatusDropdown) {
            this.changeStatusDropdown.value = null;
        }
        this.showAssignUserOPanel = false;
        this.showAddActionOPanel = false;
    }

}
