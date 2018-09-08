import {Injectable} from '@angular/core';
import {ApiRequestService} from './api-request.service';
import {TranslateService} from './translate.service';
import {Observable} from 'rxjs';
import {HttpParams} from '@angular/common/http';
import {Subject} from 'rxjs';

@Injectable()
export class ActionService {

    constructor( private apiRequest:  ApiRequestService, private translate:  TranslateService) {}

    /**
     * Gets List of actions
     */
    getActions(page?:  number, size?:  number):  Observable<any> {
        // Create Request URL params
        let params:  HttpParams = new HttpParams();
        params = params.append("page", typeof page === "number"? page.toString(): "0");
        params = params.append("size", typeof size === "number"? size.toString(): "1000");

        let actionListSubject = new Subject<any>(); // Will use this subject to emit data that we want
        this.apiRequest.get('api/actions', params)
            .subscribe(jsonResp => {
                actionListSubject.next(jsonResp.items);
            });
        return actionListSubject;
    }
}
