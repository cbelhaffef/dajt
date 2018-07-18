import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Router} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/observable/throw';
import 'rxjs/add/observable/of';
import {UserInfoService} from '../user-info.service';
import {AppConfig} from '../../app-config';
import {SharedService} from '../shared.service';


@Injectable()
export class ApiRequestService {

    constructor(
        private appConfig:  AppConfig,
        private http:  HttpClient,
        private router:  Router,
        private userInfoService:  UserInfoService,
        private sharedService: SharedService
    ) {}

    /**
     * This is a Global place to add all the request headers for every REST calls
     */
    getHeaders():  HttpHeaders {
        let headers = new HttpHeaders();
        let token = this.userInfoService.getStoredToken();
        headers = headers.append('Content-Type', 'application/json');
        if (token !== null) {
            headers = headers.append('Authorization', token);
        }
        return headers;
    }

    get(url:  string, urlParams?:  HttpParams):  Observable<any> {
        let me = this;
        return this.http.get(this.appConfig.baseApiPath + url, {headers:  this.getHeaders(),  params:  urlParams} )
            .catch(function(error:  any) {
                console.log('Some error in catch');
                if ((error.status === 401 || error.status === 403) && me.router.url !== '/login') {
                    me.router.navigate(['/logout']);
                }
                me.sharedService.toggle(error);
                return Observable.throw(error || 'Server error');
            });
    }

    post(url:  string, body:  Object):  Observable<any> {
        let me = this;
        return this.http.post(this.appConfig.baseApiPath + url, JSON.stringify(body), { headers:  this.getHeaders()})
            .catch(function(error:  any) {
                if ((error.status === 401 || error.status === 403) && me.router.url !== '/login') {
                    me.router.navigate(['/logout']);
                } else if ((error.status === 401 || error.status === 403) && me.router.url === '/login') {
                    return Observable.of(error || 'Server error');
                }
                me.sharedService.toggle(error);
                return Observable.throw(error || 'Server error');
            });
    }

    put(url:  string, body:  Object):  Observable<any> {
        let me = this;
        return this.http.put(this.appConfig.baseApiPath + url, JSON.stringify(body), { headers:  this.getHeaders()})
            .catch(function(error:  any) {
                if ((error.status === 401 || error.status === 403) && me.router.url !== '/login') {
                    me.router.navigate(['/logout']);
                }
                me.sharedService.toggle(error);
                return Observable.throw(error || 'Server error');
            });
    }

    delete(url:  string):  Observable<any> {
        let me = this;
        return me.http.delete(this.appConfig.baseApiPath + url, { headers:  this.getHeaders()})
            .catch(function(error:  any) {
                if ((error.status === 401 || error.status === 403) && me.router.url !== '/login') {
                    me.router.navigate(['/logout']);
                }
                me.sharedService.toggle(error);
                return Observable.throw(error || 'Server error');
            });
    }

}
