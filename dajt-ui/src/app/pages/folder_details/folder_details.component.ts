import {Component, OnInit, ViewChild} from '@angular/core';
import 'rxjs/operators';
import {ActivatedRoute} from '@angular/router';
import {FolderService} from '../../services/api/folder.service';
import {Folder} from '../../models/folder.model';
import {FormBuilder, FormControl, FormGroup, NgForm, Validators} from '@angular/forms';
import {CourtService} from '../../services/api/court.service';
import {VictimService} from '../../services/api/victim.service';
import {AccusedService} from '../../services/api/accused.service';
import {ActionService} from '../../services/api/action.service';
import {Message} from 'primeng/api';
import {Court} from '../../models/court.model';
import {UserInfoService} from '../../services/user-info.service';
import {StatusService} from '../../services/api/status.service';
import {PriorityService} from '../../services/api/priority.service';
import {CommonService} from '../../services/common.service';
import {Status} from '../../models/status.model';
import {Priority} from '../../models/priority.model';
import {Victim} from '../../models/victim.model';
import {Accused} from '../../models/accused.model';
import {MessageService} from 'primeng/components/common/messageservice';
import {Action} from '../../models/action.model';
import {SharedService} from '../../services/shared.service';
import {User} from '../../models/user.model';
import {Dropdown, OverlayPanel} from 'primeng/primeng';
import {UserService} from '../../services/api/user.service';

@Component( {
    selector   :  'app-folders-pg',
    templateUrl:  './folder_details.component.html',
    styleUrls  :  [ './folder_details.scss'],
    providers : [MessageService]
})

export class FolderDetailsComponent implements OnInit  {

    @ViewChild(NgForm) updateDetailfolderForm:  NgForm;
    @ViewChild(OverlayPanel) overlayPanel:  OverlayPanel;
    @ViewChild('assignUserDropdown') assignUserDropdown:  Dropdown;

    msgs:  Message[] = [];

    public folder:  Folder = new Folder();

    public submitted:  boolean;
    public isLoading = false;

    public showUpdateDetail = false;
    public showUpdateVictims = false;
    public showUpdateAccused = false;
    public showUpdateActions = false;
    public showUpdateComments = false;

    public listUsers = [];
    public selectedUser:  User;
    public showAssignUserOPanel = false;

    public statusList: Status[] = [];
    public prioritiesList: Priority[] = [];
    public actionsList: any[] = [];

    public victim: string;
    public accused: string;

    public selectedVictims:  Victim[] = [];
    public selectedAccused:  Accused[] = [];
    public selectedAction: any;

    public filteredCourts:  any[] = [];
    public queryCourt:  string;
    public selectedCourt: Court;


    constructor(private activateRoute:  ActivatedRoute,
                private folderService:  FolderService,
                private victimService:  VictimService,
                private guiltyService:  AccusedService,
                private actionService:  ActionService,
                private statusService:  StatusService,
                private priorityService: PriorityService,
                private courtService:  CourtService,
                private commonService: CommonService,
                private userService: UserService,
                private userInfoService: UserInfoService,
                private messageService: MessageService,
                private sharedService: SharedService,
                private fb:  FormBuilder) {
    }

    ngOnInit():  void {
        const _self = this;
        _self.isLoading = true;

        _self.sharedService.toggle$.subscribe(item  => {
            _self.msgs.push({severity: 'error', summary: ' خطأ داخلي في الخادم ', detail: item['message'] });
        });
        _self.userService.getUsers()
            .subscribe(function(users) {
                for (let u of users) {
                    _self.listUsers.push( {label:  u.firstname + ' ' + u.lastname , value :  u});
                }
            });
        _self.folderService.getFolderDetails(_self.activateRoute.snapshot.params.id)
            .subscribe(function(folder) {
                _self.folder = folder;
                _self.isLoading = false;
            });
        _self.statusService.getStatus()
            .subscribe(function(statusList) {
                _self.statusList = statusList;
            });
        _self.priorityService.getPriorities()
            .subscribe(function(prioritiesList) {
                _self.prioritiesList = prioritiesList;
            });

        _self.actionService.getActions()
            .subscribe(function(actionsList) {
                _self.actionsList = actionsList;
            });
    }

    showHideUpdateDetail(): void {
        let _self = this;
        _self.showUpdateDetail = !_self.showUpdateDetail;
        if (_self.showUpdateDetail) {
            _self.updateDetailfolderForm.setValue({
                status   : _self.folder.status,
                priority : _self.folder.priority,
                court    : _self.folder.court,
                administrationConcerned : _self.folder.administrationConcerned
            });
        } else {
            _self.updateDetailfolderForm.resetForm();
        }
    }

    showHideUpdateVictims(): void {
        let _self = this;
        _self.showUpdateVictims = !_self.showUpdateVictims;
        if (_self.showUpdateVictims) {
            _self.victim = '';
        }
    }

    showHideUpdateAccused(): void {
        let _self = this;
        _self.showUpdateAccused = !_self.showUpdateAccused;
        if (_self.showUpdateAccused) {
            _self.accused = '';
        }
    }

    showHideUpdateActions(): void {
        let _self = this;
        _self.showUpdateActions = !_self.showUpdateActions;
    }

