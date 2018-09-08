import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {Subject} from 'rxjs';
import {ApiRequestService} from './api-request.service';
import {TranslateService} from './translate.service';
import {HttpParams} from '@angular/common/http';
import {Folder} from '../../models/folder.model';
import {User} from '../../models/user.model';
import {Action} from '../../models/action.model';
import {Status} from '../../models/status.model';
import {Victim} from '../../models/victim.model';
import {Accused} from '../../models/accused.model';

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
                folderListSubject.next(jsonResp); // incidentList is a Subject and emits an event thats being listened to by the components
            });
        return folderListSubject;
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

    updateFolder(folder: Folder): Subject<any> {
        let  me = this;
        let  folderSubject = new Subject<any>();
        this.apiRequest.put('api/folders', folder)
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

    assignUserToFolder(folder:  Folder, user:  User):  Observable<any> {
        const me = this;
        const folderSubject = new  Subject<any>();
        this.apiRequest.put('api/folders/' + folder.id + '/assign', user)
            .subscribe(jsonResp => {
                folderSubject.next(jsonResp);
            });
        return folderSubject;
    }

    addActionToListOfFolders(folders:  number[], action:  Action):  Observable<any> {
        const me = this;
        const folderSubject = new  Subject<any>();
        this.apiRequest.post('api/folders/addAction/' + action.code, folders)
            .subscribe(jsonResp => {
                folderSubject.next(jsonResp);
            });
        return folderSubject;
    }

    changeStatusToListOfFolders(folders:  number[], status:  Status):  Observable<any> {
        const _self = this;
        const folderSubject = new  Subject<any>();
        _self.apiRequest.post('api/folders/changeStatus/' + status.code, folders)
            .subscribe(jsonResp => {
                folderSubject.next(jsonResp);
            });
        return folderSubject;
    }

    addVictim(folder: Folder , victim: Victim): Observable<any> {
        let _self = this;
        let folderSubject = new Subject<any>();
        _self.apiRequest.put('api/folders/' + folder.id + '/addVictim', victim)
            .subscribe(jsonResp => {
                folderSubject.next(jsonResp);
            });
        return folderSubject;
    }

    removeVictim(folder: Folder , victim: Victim): Observable<any> {
        let _self = this;
        let folderSubject = new Subject<any>();
        _self.apiRequest.delete('api/folders/' + folder.id + '/removeVictim/' + victim.id)
            .subscribe(jsonResp => {
                folderSubject.next(jsonResp);
            });
        return folderSubject;
    }

    addAccused(folder: Folder , accused: Accused): Observable<any> {
        let _self = this;
        let folderSubject = new Subject<any>();
        _self.apiRequest.put('api/folders/' + folder.id + '/addAccused', accused)
            .subscribe(jsonResp => {
                folderSubject.next(jsonResp);
            });
        return folderSubject;
    }

    removeAccused(folder: Folder , accused: Accused): Observable<any> {
        let _self = this;
        let folderSubject = new Subject<any>();
        _self.apiRequest.delete('api/folders/' + folder.id + '/removeAccused/' + accused.id)
            .subscribe(jsonResp => {
                folderSubject.next(jsonResp);
            });
        return folderSubject;
    }

    addAction(folder: Folder , action: Action): Observable<any> {
        let _self = this;
        let folderSubject = new Subject<any>();
        _self.apiRequest.put('api/folders/' + folder.id + '/addAction', action)
            .subscribe(jsonResp => {
                folderSubject.next(jsonResp);
            });
        return folderSubject;
    }

    removeAction(folder: Folder , action: Action): Observable<any> {
        let _self = this;
        let folderSubject = new Subject<any>();
        _self.apiRequest.delete('api/folders/' + folder.id + '/removeAction/' + action.id)
            .subscribe(jsonResp => {
                folderSubject.next(jsonResp);
            });
        return folderSubject;
    }
}
