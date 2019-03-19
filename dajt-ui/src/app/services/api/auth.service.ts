import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {Observable, Subject} from 'rxjs';
import {LoginInfoInStorage, UserinfoService} from '../userinfo.service';
import {ApiRequestService} from './api-request.service';

export interface AuthRequestParam {
    username:  string;
    password:  string;
}

@Injectable()
export class AuthService {

    public landingPage = '/home/cases/folders';

    constructor(
        private router:  Router,
        private userInfoService:  UserinfoService,
        private apiRequest:  ApiRequestService) {}


    getToken(username:  string, password:  string):  Observable<any> {
        let me = this;

        let bodyData:  AuthRequestParam = {
            'username':  username,
            'password':  password,
        };

        // Will use this subject to emit data that we want after ajax login attempt
        let loginDataSubject:  Subject<any> = new Subject<any>();
        let loginInfoReturn:  LoginInfoInStorage; // Object that we want to send back to Login Page

        this.apiRequest.post('api/auth/signin', bodyData)
            .subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === 'SUCCESS') {
                    // Create a success object that we want to send back to login page
                    loginInfoReturn = {
                        'success'    :  true,
                        'message'    :  jsonResp.operationMessage,
                        'landingPage':  this.landingPage,
                        'user'       :   {
                            'userId'     :  jsonResp.item.userPrinciple.id,
                            'email'      :  jsonResp.item.userPrinciple.email,
                            'username'   :  jsonResp.item.userPrinciple.username,
                            'firstname'  :  jsonResp.item.userPrinciple.firsname,
                            'lastname'   :  jsonResp.item.userPrinciple.lastname,
                            'displayName':  jsonResp.item.userPrinciple.firstname + ' ' + jsonResp.item.userPrinciple.lastname,
                            'token'      :  jsonResp.item.token,
                        }
                    };

                    // store username and jwt token in auth storage to keep user logged in between page refreshes
                    this.userInfoService.storeUserInfo(JSON.stringify(loginInfoReturn.user));
                } else {
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
    }

    logout(navigatetoLogout = true):  void {
        // clear token remove user from local storage to log user out
        this.userInfoService.removeUserInfo();
        if (navigatetoLogout) {
            this.router.navigate(['logout']);
        }
    }
}
