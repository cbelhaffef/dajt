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
/**
 * This is a singleton class
 */
var AppConfig = /** @class */ (function () {
    function AppConfig() {
        // Provide all the Application Configs here
        this.version = '1.0.0';
        this.locale = 'en-US';
        this.currencyFormat = { style: 'currency', currency: 'USD' };
        this.dateFormat = { year: 'numeric', month: 'short', day: 'numeric' };
        // API Related configs
        this.apiPort = '9119';
        if (this.apiProtocol === undefined) {
            this.apiProtocol = window.location.protocol;
        }
        if (this.apiHostName === undefined) {
            this.apiHostName = window.location.hostname;
        }
        if (this.apiPort === undefined) {
            this.apiPort = window.location.port;
        }
        if (this.apiHostName.includes('infomud') || this.apiHostName.includes('heroku')) {
            this.baseApiPath = this.apiProtocol + '//' + this.apiHostName + '/';
        }
        else {
            this.baseApiPath = this.apiProtocol + '//' + this.apiHostName + ':' + this.apiPort + '/';
        }
        if (this.locale === undefined) {
            this.locale = navigator.language;
        }
    }
    AppConfig = __decorate([
        Injectable(),
        __metadata("design:paramtypes", [])
    ], AppConfig);
    return AppConfig;
}());
export { AppConfig };
//# sourceMappingURL=app-config.js.map