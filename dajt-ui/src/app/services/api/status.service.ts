import {Injectable} from '@angular/core';
import {ApiRequestService} from './api-request.service';
import {TranslateService} from './translate.service';
import {Observable} from 'rxjs';
import {HttpParams} from '@angular/common/http';
import {Subject} from 'rxjs';

@Injectable()
export class StatusService {

    constructor( private apiRequest:  ApiRequestService, private translate:  TranslateService) {}

    /**
     * Gets List of status
     */
    getStatus():  Observable<any> {
        // Create Request URL params
        let params:  HttpParams = new HttpParams();

        let statusListSubject = new Subject<any>(); // Will use this subject to emit data that we want
        this.apiRequest.get('api/status', params)
            .subscribe(jsonResp => {
                statusListSubject.next(jsonResp.items);
            });
        return statusListSubject;
    }
}
