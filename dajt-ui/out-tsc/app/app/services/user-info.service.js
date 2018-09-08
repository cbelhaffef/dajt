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
var UserInfoService = /** @class */ (function () {
    function UserInfoService() {
        this.currentUserKey = 'currentUser';
        this.storage = sessionStorage; // <--- you may switch between sessionStorage or LocalStrage (only one place to change)
    }
    // Store userinfo from session storage
    UserInfoService.prototype.storeUserInfo = function (userInfoString) {
        this.storage.setItem(this.currentUserKey, userInfoString);
    };
    // Remove userinfo from session storage
    UserInfoService.prototype.removeUserInfo = function () {
        this.storage.removeItem(this.currentUserKey);
    };
    // Get userinfo from session storage
    UserInfoService.prototype.getUserInfo = function () {
        try {
            var userInfoString = this.storage.getItem(this.currentUserKey);
            if (userInfoString) {
                var userObj = JSON.parse(this.storage.getItem(this.currentUserKey));
                return userObj;
            }
            else {
                return null;
            }
        }
        catch (e) {
            return null;
        }
    };
    UserInfoService.prototype.isLoggedIn = function () {
        return this.storage.getItem(this.currentUserKey) ? true : false;
    };
    // Get User's Display name from session storage
    UserInfoService.prototype.getUserName = function () {
        var userObj = this.getUserInfo();
        if (userObj !== null) {
            return userObj.displayName;
        }
        return 'no-user';
    };
    UserInfoService.prototype.getStoredToken = function () {
        var userObj = this.getUserInfo();
        if (userObj !== null) {
            return userObj.token;
        }
        return null;
    };
    UserInfoService = __decorate([
        Injectable(),
        __metadata("design:paramtypes", [])
    ], UserInfoService);
    return UserInfoService;
}());
export { UserInfoService };
//# sourceMappingURL=user-info.service.js.map