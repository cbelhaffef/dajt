import { Injectable, Inject } from '@angular/core';
import { Observable, ReplaySubject, Subject } from 'rxjs';
import { ApiRequestService } from './api-request.service';
import { TranslateService } from './translate.service';
import { HttpParams} from "@angular/common/http";

@Injectable()
export class FolderService {

    constructor( private apiRequest: ApiRequestService, private translate: TranslateService) {}

    /**
     * Gets List of orders
     */
    getFolder(folderNumber?:string, status?:string,page?:number, size?:number): Observable<any> {
        //Create Request URL params
        let me = this;
        let params: HttpParams = new HttpParams();
        params = params.append('page', typeof page === "number"? page.toString():"0");
        params = params.append('size', typeof size === "number"? size.toString():"1000");
        if (folderNumber && typeof folderNumber === "string"){
            params = params.append(folderNumber);
        }
        if (status && typeof status === "string"){
            params = params.append(status);
        }
        let folderListSubject = new Subject<any>(); // Will use this subject to emit data that we want
        this.apiRequest.get('api/folders',params)
            .subscribe(jsonResp => {
                let returnObj = jsonResp.items.map(function(v, i, a){
                    let newRow = Object.assign({}, v, {
                        createDate  : me.translate.getDateString(v.createDate),
                        modifDate   : me.translate.getDateString(v.modifDate),
                        closeDate: (v.closeDate != null ? me.translate.getDateString(v.closeDate) : '')
                    });
                    return newRow;
                });
                folderListSubject.next(returnObj); // incidentList is a Subject and emits an event thats being listened to by the components
            });
        return folderListSubject;
    }

    getFolderStatus(): Observable<any> {
        return this.apiRequest.get('api/folders/status');
    }

    getFolderDetails(number: string): Observable<any> {
        return this.apiRequest.get('api/folders/' + number);
    }

}