    showHideUpdateComments(): void {
        let _self = this;
        _self.showUpdateComments = !_self.showUpdateComments;
    }

    compareStatusFn(s1: Status, s2: Status): boolean {
        return s1 && s2 ? s1.id === s2.id : s1 === s2;
    }

    comparePriorityFn(p1: Priority, p2: Priority): boolean {
        return p1 && p2 ? p1.id === p2.id : p1 === p2;
    }

    updateDetailFolder(f:  NgForm, folder: Folder) {
        let _self = this;
        let userStored = _self.userInfoService.getUserInfo();
        if (userStored != null) {
            f.value['updater'] = { userId :  userStored.userId };
        }
        if (typeof f.value['court'] === 'string') {
            let c = new Court(f.value['court']);
            f.value['court'] = c;
        }

        folder.updater = f.value.updater;
        folder.court = f.value.court;
        folder.status = f.value.status;
        folder.priority = f.value.priority;
        folder.administrationConcerned = f.value.administrationConcerned;

        _self.folderService.updateFolder(folder)
            .subscribe(function(fd) {
                _self.folder = fd;
                _self.msgs.push({severity: 'success', summary: ' تم تحديث المجلد : ' + fd.number, detail: ''});
                _self.showUpdateDetail = false;
            });
    }

    addVictim(fd: Folder, victimName: string) {
        let _self = this;
        _self.folderService.addVictim(fd, new Victim(victimName)).subscribe(function(folderDb) {
            _self.folder = folderDb;
            _self.msgs.push({severity: 'success', summary: ' تم تحديث المجلد : ' + fd.number, detail: ''});
            _self.victim = '' ;
        });
    }

    removeVictim(fd: Folder, victim: Victim) {
        let _self = this;
        _self.folderService.removeVictim(fd, victim).subscribe(function(folderDb) {
            _self.folder = folderDb;
            _self.msgs.push({severity: 'success', summary: ' تم تحديث المجلد : ' + fd.number, detail: ''});
            _self.showUpdateVictims = false;
        });
    }

    addAccused(fd: Folder, accusedName: string) {
        let _self = this;
        _self.folderService.addAccused(fd, new Accused(accusedName)).subscribe(function(folderDb) {
            _self.folder = folderDb;
            _self.msgs.push({severity: 'success', summary: ' تم تحديث المجلد : ' + fd.number, detail: ''});
            _self.accused = '' ;
        });
    }

    removeAccused(fd: Folder, accused: Accused) {
        let _self = this;
        _self.folderService.removeAccused(fd, accused).subscribe(function (folderDb) {
            _self.folder = folderDb;
            _self.msgs.push({severity: 'success', summary: ' تم تحديث المجلد : ' + fd.number, detail: ''});
            _self.showHideUpdateAccused();
        });
    }

    addAction(fd: Folder, action: Action) {
        let _self = this;
        _self.folderService.addAction(fd, action).subscribe(function(folderDb) {
            _self.folder = folderDb;
            _self.msgs.push({severity: 'success', summary: ' تم تحديث المجلد : ' + fd.number, detail: ''});
            _self.showUpdateActions = false;
        });
    }

    removeAction(fd: Folder,  action: Action) {
        let _self = this;
        _self.folderService.removeAction(fd, action).subscribe(function(folderDb) {
            _self.folder = folderDb;
            _self.msgs.push({severity: 'success', summary: ' تم تحديث المجلد : ' + fd.number, detail: ''});
            _self.showUpdateActions = false;
        });
    }

    assignUserToMe(fd: Folder) {
        let _self = this;
        let userStored = _self.userInfoService.getUserInfo();
        if (userStored != null) {
            _self.assignUser(fd, userStored);
        }
    }

    assignUser(fd: Folder, user: User) {
      let _self = this;
      _self.folderService.assignUserToFolder(fd, user).subscribe(function(folderDb) {
          _self.folder = folderDb;
          _self.msgs.push({severity: 'success', summary: ' تم تحديث المجلد : ' + fd.number, detail: ''});
          _self.showAssignUserOPanel = false;
      });
    }

    closeDetailUpdating() {
        let _self = this;
        _self.showUpdateDetail = false;
    }

    closeVictimsUpdating() {
        let _self = this;
        _self.showUpdateVictims = false;
        _self.victim = '';
    }

    closeAccusedUpdating() {
        let _self = this;
        _self.showUpdateAccused = false;
        _self.accused = '';
    }

    closeActionsUpdating() {
        let _self = this;
        _self.showUpdateActions = false;
    }

    filterCourts(event) {
        let _self = this;
        let query = event.query;
        _self.courtService.getCourts(query).subscribe(function(courts) {
            _self.filteredCourts = _self.commonService.filterItem(query, courts);
            _self.queryCourt = query;
        });
    }

    displayBreakLine(value: string): string {
        if (value) {
            return value.replace(/(?:\\[rn]|[\r\n]+|↵)+/g, '<br\>');
        }
        return value;
    }

    removeBreakLine(value: string): string {
        if (value) {
            return value.replace('<br/>', '\n');
        }
        return value;
    }

    showAssignUserDropdown():  void {
        let _self = this;
        _self.selectedUser = null;
        _self.showAssignUserOPanel = true;
        if (_self.assignUserDropdown) {
            _self.assignUserDropdown.value = null;
        }
    }
}
