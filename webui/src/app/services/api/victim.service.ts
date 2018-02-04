import {Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {ApiRequestService} from './api-request.service';
import {TranslateService} from './translate.service';
import {HttpParams} from '@angular/common/http';

@Injectable()
export class VictimService {

    constructor( private apiRequest: ApiRequestService, private translate: TranslateService) {}

    /**
     * Gets List of victims
     */
    getVictims(firstName?: string, lastName?: string, page?: number, size?: number): Observable<any> {
        // Create Request URL params
        let params: HttpParams = new HttpParams();
        params = params.append("page", typeof page === "number"? page.toString():"0");
        params = params.append("size", typeof size === "number"? size.toString():"1000");
        if (firstName && typeof firstName === "string"){
            params = params.append("firstName", firstName);
        }
        if (lastName && typeof lastName === "string"){
            params = params.append("lastName", lastName);
        }
        let vicitmListSubject = new Subject<any>(); // Will use this subject to emit data that we want
        this.apiRequest.get('api/victims', params)
            .subscribe(jsonResp => {
                vicitmListSubject.next(jsonResp.items);
            });
        return vicitmListSubject;
    }
}
