var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
import { Component, ViewChild } from '@angular/core';
import 'rxjs/add/operator/switchMap';
import { ActivatedRoute } from '@angular/router';
import { FolderService } from '../../services/api/folder.service';
import { Folder } from '../../models/folder.model';
import { FormBuilder, NgForm } from '@angular/forms';
import { CourtService } from '../../services/api/court.service';
import { VictimService } from '../../services/api/victim.service';
import { AccusedService } from '../../services/api/accused.service';
import { ActionService } from '../../services/api/action.service';
import { Court } from '../../models/court.model';
import { UserInfoService } from '../../services/user-info.service';
import { StatusService } from '../../services/api/status.service';
import { PriorityService } from '../../services/api/priority.service';
import { CommonService } from '../../services/common.service';
import { Victim } from '../../models/victim.model';
import { Accused } from '../../models/accused.model';
import { MessageService } from 'primeng/components/common/messageservice';
import { SharedService } from '../../services/shared.service';
import { Dropdown, OverlayPanel } from 'primeng/primeng';
import { UserService } from '../../services/api/user.service';
var FolderDetailsComponent = /** @class */ (function () {
    function FolderDetailsComponent(activateRoute, folderService, victimService, guiltyService, actionService, statusService, priorityService, courtService, commonService, userService, userInfoService, messageService, sharedService, fb) {
        this.activateRoute = activateRoute;
        this.folderService = folderService;
        this.victimService = victimService;
        this.guiltyService = guiltyService;
        this.actionService = actionService;
        this.statusService = statusService;
        this.priorityService = priorityService;
        this.courtService = courtService;
        this.commonService = commonService;
        this.userService = userService;
        this.userInfoService = userInfoService;
        this.messageService = messageService;
        this.sharedService = sharedService;
        this.fb = fb;
        this.msgs = [];
        this.folder = new Folder();
        this.isLoading = false;
        this.showUpdateDetail = false;
        this.showUpdateVictims = false;
        this.showUpdateAccused = false;
        this.showUpdateActions = false;
        this.showUpdateComments = false;
        this.listUsers = [];
        this.showAssignUserOPanel = false;
        this.statusList = [];
        this.prioritiesList = [];
        this.actionsList = [];
        this.selectedVictims = [];
        this.selectedAccused = [];
        this.filteredCourts = [];
    }
    FolderDetailsComponent.prototype.ngOnInit = function () {
        var _self = this;
        _self.isLoading = true;
        _self.sharedService.toggle$.subscribe(function (item) {
            _self.msgs.push({ severity: 'error', summary: ' خطأ داخلي في الخادم ', detail: item['message'] });
        });
        _self.userService.getUsers()
            .subscribe(function (users) {
            for (var _i = 0, users_1 = users; _i < users_1.length; _i++) {
                var u = users_1[_i];
                _self.listUsers.push({ label: u.firstname + ' ' + u.lastname, value: u });
            }
        });
        _self.folderService.getFolderDetails(_self.activateRoute.snapshot.params.id)
            .subscribe(function (folder) {
            _self.folder = folder;
            _self.isLoading = false;
        });
        _self.statusService.getStatus()
            .subscribe(function (statusList) {
            _self.statusList = statusList;
        });
        _self.priorityService.getPriorities()
            .subscribe(function (prioritiesList) {
            _self.prioritiesList = prioritiesList;
        });
        _self.actionService.getActions()
            .subscribe(function (actionsList) {
            _self.actionsList = actionsList;
        });
    };
    FolderDetailsComponent.prototype.showHideUpdateDetail = function () {
        var _self = this;
        _self.showUpdateDetail = !_self.showUpdateDetail;
        if (_self.showUpdateDetail) {
            _self.updateDetailfolderForm.setValue({
                status: _self.folder.status,
                priority: _self.folder.priority,
                court: _self.folder.court,
                administrationConcerned: _self.folder.administrationConcerned
            });
        }
        else {
            _self.updateDetailfolderForm.resetForm();
        }
    };
    FolderDetailsComponent.prototype.showHideUpdateVictims = function () {
        var _self = this;
        _self.showUpdateVictims = !_self.showUpdateVictims;
        if (_self.showUpdateVictims) {
            _self.victim = '';
        }
    };
    FolderDetailsComponent.prototype.showHideUpdateAccused = function () {
        var _self = this;
        _self.showUpdateAccused = !_self.showUpdateAccused;
        if (_self.showUpdateAccused) {
            _self.accused = '';
        }
    };
    FolderDetailsComponent.prototype.showHideUpdateActions = function () {
        var _self = this;
        _self.showUpdateActions = !_self.showUpdateActions;
    };
    FolderDetailsComponent.prototype.showHideUpdateComments = function () {
        var _self = this;
        _self.showUpdateComments = !_self.showUpdateComments;
    };
    FolderDetailsComponent.prototype.compareStatusFn = function (s1, s2) {
        return s1 && s2 ? s1.id === s2.id : s1 === s2;
    };
    FolderDetailsComponent.prototype.comparePriorityFn = function (p1, p2) {
        return p1 && p2 ? p1.id === p2.id : p1 === p2;
    };
    FolderDetailsComponent.prototype.updateDetailFolder = function (f, folder) {
        var _self = this;
        var userStored = _self.userInfoService.getUserInfo();
        if (userStored != null) {
            f.value['updater'] = { userId: userStored.userId };
        }
        if (typeof f.value['court'] === 'string') {
            var c = new Court(f.value['court']);
            f.value['court'] = c;
        }
        folder.updater = f.value.updater;
        folder.court = f.value.court;
        folder.status = f.value.status;
        folder.priority = f.value.priority;
        folder.administrationConcerned = f.value.administrationConcerned;
        _self.folderService.updateFolder(folder)
            .subscribe(function (fd) {
            _self.folder = fd;
            _self.msgs.push({ severity: 'success', summary: ' تم تحديث المجلد : ' + fd.number, detail: '' });
            _self.showUpdateDetail = false;
        });
    };
    FolderDetailsComponent.prototype.addVictim = function (fd, victimName) {
        var _self = this;
        _self.folderService.addVictim(fd, new Victim(victimName)).subscribe(function (folderDb) {
            _self.folder = folderDb;
            _self.msgs.push({ severity: 'success', summary: ' تم تحديث المجلد : ' + fd.number, detail: '' });
            _self.victim = '';
        });
    };
    FolderDetailsComponent.prototype.removeVictim = function (fd, victim) {
        var _self = this;
        _self.folderService.removeVictim(fd, victim).subscribe(function (folderDb) {
            _self.folder = folderDb;
            _self.msgs.push({ severity: 'success', summary: ' تم تحديث المجلد : ' + fd.number, detail: '' });
            _self.showUpdateVictims = false;
        });
    };
    FolderDetailsComponent.prototype.addAccused = function (fd, accusedName) {
        var _self = this;
        _self.folderService.addAccused(fd, new Accused(accusedName)).subscribe(function (folderDb) {
            _self.folder = folderDb;
            _self.msgs.push({ severity: 'success', summary: ' تم تحديث المجلد : ' + fd.number, detail: '' });
            _self.accused = '';
        });
    };
    FolderDetailsComponent.prototype.removeAccused = function (fd, accused) {
        var _self = this;
        _self.folderService.removeAccused(fd, accused).subscribe(function (folderDb) {
            _self.folder = folderDb;
            _self.msgs.push({ severity: 'success', summary: ' تم تحديث المجلد : ' + fd.number, detail: '' });
            _self.showHideUpdateAccused();
        });
    };
    FolderDetailsComponent.prototype.addAction = function (fd, action) {
        var _self = this;
        _self.folderService.addAction(fd, action).subscribe(function (folderDb) {
            _self.folder = folderDb;
            _self.msgs.push({ severity: 'success', summary: ' تم تحديث المجلد : ' + fd.number, detail: '' });
            _self.showUpdateActions = false;
        });
    };
    FolderDetailsComponent.prototype.removeAction = function (fd, action) {
        var _self = this;
        _self.folderService.removeAction(fd, action).subscribe(function (folderDb) {
            _self.folder = folderDb;
            _self.msgs.push({ severity: 'success', summary: ' تم تحديث المجلد : ' + fd.number, detail: '' });
            _self.showUpdateActions = false;
        });
    };
    FolderDetailsComponent.prototype.assignUserToMe = function (fd) {
        var _self = this;
        var userStored = _self.userInfoService.getUserInfo();
        if (userStored != null) {
            _self.assignUser(fd, userStored);
        }
    };
    FolderDetailsComponent.prototype.assignUser = function (fd, user) {
        var _self = this;
        _self.folderService.assignUserToFolder(fd, user).subscribe(function (folderDb) {
            _self.folder = folderDb;
            _self.msgs.push({ severity: 'success', summary: ' تم تحديث المجلد : ' + fd.number, detail: '' });
            _self.showAssignUserOPanel = false;
        });
    };
    FolderDetailsComponent.prototype.closeDetailUpdating = function () {
        var _self = this;
        _self.showUpdateDetail = false;
    };
    FolderDetailsComponent.prototype.closeVictimsUpdating = function () {
        var _self = this;
        _self.showUpdateVictims = false;
        _self.victim = '';
    };
    FolderDetailsComponent.prototype.closeAccusedUpdating = function () {
        var _self = this;
        _self.showUpdateAccused = false;
        _self.accused = '';
    };
    FolderDetailsComponent.prototype.closeActionsUpdating = function () {
        var _self = this;
        _self.showUpdateActions = false;
    };
    FolderDetailsComponent.prototype.filterCourts = function (event) {
        var _self = this;
        var query = event.query;
        _self.courtService.getCourts(query).subscribe(function (courts) {
            _self.filteredCourts = _self.commonService.filterItem(query, courts);
            _self.queryCourt = query;
        });
    };
    FolderDetailsComponent.prototype.displayBreakLine = function (value) {
        if (value) {
            return value.replace(/(?:\\[rn]|[\r\n]+|↵)+/g, '<br\>');
        }
        return value;
    };
    FolderDetailsComponent.prototype.removeBreakLine = function (value) {
        if (value) {
            return value.replace('<br/>', '\n');
        }
        return value;
    };
    FolderDetailsComponent.prototype.showAssignUserDropdown = function () {
        var _self = this;
        _self.selectedUser = null;
        _self.showAssignUserOPanel = true;
        if (_self.assignUserDropdown) {
            _self.assignUserDropdown.value = null;
        }
    };
    __decorate([
        ViewChild(NgForm),
        __metadata("design:type", NgForm)
    ], FolderDetailsComponent.prototype, "updateDetailfolderForm", void 0);
    __decorate([
        ViewChild(OverlayPanel),
        __metadata("design:type", OverlayPanel)
    ], FolderDetailsComponent.prototype, "overlayPanel", void 0);
    __decorate([
        ViewChild('assignUserDropdown'),
        __metadata("design:type", Dropdown)
    ], FolderDetailsComponent.prototype, "assignUserDropdown", void 0);
    FolderDetailsComponent = __decorate([
        Component({
            selector: 'app-folders-pg',
            templateUrl: './folder_details.component.html',
            styleUrls: ['./folder_details.scss'],
            providers: [MessageService]
        }),
        __metadata("design:paramtypes", [ActivatedRoute,
            FolderService,
            VictimService,
            AccusedService,
            ActionService,
            StatusService,
            PriorityService,
            CourtService,
            CommonService,
            UserService,
            UserInfoService,
            MessageService,
            SharedService,
            FormBuilder])
    ], FolderDetailsComponent);
    return FolderDetailsComponent;
}());
export { FolderDetailsComponent };
//# sourceMappingURL=folder_details.component.js.map