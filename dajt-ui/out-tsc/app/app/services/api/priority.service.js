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
var PriorityService = /** @class */ (function () {
    function PriorityService(apiRequest, translate) {
        this.apiRequest = apiRequest;
        this.translate = translate;
    }
    /**
     * Gets List of priorities
     */
    PriorityService.prototype.getPriorities = function () {
        // Create Request URL params
        var params = new HttpParams();
        var prioritiesListSubject = new Subject(); // Will use this subject to emit data that we want
        this.apiRequest.get('api/priorities', params)
            .subscribe(function (jsonResp) {
            prioritiesListSubject.next(jsonResp.items);
        });
        return prioritiesListSubject;
    };
    PriorityService = __decorate([
        Injectable(),
        __metadata("design:paramtypes", [ApiRequestService, TranslateService])
    ], PriorityService);
    return PriorityService;
}());
export { PriorityService };
//# sourceMappingURL=priority.service.js.map