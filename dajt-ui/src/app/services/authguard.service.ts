import { Injectable } from '@angular/core';
import { UserinfoService } from './userinfo.service';
import { AuthService } from './api/auth.service';
import { Router, CanActivate, CanActivateChild, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

@Injectable()
export class AuthGuardService implements CanActivate, CanActivateChild {

    constructor(
        private router:  Router,
        private loginService:  AuthService,
        private userInfoService:  UserinfoService
    ) { }

    canActivate(route:  ActivatedRouteSnapshot, state:  RouterStateSnapshot):  boolean {
        let url:  string = state.url;
        return this.checkLogin(url);
        // return true;
    }

    canActivateChild(route:  ActivatedRouteSnapshot, state:  RouterStateSnapshot):  boolean {
        return this.canActivate(route, state);
    }

    checkLogin(url:  string):  boolean {
        if (this.userInfoService.isLoggedIn()) {
            return true;
        }
        // Store the original url in login service and then redirect to login page
        this.loginService.landingPage = url;
        this.router.navigate(['login',]);
        return false;
    }
}