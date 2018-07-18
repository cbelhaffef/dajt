import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {FolderService} from '../../services/api/folder.service';
import {VictimService} from '../../services/api/victim.service';
import {NgForm} from '@angular/forms';
import {AccusedService} from '../../services/api/accused.service';
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
import {FolderResponse} from '../../models/folder.response.model';
import {LazyLoadEvent, Message} from 'primeng/api';
import {StatusService} from '../../services/api/status.service';
import {Status} from '../../models/status.model';
import {MessageService} from 'primeng/components/common/messageservice';

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

    public listFolders: FolderResponse = new FolderResponse();
    public selectedFolders = [];

    public listCourts = [];
    public listOffices = [];

    public folderCreated:  Folder;

    public  msgs: Message[] = [];

    animal:  string;
    name:  string;

    public listUsers = [];
    public selectedUser:  User;
    public showAssignUserOPanel = false;

    public listActions = [];
    public selectedAction:  any;
    public showAddActionOPanel = false;

    public statusList = [];
    public selectedStatus: any;
    public showChangeStatusOPanel = false;

    public loading: boolean;

    constructor(private router:  Router,
                private messageService: MessageService,
                private spinnerService:  SpinnerService,
                private folderService:  FolderService,
                private victimService:  VictimService,
                private guiltyService:  AccusedService,
                private courtService:   CourtService,
                private officeService:  OfficeService,
                private statusService:  StatusService,
                private userService:  UserService,
                private actionService:  ActionService,
                public dialog:  MatDialog) { }

    ngOnInit():  void {
        let _self = this;
        _self.getFolders();
        _self.statusService.getStatus()
            .subscribe(function(statusList) {
                _self.statusList = statusList;
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
                   _self.listUsers.push( {label:  u.firstname + ' ' + u.lastname , value :  u});
               }
            });

        _self.actionService.getActions()
            .subscribe(function(actions) {
                for (let a of actions) {
                    _self.listActions.push( {label:  a.name , value :  a});
                }
            });
    }

    loadFoldersLazy(event: LazyLoadEvent) {
        let _self =  this;
        _self.getFolders(_self.filterFoldersForm, event.first);
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
                    _self.showMessage('success', '', '');
            }

        });
    }

    onSubmitFilterFoldersForm(f:  NgForm) {
        let _self = this;
        _self.getFolders(f);
    }

    getFolders(f?:  NgForm, p?: number) {
        let _self = this;
        _self.spinnerService.showSpinner();
        _self.loading = true;

        let folderNumber, office, status, victim, accused, page;
        if (f && f.value) {
            folderNumber = f.value.folderNumber;
            office = f.value.office;
            status = f.value.status;
            victim = f.value.victim;
            accused = f.value.accused;
        }
        if (p) {
            if (p > 0) {
                p = Math.floor(_self.listFolders.totalItems / p);
            }
            page = p;
        }
        _self.folderService.getFolders(folderNumber, office, status, victim, accused, page)
            .subscribe(function(folderData) {
                _self.listFolders = folderData;
                _self.selectedFolders = [];
                _self.spinnerService.hideSpinner();
                _self.loading = false;
            });
    }

    assignUser(folders:  Folder[], user:  User):  void {
        let _self =  this;
        _self.spinnerService.showSpinner();
        _self.folderService.assignUser(folders.map(f => f.id ), user).subscribe(jsonResp => {
            _self.overlayPanel.hide();
            _self.getFolders(_self.filterFoldersForm);
        });
    }

    addAction(folders:  Folder[], action:  Action):  void {
        let _self =  this;
        _self.spinnerService.showSpinner();
        _self.folderService.addActionToListOfFolders(folders.map(f => f.id ), action).subscribe(jsonResp => {
            _self.overlayPanel.hide();
            _self.getFolders(_self.filterFoldersForm);
        });
    }

    changeStatus(folders:  Folder[], status:  Status):  void {
        let _self =  this;
        _self.spinnerService.showSpinner();
        _self.folderService.changeStatusToListOfFolders(folders.map(f => f.id ), status).subscribe(jsonResp => {
            _self.overlayPanel.hide();
            _self.getFolders(_self.filterFoldersForm);
        });
    }

    showAssignUserDropdown():  void {
       let _self = this;
       _self.selectedUser = null;
       _self.showAssignUserOPanel = true;
       if (_self.assignUserDropdown) {
           _self.assignUserDropdown.value = null;
       }
       _self.showAddActionOPanel = false;
       _self.showChangeStatusOPanel = false;
    }

    showAddActionDropdown():  void {
        let _self = this;
        _self.selectedAction = null;
        _self.showAddActionOPanel = true;
        if (_self.addActionDropdown) {
            _self.addActionDropdown.value = null;
        }
        _self.showAssignUserOPanel = false;
        _self.showChangeStatusOPanel = false;
    }

    showChangeFolderStatusDropdown():  void {
        let _self = this;
        _self.selectedStatus = null;
        _self.showChangeStatusOPanel = true;
        if (_self.changeStatusDropdown) {
            _self.changeStatusDropdown.value = null;
        }
        _self.showAssignUserOPanel = false;
        _self.showAddActionOPanel = false;
    }

    public getToolTipActionValue(listAction: Action[]): string {
        let actionValue = '';
        listAction.forEach(function (a: Action , i) {
            actionValue += a.name;
            if (i < listAction.length) {
                actionValue += ' \n';
            }
        });
        return actionValue;
    }

    showMessage(type: string, summary: string, detail: string) {
        this.msgs = [];
        this.msgs.push({severity: type, summary: summary, detail: detail});
    }

}
