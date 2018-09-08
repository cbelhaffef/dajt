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
import { ApiRequestService } from './api-request.service';
import { TranslateService } from './translate.service';
import { HttpParams } from '@angular/common/http';
import { Subject } from 'rxjs';
var StatusService = /** @class */ (function () {
    function StatusService(apiRequest, translate) {
        this.apiRequest = apiRequest;
        this.translate = translate;
    }
    /**
     * Gets List of status
     */
    StatusService.prototype.getStatus = function () {
        // Create Request URL params
        var params = new HttpParams();
        var statusListSubject = new Subject(); // Will use this subject to emit data that we want
        this.apiRequest.get('api/status', params)
            .subscribe(function (jsonResp) {
            statusListSubject.next(jsonResp.items);
        });
        return statusListSubject;
    };
    StatusService = __decorate([
        Injectable(),
        __metadata("design:paramtypes", [ApiRequestService, TranslateService])
    ], StatusService);
    return StatusService;
}());
export { StatusService };
//# sourceMappingURL=status.service.js.map