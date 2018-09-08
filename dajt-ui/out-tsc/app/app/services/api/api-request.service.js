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
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, of } from 'rxjs';
import { UserInfoService } from '../user-info.service';
import { AppConfig } from '../../app-config';
import { SharedService } from '../shared.service';
import { catchError } from 'rxjs/operators';
var ApiRequestService = /** @class */ (function () {
    function ApiRequestService(appConfig, http, router, userInfoService, sharedService) {
        this.appConfig = appConfig;
        this.http = http;
        this.router = router;
        this.userInfoService = userInfoService;
        this.sharedService = sharedService;
    }
    /**
     * This is a Global place to add all the request headers for every REST calls
     */
    ApiRequestService.prototype.getHeaders = function () {
        var headers = new HttpHeaders();
        var token = this.userInfoService.getStoredToken();
        headers = headers.append('Content-Type', 'application/json');
        if (token !== null) {
            headers = headers.append('Authorization', token);
        }
        return headers;
    };
    ApiRequestService.prototype.get = function (url, urlParams) {
        var me = this;
        return this.http.get(this.appConfig.baseApiPath + url, { headers: this.getHeaders(), params: urlParams }).pipe(catchError(function (error) {
            console.log('Some error in catch');
            if ((error.status === 401 || error.status === 403) && me.router.url !== '/login') {
                me.router.navigate(['/logout']);
            }
            me.sharedService.emit(error);
            return Observable.throw(error || 'Server error');
        }));
    };
    ApiRequestService.prototype.post = function (url, body) {
        var me = this;
        return this.http.post(this.appConfig.baseApiPath + url, JSON.stringify(body), { headers: this.getHeaders() }).pipe(catchError(function (error) {
            if ((error.status === 401 || error.status === 403) && me.router.url !== '/login') {
                me.router.navigate(['/logout']);
            }
            else if ((error.status === 401 || error.status === 403) && me.router.url === '/login') {
                return of(error || 'Server error');
            }
            me.sharedService.emit(error);
            return Observable.throw(error || 'Server error');
        }));
    };
    ApiRequestService.prototype.put = function (url, body) {
        var me = this;
        return this.http.put(this.appConfig.baseApiPath + url, JSON.stringify(body), { headers: this.getHeaders() }).pipe(catchError(function (error) {
            if ((error.status === 401 || error.status === 403) && me.router.url !== '/login') {
                me.router.navigate(['/logout']);
            }
            me.sharedService.emit(error);
            return Observable.throw(error || 'Server error');
        }));
    };
    ApiRequestService.prototype.delete = function (url) {
        var me = this;
        return me.http.delete(this.appConfig.baseApiPath + url, { headers: this.getHeaders() }).pipe(catchError(function (error) {
            if ((error.status === 401 || error.status === 403) && me.router.url !== '/login') {
                me.router.navigate(['/logout']);
            }
            me.sharedService.emit(error);
            return Observable.throw(error || 'Server error');
        }));
    };
    ApiRequestService = __decorate([
        Injectable(),
        __metadata("design:paramtypes", [AppConfig,
            HttpClient,
            Router,
            UserInfoService,
            SharedService])
    ], ApiRequestService);
    return ApiRequestService;
}());
export { ApiRequestService };
//# sourceMappingURL=api-request.service.js.map