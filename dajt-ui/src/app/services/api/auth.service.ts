import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {Observable, Subject} from 'rxjs';
import {LoginInfoInStorage, UserInfoService} from '../user-info.service';
import {ApiRequestService} from './api-request.service';

export interface AuthRequestParam {
    username:  string;
    password:  string;
}

@Injectable()
export class AuthService {

    public landingPage = '/home/legal_cases/folders';

    constructor(
        private router:  Router,
        private userInfoService:  UserInfoService,
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

        this.apiRequest.post('session', bodyData)
            .subscribe(jsonResp => {
                if (jsonResp !== undefined && jsonResp !== null && jsonResp.operationStatus === 'SUCCESS') {
                    // Create a success object that we want to send back to login page
                    loginInfoReturn = {
                        'success'    :  true,
                        'message'    :  jsonResp.operationMessage,
                        'landingPage':  this.landingPage,
                        'user'       :   {
                            'userId'     :  jsonResp.item.userId,
                            'email'      :  jsonResp.item.emailAddress,
                            'username'   :  jsonResp.item.username,
                            'firstname'  :  jsonResp.item.firsname,
                            'lastname'   :  jsonResp.item.lastname,
                            'displayName':  jsonResp.item.firstname + ' ' + jsonResp.item.lastname,
                            'token'      :  jsonResp.item.token,
                        }
                    };

                    // store username and jwt token in session storage to keep user logged in between page refreshes
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
