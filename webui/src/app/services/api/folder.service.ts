import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Subject} from 'rxjs/Subject';
import {ApiRequestService} from './api-request.service';
import {TranslateService} from './translate.service';
import {HttpParams} from '@angular/common/http';
import {Folder} from '../../models/folder.model';
import {User} from '../../models/user.model';

@Injectable()
export class FolderService {

    constructor( private apiRequest: ApiRequestService, private translate: TranslateService) {}

    /**
     * Gets List of folders
     */
    getFolders(folderNumber?: string, office?: number, status?: string, victim?: string, guilty?: string, page?: number, size?: number): Observable<any> {
        // Create Request URL params
        const me = this;
        let params: HttpParams = new HttpParams();
        params = params.append('page', typeof page === 'number' ? page.toString() : '0');
        params = params.append('size', typeof size === 'number' ? size.toString() : '1000');
        if (folderNumber && typeof folderNumber === 'string') {
            params = params.append('folderNumber', folderNumber);
        }
        if (office && typeof office === 'string') {
            params = params.append('office', office);
        }
        if (status && typeof status === 'string') {
            params = params.append('status', status);
        }
        if (victim && typeof victim === 'string') {
            params = params.append('victim', victim);
        }
        if (guilty && typeof guilty === 'string') {
            params = params.append('guilty', guilty);
        }
        const folderListSubject = new Subject<any>(); // Will use this subject to emit data that we want
        this.apiRequest.get('api/folders', params)
            .subscribe(jsonResp => {
                const returnObj = jsonResp.items.map(function(v, i, a) {
                    const newRow = Object.assign({}, v, {
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

    getFolderDetails(id: string): Observable<any> {
        const me = this;
        const folderSubject = new Subject<any>();
        me.apiRequest.get('api/folders/' + id)
            .subscribe(jsonResp => {
                folderSubject.next(jsonResp);
            });
        return folderSubject;
    }

    addFolder(folder) {
        const me = this;
        const folderSubject = new Subject<any>();
        this.apiRequest.post('api/folders', folder)
            .subscribe(jsonResp => {
                    folderSubject.next(jsonResp);
                });
        return folderSubject;
    }

    assignUser(folders: number[], user: User): Observable<any> {
        const me = this;
        const folderSubject = new  Subject<any>();
        this.apiRequest.post('api/folders/assign/' + user.userId, folders)
            .subscribe(jsonResp => {
               folderSubject.next(jsonResp);
            });
        return folderSubject;
    }
}
