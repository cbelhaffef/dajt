var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
import { Component, ViewEncapsulation } from '@angular/core';
import { ActivatedRoute, NavigationEnd, Router } from '@angular/router';
import { AuthService } from './services/api/auth.service';
import { UserInfoService } from './services/user-info.service';
import { filter, map, mergeMap } from 'rxjs/operators';
var HomeComponent = /** @class */ (function () {
    function HomeComponent(router, activeRoute, loginService, userInfoService) {
        var _this = this;
        this.router = router;
        this.activeRoute = activeRoute;
        this.loginService = loginService;
        this.userInfoService = userInfoService;
        this.showAppAlert = true;
        this.selectedHeaderItemIndex = 0;
        this.selectedSubNavItemIndex = 1;
        this.userName = '';
        // This block is to retrieve the data from the routes (routes are defined in app-routing.module.ts)
        router.events.pipe(filter(function (event) { return event instanceof NavigationEnd; }), map(function (_) { return _this.router.routerState.root; }), map(function (route) {
            while (route.firstChild) {
                route = route.firstChild;
            }
            return route;
        }), mergeMap(function (route) { return route.data; }))
            .subscribe(function (data) {
            console.log('Route data===:  ', data[0]);
            _this.selectedHeaderItemIndex = data[0] ? data[0].selectedHeaderItemIndex : -1;
            _this.selectedSubNavItemIndex = data[0] ? data[0].selectedSubNavItemIndex : -1;
        });
        this.userName = this.userInfoService.getUserName();
    }
    HomeComponent.prototype.navbarSelectionChange = function (val) {
        console.log(val);
    };
    HomeComponent.prototype.closeAppAlert = function () {
        this.showAppAlert = false;
    };
    HomeComponent = __decorate([
        Component({
            selector: 'app-home-comp',
            templateUrl: './home.component.html',
            styleUrls: ['./home.scss'],
            encapsulation: ViewEncapsulation.None
        }),
        __metadata("design:paramtypes", [Router,
            ActivatedRoute,
            AuthService,
            UserInfoService])
    ], HomeComponent);
    return HomeComponent;
}());
export { HomeComponent };
//# sourceMappingURL=home.component.js.map