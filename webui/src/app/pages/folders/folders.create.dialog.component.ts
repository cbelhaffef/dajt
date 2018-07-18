import {Component, Inject, OnInit, ViewChild} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {FormControl, NgForm, Validators} from '@angular/forms';
import {OfficeService} from '../../services/api/office.service';
import {VictimService} from '../../services/api/victim.service';
import {AccusedService} from '../../services/api/accused.service';
import {FolderService} from '../../services/api/folder.service';
import {any} from 'codelyzer/util/function';
import {CourtService} from '../../services/api/court.service';
import {UserService} from '../../services/api/user.service';
import {UserInfoService} from '../../services/user-info.service';
import {Victim} from '../../models/victim.model';
import {Accused} from '../../models/accused.model';
import {Court} from '../../models/court.model';

@Component( {
    selector:  'folders-create-dialog-component',
    templateUrl:  'folders.create.dialog.component.html',
    styleUrls :  ['./folders.create.dialog.component.css']
})
export class FoldersCreateDialogComponent implements OnInit {

    @ViewChild(NgForm) createFolderForm:  NgForm;

    public listOffices = [];
    public listUsers = [];

    public filteredOffices:  any[] = [];
    public queryOffice:  string;

    public selectedVictims:  Victim[] = [];
    public selectedAccused:  Accused[] = [];

    public filteredCourts:  any[] = [];
    public selectedCourt:  string;
    public queryCourt:  string;

    public filteredUsers:  any[] = [];
    public selectedUser:  string;
    public queryUser:  string;

    public vict: string;
    public acc: string;

    officeControl = new FormControl('', [Validators.required]);

    constructor(
        public officeService:  OfficeService,
        public folderService:  FolderService,
        public courtService:   CourtService,
        public userService:  UserService,
        public userInfoService:  UserInfoService,
        public dialogRef:  MatDialogRef<FoldersCreateDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data:  any) { }

    ngOnInit():  void {
        let _self = this;
        _self.officeService.getOffices()
            .subscribe(function(offices) {
                _self.listOffices = offices;
            });
        _self.userService.getUsers()
            .subscribe(function(users) {
                _self.listUsers = users;
            });
    }

    close():  void {
        this.dialogRef.close();
    }

    filterOffices(event) {
        let _self = this;
        let query = event.query;
        _self.officeService.getOffices(query).subscribe(function(offices) {
            _self.filteredOffices = offices;
            _self.queryOffice = query;
        });
    }

    filterCourts(event) {
        let _self = this;
        let query = event.query;
        _self.courtService.getCourts(query).subscribe(function(courts) {
            _self.filteredCourts = _self.filterItem(query, courts);
            _self.queryCourt = query;
        });
    }

    filterUsers(event) {
        let _self = this;
        let query = event.query;
        let queryArr = query.split(' ');
        let firstname = queryArr[0];
        let lastname = queryArr.length > 1 ? queryArr[1] :  null;
        _self.userService.getUsers(firstname, lastname).subscribe(function(users) {
            _self.filteredUsers = [];
            for (let u of users) {
                _self.filteredUsers.push( {name:  u.firstname + ' ' + u.lastname , id:  u.userId});
            }
            _self.queryUser = query;
        });
    }

    filterItem(query, items:  any[]):  any[] {
        let filtered:  any[] = [];
        for (let i = 0; i < items.length; i++) {
            let accused = items[i];
            if (accused.name.toLowerCase().indexOf(query.toLowerCase()) === 0) {
                filtered.push(accused);
            }
        }
        return filtered;
    }

    addItem(event:  KeyboardEvent, item:  string, selectedItems:  any[], filterdItems:  any[]) {
        if (event.code === 'Enter' && filterdItems.length === 0) {
            selectedItems.push( { name:  item });
        }
    }

    addToVictims(event) {
        let _self = this;
        if (_self.selectedVictims.find(c => c.name === event.value ) === undefined) {
            _self.selectedVictims.push(new Victim(event.value));
        }
    }

    removeFromVictims(event) {
        let _self = this;
        let index = _self.selectedVictims.findIndex(c => c.name === event.value);
        if (index !== -1) {
            _self.selectedVictims.splice(index, 1);
        }
    }

    addToAccused(event) {
        let _self = this;
        if (_self.selectedAccused.find(c => c.name === event.value ) === undefined) {
            _self.selectedAccused.push(new Accused(event.value));
        }
    }

    removeFromAccused(event) {
        let _self = this;
        let index = _self.selectedAccused.findIndex(c => c.name === event.value);
        if (index !== -1) {
            _self.selectedAccused.splice(index, 1);
        }
    }

    createFolder(f:  NgForm) {
        let _self = this;
        let userStored = _self.userInfoService.getUserInfo();
        if (userStored != null) {
            f.value['reporter'] = { userId :  userStored.userId };
            f.value['updater'] = { userId :  userStored.userId };
        }

        f.value['victims'] = _self.selectedVictims;
        f.value['accused'] = _self.selectedAccused;
        if (typeof f.value['court'] === 'string') {
            let c = new Court(f.value['court']);
            f.value['court'] = c;
        }
        _self.folderService.addFolder(f.value)
            .subscribe(function(folder) {
                _self.close();

            });
    }
}
