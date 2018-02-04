import {Injectable} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {ApiRequestService} from './api-request.service';
import {TranslateService} from './translate.service';

@Injectable()
export class CourtService {

    constructor( private apiRequest: ApiRequestService, private translate: TranslateService) {}

    /**
     * Gets List of courts
     */
    getCourts(): Observable<any> {
        // Create Request URL params
        let courtListSubject = new Subject<any>(); // Will use this subject to emit data that we want
        this.apiRequest.get('api/courts')
            .subscribe(jsonResp => {
                courtListSubject.next(jsonResp.items);
            });
        return courtListSubject;
    }
}
