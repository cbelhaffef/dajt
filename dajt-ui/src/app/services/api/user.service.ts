import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Subject} from 'rxjs/Subject';
import {ApiRequestService} from './api-request.service';
import {TranslateService} from './translate.service';
import {HttpParams} from '@angular/common/http';

@Injectable()
export class UserService {

    constructor( private apiRequest:  ApiRequestService, private translate:  TranslateService) {}

    /**
     * Gets List of operators
     */
    getUsers(firstname?:  string, lastname?:  string, page?:  number, size?:  number):  Observable<any> {
        // Create Request URL params
        let params:  HttpParams = new HttpParams();
        params = params.append('page', typeof page === 'number' ? page.toString() :  '0');
        params = params.append('size', typeof size === 'number' ? size.toString() :  '1000');
        if (firstname && typeof firstname === 'string') {
            params = params.append('firstname', firstname);
        }
        if (lastname && typeof lastname === 'string') {
            params = params.append('lastname', lastname);
        }
        const usersListSubject = new Subject<any>(); // Will use this subject to emit data that we want
        this.apiRequest.get('api/users', params)
            .subscribe(jsonResp => {
                usersListSubject.next(jsonResp.items);
            });
        return usersListSubject;
    }
}
