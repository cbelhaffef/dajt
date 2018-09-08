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
var OfficeService = /** @class */ (function () {
    function OfficeService(apiRequest, translate) {
        this.apiRequest = apiRequest;
        this.translate = translate;
    }
    /**
     * Gets List of guilties
     */
    OfficeService.prototype.getOffices = function (page, size) {
        // Create Request URL params
        var params = new HttpParams();
        params = params.append("page", typeof page === "number" ? page.toString() : "0");
        params = params.append("size", typeof size === "number" ? size.toString() : "1000");
        var officeListSubject = new Subject(); // Will use this subject to emit data that we want
        this.apiRequest.get('api/offices', params)
            .subscribe(function (jsonResp) {
            officeListSubject.next(jsonResp.items);
        });
        return officeListSubject;
    };
    OfficeService = __decorate([
        Injectable(),
        __metadata("design:paramtypes", [ApiRequestService, TranslateService])
    ], OfficeService);
    return OfficeService;
}());
export { OfficeService };
//# sourceMappingURL=office.service.js.map