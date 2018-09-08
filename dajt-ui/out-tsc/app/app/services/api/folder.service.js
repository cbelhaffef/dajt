var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { ApiRequestService } from './api-request.service';
import { TranslateService } from './translate.service';
import { HttpParams } from '@angular/common/http';
var FolderService = /** @class */ (function () {
    function FolderService(apiRequest, translate) {
        this.apiRequest = apiRequest;
        this.translate = translate;
    }
    /**
     * Gets List of folders
     */
    FolderService.prototype.getFolders = function (folderNumber, office, status, victim, accused, page, size) {
        // Create Request URL params
        var me = this;
        var params = new HttpParams();
        params = params.append('page', typeof page === 'number' ? page.toString() : '0');
        params = params.append('size', typeof size === 'number' ? size.toString() : '10');
        if (folderNumber && typeof folderNumber === 'string') {
            params = params.append('folderNumber', folderNumber);
        }
        if (office && typeof office === 'string') {
            params = params.append('office', office);
        }
        if (status && typeof status === 'string') {
            params = params.append('status', status);
        }
        if (victim && typeof victim === 'string') {
            params = params.append('victim', victim);
        }
        if (accused && typeof accused === 'string') {
            params = params.append('accused', accused);
        }
        var folderListSubject = new Subject(); // Will use this subject to emit data that we want
        this.apiRequest.get('api/folders', params)
            .subscribe(function (jsonResp) {
            folderListSubject.next(jsonResp); // incidentList is a Subject and emits an event thats being listened to by the components
        });
        return folderListSubject;
    };
    FolderService.prototype.getFolderDetails = function (id) {
        var me = this;
        var folderSubject = new Subject();
        me.apiRequest.get('api/folders/' + id)
            .subscribe(function (jsonResp) {
            folderSubject.next(jsonResp);
        });
        return folderSubject;
    };
    FolderService.prototype.addFolder = function (folder) {
        var me = this;
        var folderSubject = new Subject();
        this.apiRequest.post('api/folders', folder)
            .subscribe(function (jsonResp) {
            folderSubject.next(jsonResp);
        });
        return folderSubject;
    };
    FolderService.prototype.updateFolder = function (folder) {
        var me = this;
        var folderSubject = new Subject();
        this.apiRequest.put('api/folders', folder)
            .subscribe(function (jsonResp) {
            folderSubject.next(jsonResp);
        });
        return folderSubject;
    };
    FolderService.prototype.assignUser = function (folders, user) {
        var me = this;
        var folderSubject = new Subject();
        this.apiRequest.post('api/folders/assign/' + user.userId, folders)
            .subscribe(function (jsonResp) {
            folderSubject.next(jsonResp);
        });
        return folderSubject;
    };
    FolderService.prototype.assignUserToFolder = function (folder, user) {
        var me = this;
        var folderSubject = new Subject();
        this.apiRequest.put('api/folders/' + folder.id + '/assign', user)
            .subscribe(function (jsonResp) {
            folderSubject.next(jsonResp);
        });
        return folderSubject;
    };
    FolderService.prototype.addActionToListOfFolders = function (folders, action) {
        var me = this;
        var folderSubject = new Subject();
        this.apiRequest.post('api/folders/addAction/' + action.code, folders)
            .subscribe(function (jsonResp) {
            folderSubject.next(jsonResp);
        });
        return folderSubject;
    };
    FolderService.prototype.changeStatusToListOfFolders = function (folders, status) {
        var _self = this;
        var folderSubject = new Subject();
        _self.apiRequest.post('api/folders/changeStatus/' + status.code, folders)
            .subscribe(function (jsonResp) {
            folderSubject.next(jsonResp);
        });
        return folderSubject;
    };
    FolderService.prototype.addVictim = function (folder, victim) {
        var _self = this;
        var folderSubject = new Subject();
        _self.apiRequest.put('api/folders/' + folder.id + '/addVictim', victim)
            .subscribe(function (jsonResp) {
            folderSubject.next(jsonResp);
        });
        return folderSubject;
    };
    FolderService.prototype.removeVictim = function (folder, victim) {
        var _self = this;
        var folderSubject = new Subject();
        _self.apiRequest.delete('api/folders/' + folder.id + '/removeVictim/' + victim.id)
            .subscribe(function (jsonResp) {
            folderSubject.next(jsonResp);
        });
        return folderSubject;
    };
    FolderService.prototype.addAccused = function (folder, accused) {
        var _self = this;
        var folderSubject = new Subject();
        _self.apiRequest.put('api/folders/' + folder.id + '/addAccused', accused)
            .subscribe(function (jsonResp) {
            folderSubject.next(jsonResp);
        });
        return folderSubject;
    };
    FolderService.prototype.removeAccused = function (folder, accused) {
        var _self = this;
        var folderSubject = new Subject();
        _self.apiRequest.delete('api/folders/' + folder.id + '/removeAccused/' + accused.id)
            .subscribe(function (jsonResp) {
            folderSubject.next(jsonResp);
        });
        return folderSubject;
    };
    FolderService.prototype.addAction = function (folder, action) {
        var _self = this;
        var folderSubject = new Subject();
        _self.apiRequest.put('api/folders/' + folder.id + '/addAction', action)
            .subscribe(function (jsonResp) {
            folderSubject.next(jsonResp);
        });
        return folderSubject;
    };
    FolderService.prototype.removeAction = function (folder, action) {
        var _self = this;
        var folderSubject = new Subject();
        _self.apiRequest.delete('api/folders/' + folder.id + '/removeAction/' + action.id)
            .subscribe(function (jsonResp) {
            folderSubject.next(jsonResp);
        });
        return folderSubject;
    };
    FolderService = __decorate([
        Injectable(),
        __metadata("design:paramtypes", [ApiRequestService, TranslateService])
    ], FolderService);
    return FolderService;
}());
export { FolderService };
//# sourceMappingURL=folder.service.js.map