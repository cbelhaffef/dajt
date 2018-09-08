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
import { Router } from '@angular/router';
import { Subject } from 'rxjs';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import { UserInfoService } from '../user-info.service';
import { ApiRequestService } from './api-request.service';
var AuthService = /** @class */ (function () {
    function AuthService(router, userInfoService, apiRequest) {
        this.router = router;
        this.userInfoService = userInfoService;
        this.apiRequest = apiRequest;
        this.landingPage = '/home/legal_cases/folders';
    }
    AuthService.prototype.getToken = function (username, password) {
        var _this = this;
        var me = this;
        var bodyData = {
            'username': username,
            'password': password,
        };
        // Will use this subject to emit data that we want after ajax login attempt
        var loginDataSubject = new Subject();
        var loginInfoReturn; // Object that we want to send back to Login Page
        this.apiRequest.post('session', bodyData)
            .subscribe(function (jsonResp) {
            if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === 'SUCCESS') {
                // Create a success object that we want to send back to login page
                loginInfoReturn = {
                    'success': true,
                    'message': jsonResp.operationMessage,
                    'landingPage': _this.landingPage,
                    'user': {
                        'userId': jsonResp.item.userId,
                        'email': jsonResp.item.emailAddress,
                        'username': jsonResp.item.username,
                        'firstname': jsonResp.item.firsname,
                        'lastname': jsonResp.item.lastname,
                        'displayName': jsonResp.item.firstname + ' ' + jsonResp.item.lastname,
                        'token': jsonResp.item.token,
                    }
                };
                // store username and jwt token in session storage to keep user logged in between page refreshes
                _this.userInfoService.storeUserInfo(JSON.stringify(loginInfoReturn.user));
            }
            else {
                // Create a faliure object that we want to send back to login page
                loginInfoReturn = {
                    'success': false,
                    'message': jsonResp.msgDesc,
                    'landingPage': '/login'
                };
            }
            loginDataSubject.next(loginInfoReturn);
        });
        return loginDataSubject;
    };
    AuthService.prototype.logout = function (navigatetoLogout) {
        if (navigatetoLogout === void 0) { navigatetoLogout = true; }
        // clear token remove user from local storage to log user out
        this.userInfoService.removeUserInfo();
        if (navigatetoLogout) {
            this.router.navigate(['logout']);
        }
    };
    AuthService = __decorate([
        Injectable(),
        __metadata("design:paramtypes", [Router,
            UserInfoService,
            ApiRequestService])
    ], AuthService);
    return AuthService;
}());
export { AuthService };
//# sourceMappingURL=auth.service.js.map