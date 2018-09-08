var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var __param = (this && this.__param) || function (paramIndex, decorator) {
    return function (target, key) { decorator(target, key, paramIndex); }
};
import { Component, Inject, ViewChild } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material';
import { FormControl, NgForm, Validators } from '@angular/forms';
import { OfficeService } from '../../services/api/office.service';
import { FolderService } from '../../services/api/folder.service';
import { CourtService } from '../../services/api/court.service';
import { UserService } from '../../services/api/user.service';
import { UserInfoService } from '../../services/user-info.service';
import { Victim } from '../../models/victim.model';
import { Accused } from '../../models/accused.model';
import { Court } from '../../models/court.model';
import { PriorityService } from '../../services/api/priority.service';
import { StatusService } from '../../services/api/status.service';
import { SharedService } from '../../services/shared.service';
var FoldersCreateDialogComponent = /** @class */ (function () {
    function FoldersCreateDialogComponent(officeService, folderService, courtService, statusService, priorityService, userService, userInfoService, sharedService, dialogRef, data) {
        this.officeService = officeService;
        this.folderService = folderService;
        this.courtService = courtService;
        this.statusService = statusService;
        this.priorityService = priorityService;
        this.userService = userService;
        this.userInfoService = userInfoService;
        this.sharedService = sharedService;
        this.dialogRef = dialogRef;
        this.data = data;
        this.listOffices = [];
        this.listUsers = [];
        this.listStatus = [];
        this.listPriorities = [];
        this.filteredOffices = [];
        this.selectedVictims = [];
        this.selectedAccused = [];
        this.filteredCourts = [];
        this.filteredUsers = [];
        this.officeControl = new FormControl('', [Validators.required]);
    }
    FoldersCreateDialogComponent.prototype.ngOnInit = function () {
        var _self = this;
        _self.officeService.getOffices()
            .subscribe(function (offices) {
            _self.listOffices = offices;
        });
        _self.statusService.getStatus()
            .subscribe(function (listStatus) {
            _self.listStatus = listStatus;
        });
        _self.priorityService.getPriorities()
            .subscribe(function (listPriorities) {
            _self.listPriorities = listPriorities;
        });
        _self.userService.getUsers()
            .subscribe(function (users) {
            _self.listUsers = users;
        });
    };
    FoldersCreateDialogComponent.prototype.close = function () {
        this.dialogRef.close();
    };
    FoldersCreateDialogComponent.prototype.filterOffices = function (event) {
        var _self = this;
        var query = event.query;
        _self.officeService.getOffices(query).subscribe(function (offices) {
            _self.filteredOffices = offices;
            _self.queryOffice = query;
        });
    };
    FoldersCreateDialogComponent.prototype.filterCourts = function (event) {
        var _self = this;
        var query = event.query;
        _self.courtService.getCourts(query).subscribe(function (courts) {
            _self.filteredCourts = _self.filterItem(query, courts);
            _self.queryCourt = query;
        });
    };
    FoldersCreateDialogComponent.prototype.filterUsers = function (event) {
        var _self = this;
        var query = event.query;
        var queryArr = query.split(' ');
        var firstname = queryArr[0];
        var lastname = queryArr.length > 1 ? queryArr[1] : null;
        _self.userService.getUsers(firstname, lastname).subscribe(function (users) {
            _self.filteredUsers = [];
            for (var _i = 0, users_1 = users; _i < users_1.length; _i++) {
                var u = users_1[_i];
                _self.filteredUsers.push({ name: u.firstname + ' ' + u.lastname, id: u.userId });
            }
            _self.queryUser = query;
        });
    };
    FoldersCreateDialogComponent.prototype.filterItem = function (query, items) {
        var filtered = [];
        for (var i = 0; i < items.length; i++) {
            var accused = items[i];
            if (accused.name.toLowerCase().indexOf(query.toLowerCase()) === 0) {
                filtered.push(accused);
            }
        }
        return filtered;
    };
    FoldersCreateDialogComponent.prototype.addItem = function (event, item, selectedItems, filterdItems) {
        if (event.code === 'Enter' && filterdItems.length === 0) {
            selectedItems.push({ name: item });
        }
    };
    FoldersCreateDialogComponent.prototype.addToVictims = function (event) {
        var _self = this;
        if (_self.selectedVictims.find(function (c) { return c.name === event.value; }) === undefined) {
            _self.selectedVictims.push(new Victim(event.value));
        }
    };
    FoldersCreateDialogComponent.prototype.removeFromVictims = function (event) {
        var _self = this;
        var index = _self.selectedVictims.findIndex(function (c) { return c.name === event.value; });
        if (index !== -1) {
            _self.selectedVictims.splice(index, 1);
        }
    };
    FoldersCreateDialogComponent.prototype.addToAccused = function (event) {
        var _self = this;
        if (_self.selectedAccused.find(function (c) { return c.name === event.value; }) === undefined) {
            _self.selectedAccused.push(new Accused(event.value));
        }
    };
    FoldersCreateDialogComponent.prototype.removeFromAccused = function (event) {
        var _self = this;
        var index = _self.selectedAccused.findIndex(function (c) { return c.name === event.value; });
        if (index !== -1) {
            _self.selectedAccused.splice(index, 1);
        }
    };
    FoldersCreateDialogComponent.prototype.createFolder = function (f) {
        var _self = this;
        var userStored = _self.userInfoService.getUserInfo();
        if (userStored != null) {
            f.value['reporter'] = { userId: userStored.userId };
            f.value['updater'] = { userId: userStored.userId };
        }
        f.value['victims'] = _self.selectedVictims;
        f.value['accused'] = _self.selectedAccused;
        if (typeof f.value['court'] === 'string') {
            var c = new Court(f.value['court']);
            f.value['court'] = c;
        }
        _self.folderService.addFolder(f.value)
            .subscribe(function (folder) {
            _self.sharedService.emit(folder);
            _self.close();
        });
    };
    __decorate([
        ViewChild(NgForm),
        __metadata("design:type", NgForm)
    ], FoldersCreateDialogComponent.prototype, "createFolderForm", void 0);
    FoldersCreateDialogComponent = __decorate([
        Component({
            selector: 'folders-create-dialog-component',
            templateUrl: 'folders.create.dialog.component.html',
            styleUrls: ['./folders.create.dialog.component.css']
        }),
        __param(9, Inject(MAT_DIALOG_DATA)),
        __metadata("design:paramtypes", [OfficeService,
            FolderService,
            CourtService,
            StatusService,
            PriorityService,
            UserService,
            UserInfoService,
            SharedService,
            MatDialogRef, Object])
    ], FoldersCreateDialogComponent);
    return FoldersCreateDialogComponent;
}());
export { FoldersCreateDialogComponent };
//# sourceMappingURL=folders.create.dialog.component.js.map