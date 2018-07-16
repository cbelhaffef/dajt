import {Component, OnInit, ViewChild} from '@angular/core';
import 'rxjs/add/operator/switchMap';
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

@Component( {
    selector   :  's-folders-pg',
    templateUrl:  './folder_details.component.html',
    styleUrls  :  [ './folder_details.scss'],
})

export class FolderDetailsComponent implements OnInit {

    @ViewChild(NgForm) updateDetailfolderForm:  NgForm;

    msgs:  Message[] = [];

    public folder:  Folder = new Folder();

    public submitted:  boolean;
    public isLoading = false;

    public showUpdateDetail = false;
    public showUpdateVictims = false;
    public showUpdateAccused = false;
    public showUpdateActions = false;
    public showUpdateComments = false;


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
                private userInfoService: UserInfoService,
                private fb:  FormBuilder) {
    }

    ngOnInit():  void {
        const me = this;
        me.isLoading = true;

        me.folderService.getFolderDetails(me.activateRoute.snapshot.params.id)
            .subscribe(function(folder) {
                me.folder = folder;
                me.isLoading = false;
            });
        me.statusService.getStatus()
            .subscribe(function(statusList) {
                me.statusList = statusList;
            });
        me.priorityService.getPriorities()
            .subscribe(function(prioritiesList) {
                me.prioritiesList = prioritiesList;
            });
        me.actionService.getActions()
            .subscribe(function(actionsList) {
                me.actionsList = actionsList;
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

    updateDetailFolder(f:  NgForm) {
        let _self = this;
        let userStored = _self.userInfoService.getUserInfo();
        if (userStored != null) {
            f.value['updater'] = { userId :  userStored.userId };
        }
        if (typeof f.value['court'] === 'string') {
            let c = new Court(f.value['court']);
            f.value['court'] = c;
        }
        _self.folderService.addFolder(f.value)
            .subscribe(function(folder) {
                alert('dossier crée');
                // _self.close();
            });
    }

    updateVictimsFolder(f: NgForm) {
        let _self = this;
        let userStored = _self.userInfoService.getUserInfo();
        if (userStored != null) {
            f.value['updater'] = { userId :  userStored.userId };
        }
        f.value['victims'] = _self.selectedVictims;
    }

    updateAccusedFolder(f: NgForm) {
        let _self = this;
        let userStored = _self.userInfoService.getUserInfo();
        if (userStored != null) {
            f.value['updater'] = { userId :  userStored.userId };
        }
        f.value['accused'] = _self.selectedAccused;
    }

    updatePeopleFolder(f: NgForm) {
        let _self = this;
        let userStored = _self.userInfoService.getUserInfo();
        if (userStored != null) {
            f.value['updater'] = { userId :  userStored.userId };
        }
        f.value['created'] = _self.selectedAccused;
    }

    addVictim(victim: string) {
        let _self = this;
    }

    removeVictim(AccusedId: number) {
        let _self = this;
    }

    addAccused(accused: string) {
        let _self = this;
    }

    removeAccused(accusedId: number) {
        let _self = this;
    }

    closeDetailUpdating() {
        let _self = this;
        _self.showUpdateDetail = false;
    }

    closeVictimsUpdating() {
        let _self = this;
        _self.showUpdateVictims = false;
    }

    closeAccusedUpdating() {
        let _self = this;
        _self.showUpdateAccused = false;
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
}
