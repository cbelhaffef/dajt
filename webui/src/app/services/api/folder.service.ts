import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Subject} from 'rxjs/Subject';
import {ApiRequestService} from './api-request.service';
import {TranslateService} from './translate.service';
import {HttpParams} from '@angular/common/http';
import {Folder} from '../../models/folder.model';
import {User} from '../../models/user.model';
import {Action} from '../../models/action.model';

@Injectable()
export class FolderService {

    constructor( private apiRequest:  ApiRequestService, private translate:  TranslateService) {}

    /**
     * Gets List of folders
     */
    getFolders(folderNumber?:  string, office?:  number, status?:  string, victim?:  string, accused?:  string, page?:  number, size?:  number):  Observable<any> {
        // Create Request URL params
        const me = this;
        let params:  HttpParams = new HttpParams();
        params = params.append('page', typeof page === 'number' ? page.toString() :  '0');
        params = params.append('size', typeof size === 'number' ? size.toString() :  '10');
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
        if (accused && typeof accused === 'string') {
            params = params.append('accused', accused);
        }
        const folderListSubject = new Subject<any>(); // Will use this subject to emit data that we want
        this.apiRequest.get('api/folders', params)
            .subscribe(jsonResp => {
                // const returnObj = jsonResp.items.map(function(v, i, a) {
                //     const newRow = Object.assign( {}, v, {
                //         created  :  me.translate.getDateString(v.created),
                //         updated   :  me.translate.getDateString(v.updated),
                //         closed:  (v.closed != null ? me.translate.getDateString(v.closed) :  '')
                //     });
                //     return newRow;
                // });

                folderListSubject.next(jsonResp); // incidentList is a Subject and emits an event thats being listened to by the components
            });
        return folderListSubject;
    }

    getFolderStatus(name?:  string):  Observable<any> {
        let params:  HttpParams = new HttpParams();
        if (name != null) {
            params = params.append('name', name);
        }

        return this.apiRequest.get('api/folders/status', params);
    }

    getFolderPriority(name?:  string):  Observable<any> {
        let params:  HttpParams = new HttpParams();
        if (name != null) {
            params = params.append('name', name);
        }

        return this.apiRequest.get('api/folders/priorities', params);
    }


    getFolderDetails(id:  string):  Observable<any> {
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

    assignUser(folders:  number[], user:  User):  Observable<any> {
        const me = this;
        const folderSubject = new  Subject<any>();
        this.apiRequest.post('api/folders/assign/' + user.userId, folders)
            .subscribe(jsonResp => {
               folderSubject.next(jsonResp);
            });
        return folderSubject;
    }

    addActionToListOfFolders(folders:  number[], action:  Action):  Observable<any> {
        const me = this;
        const folderSubject = new  Subject<any>();
        this.apiRequest.post('api/folders/addAction/' + action.id, folders)
            .subscribe(jsonResp => {
                folderSubject.next(jsonResp);
            });
        return folderSubject;
    }

    changeStatusToListOfFolders(folders:  number[], status:  string):  Observable<any> {
        const me = this;
        const folderSubject = new  Subject<any>();
        this.apiRequest.post('api/folders/changeStatus/' + status, folders)
            .subscribe(jsonResp => {
                folderSubject.next(jsonResp);
            });
        return folderSubject;
    }
}
