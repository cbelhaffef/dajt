import {Injectable} from '@angular/core';
import {ApiRequestService} from './api-request.service';
import {TranslateService} from './translate.service';
import {Observable} from 'rxjs/Rx';
import {HttpParams} from '@angular/common/http';
import {Subject} from 'rxjs/Subject';

@Injectable()
export class PriorityService {

    constructor( private apiRequest:  ApiRequestService, private translate:  TranslateService) {}

    /**
     * Gets List of priorities
     */
    getPriorities():  Observable<any> {
        // Create Request URL params
        let params:  HttpParams = new HttpParams();

        let prioritiesListSubject = new Subject<any>(); // Will use this subject to emit data that we want
        this.apiRequest.get('api/priorities', params)
            .subscribe(jsonResp => {
                prioritiesListSubject.next(jsonResp.items);
            });
        return prioritiesListSubject;
    }
}
