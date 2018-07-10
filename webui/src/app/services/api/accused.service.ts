import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Subject} from 'rxjs/Subject';
import {ApiRequestService} from './api-request.service';
import {TranslateService} from './translate.service';
import {HttpParams} from '@angular/common/http';

@Injectable()
export class AccusedService {

    constructor( private apiRequest:  ApiRequestService, private translate:  TranslateService) {}

    /**
     * Gets List of guilties
     */
    getGuilties(name?:  string, page?:  number, size?:  number):  Observable<any> {
        // Create Request URL params
        let params:  HttpParams = new HttpParams();
        params = params.append('page', typeof page === 'number'? page.toString(): '0');
        params = params.append('size', typeof size === 'number'? size.toString(): '1000');
        if (name && typeof name === 'string') {
            params = params.append('name', name);
        }
        let guiltyListSubject = new Subject<any>(); // Will use this subject to emit data that we want
        this.apiRequest.get('api/guilties', params)
            .subscribe(jsonResp => {
                guiltyListSubject.next(jsonResp.items);
            });
        return guiltyListSubject;
    }
}
