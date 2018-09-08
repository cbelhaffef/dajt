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
import { Router } from '@angular/router';
import { FolderService } from '../../services/api/folder.service';
import { VictimService } from '../../services/api/victim.service';
import { NgForm } from '@angular/forms';
import { AccusedService } from '../../services/api/accused.service';
import { CourtService } from '../../services/api/court.service';
import { OfficeService } from '../../services/api/office.service';
import { MatDialog } from '@angular/material';
import { FoldersCreateDialogComponent } from './folders.create.dialog.component';
import { UserService } from '../../services/api/user.service';
import { SpinnerService } from '../../services/spinner.service';
import { Dropdown, OverlayPanel } from 'primeng/primeng';
import { ActionService } from '../../services/api/action.service';
import { FolderResponse } from '../../models/folder.response.model';
import { StatusService } from '../../services/api/status.service';
import { MessageService } from 'primeng/components/common/messageservice';
import { SharedService } from '../../services/shared.service';
var FoldersComponent = /** @class */ (function () {
    function FoldersComponent(router, messageService, spinnerService, folderService, victimService, guiltyService, courtService, officeService, statusService, userService, actionService, sharedService, dialog) {
        this.router = router;
        this.messageService = messageService;
        this.spinnerService = spinnerService;
        this.folderService = folderService;
        this.victimService = victimService;
        this.guiltyService = guiltyService;
        this.courtService = courtService;
        this.officeService = officeService;
        this.statusService = statusService;
        this.userService = userService;
        this.actionService = actionService;
        this.sharedService = sharedService;
        this.dialog = dialog;
        this.listFolders = new FolderResponse();
        this.selectedFolders = [];
        this.listCourts = [];
        this.listOffices = [];
        this.msgs = [];
        this.listUsers = [];
        this.showAssignUserOPanel = false;
        this.listActions = [];
        this.showAddActionOPanel = false;
        this.statusList = [];
        this.showChangeStatusOPanel = false;
    }
    FoldersComponent.prototype.ngOnInit = function () {
        var _self = this;
        _self.sharedService.toggle$.subscribe(function (item) {
            _self.msgs.push({ severity: 'success', summary: ' تم انشاء الملف : ' + item['number'], detail: '' });
        });
        _self.getFolders();
        _self.statusService.getStatus()
            .subscribe(function (statusList) {
            _self.statusList = statusList;
        });
        _self.courtService.getCourts()
            .subscribe(function (courts) {
            _self.listCourts = courts;
        });
        _self.officeService.getOffices()
            .subscribe(function (offices) {
            _self.listOffices = offices;
        });
        _self.userService.getUsers()
            .subscribe(function (users) {
            for (var _i = 0, users_1 = users; _i < users_1.length; _i++) {
                var u = users_1[_i];
                _self.listUsers.push({ label: u.firstname + ' ' + u.lastname, value: u });
            }
        });
        _self.actionService.getActions()
            .subscribe(function (actions) {
            for (var _i = 0, actions_1 = actions; _i < actions_1.length; _i++) {
                var a = actions_1[_i];
                _self.listActions.push({ label: a.name, value: a });
            }
        });
    };
    FoldersComponent.prototype.loadFoldersLazy = function (event) {
        var _self = this;
        _self.getFolders(_self.filterFoldersForm, event.first);
    };
    FoldersComponent.prototype.openCreateFolderDialog = function () {
        var _self = this;
        var dialogRef = _self.dialog.open(FoldersCreateDialogComponent, {
            width: '60%',
            direction: 'rtl',
            data: { name: _self.name, animal: _self.animal }
        });
        dialogRef.afterClosed().subscribe(function (result) {
            console.log('The dialog was closed');
            if (result !== undefined && result != null) {
                _self.folderCreated = result;
                _self.showMessage('success', '', '');
            }
        });
    };
    FoldersComponent.prototype.onSubmitFilterFoldersForm = function (f) {
        var _self = this;
        _self.getFolders(f);
    };
    FoldersComponent.prototype.getFolders = function (f, p) {
        var _self = this;
        _self.spinnerService.showSpinner();
        _self.loading = true;
        var folderNumber, office, status, victim, accused, page;
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
            .subscribe(function (folderData) {
            _self.listFolders = folderData;
            _self.selectedFolders = [];
            _self.spinnerService.hideSpinner();
            _self.loading = false;
        });
    };
    FoldersComponent.prototype.assignUser = function (folders, user) {
        var _self = this;
        _self.spinnerService.showSpinner();
        _self.folderService.assignUser(folders.map(function (f) { return f.id; }), user).subscribe(function (jsonResp) {
            _self.overlayPanel.hide();
            _self.getFolders(_self.filterFoldersForm);
        });
    };
    FoldersComponent.prototype.addAction = function (folders, action) {
        var _self = this;
        _self.spinnerService.showSpinner();
        _self.folderService.addActionToListOfFolders(folders.map(function (f) { return f.id; }), action).subscribe(function (jsonResp) {
            _self.overlayPanel.hide();
            _self.getFolders(_self.filterFoldersForm);
        });
    };
    FoldersComponent.prototype.changeStatus = function (folders, status) {
        var _self = this;
        _self.spinnerService.showSpinner();
        _self.folderService.changeStatusToListOfFolders(folders.map(function (f) { return f.id; }), status).subscribe(function (jsonResp) {
            _self.overlayPanel.hide();
            _self.getFolders(_self.filterFoldersForm);
        });
    };
    FoldersComponent.prototype.showAssignUserDropdown = function () {
        var _self = this;
        _self.selectedUser = null;
        _self.showAssignUserOPanel = true;
        if (_self.assignUserDropdown) {
            _self.assignUserDropdown.value = null;
        }
        _self.showAddActionOPanel = false;
        _self.showChangeStatusOPanel = false;
    };
    FoldersComponent.prototype.showAddActionDropdown = function () {
        var _self = this;
        _self.selectedAction = null;
        _self.showAddActionOPanel = true;
        if (_self.addActionDropdown) {
            _self.addActionDropdown.value = null;
        }
        _self.showAssignUserOPanel = false;
        _self.showChangeStatusOPanel = false;
    };
    FoldersComponent.prototype.showChangeFolderStatusDropdown = function () {
        var _self = this;
        _self.selectedStatus = null;
        _self.showChangeStatusOPanel = true;
        if (_self.changeStatusDropdown) {
            _self.changeStatusDropdown.value = null;
        }
        _self.showAssignUserOPanel = false;
        _self.showAddActionOPanel = false;
    };
    FoldersComponent.prototype.getToolTipActionValue = function (listAction) {
        var actionValue = '';
        listAction.forEach(function (a, i) {
            actionValue += a.name;
            if (i < listAction.length) {
                actionValue += ' \n';
            }
        });
        return actionValue;
    };
    FoldersComponent.prototype.showMessage = function (type, summary, detail) {
        this.msgs = [];
        this.msgs.push({ severity: type, summary: summary, detail: detail });
    };
    __decorate([
        ViewChild(NgForm),
        __metadata("design:type", NgForm)
    ], FoldersComponent.prototype, "filterFoldersForm", void 0);
    __decorate([
        ViewChild(OverlayPanel),
        __metadata("design:type", OverlayPanel)
    ], FoldersComponent.prototype, "overlayPanel", void 0);
    __decorate([
        ViewChild('assignUserDropdown'),
        __metadata("design:type", Dropdown)
    ], FoldersComponent.prototype, "assignUserDropdown", void 0);
    __decorate([
        ViewChild('addActionDropdown'),
        __metadata("design:type", Dropdown)
    ], FoldersComponent.prototype, "addActionDropdown", void 0);
    __decorate([
        ViewChild('changeStatusDropdown'),
        __metadata("design:type", Dropdown)
    ], FoldersComponent.prototype, "changeStatusDropdown", void 0);
    __decorate([
        ViewChild(NgForm),
        __metadata("design:type", NgForm)
    ], FoldersComponent.prototype, "createFolderForm", void 0);
    FoldersComponent = __decorate([
        Component({
            selector: 'app-folders-pg',
            templateUrl: './folders.component.html',
            styleUrls: ['./folders.scss'],
        }),
        __metadata("design:paramtypes", [Router,
            MessageService,
            SpinnerService,
            FolderService,
            VictimService,
            AccusedService,
            CourtService,
            OfficeService,
            StatusService,
            UserService,
            ActionService,
            SharedService,
            MatDialog])
    ], FoldersComponent);
    return FoldersComponent;
}());
export { FoldersComponent };
//# sourceMappingURL=folders.component.js.map