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

@Component( {
    selector:  'folders-create-dialog-component',
    templateUrl:  'folders.create.dialog.component.html',
    styleUrls :  ['./folders.create.dialog.component.css']
})
export class FoldersCreateDialogComponent implements OnInit {

    @ViewChild(NgForm) createFolderForm:  NgForm;

    public listOffices = [];

    public filteredOffices:  any[] = [];
    public selectedOffice:  string;
    public queryOffice:  string;

    public filteredVictims:  any[] = [];
    public selectedVictims:  any[] = [];
    public queryVictim:  string;

    public filteredGuilties:  any[] = [];
    public selectedGuilties:  any[] = [];
    public queryGuilty:  string;

    public filteredCourts:  any[] = [];
    public selectedCourt:  string;
    public queryCourt:  string;

    public filteredUsers:  any[] = [];
    public selectedUser:  string;
    public queryUser:  string;

    officeControl = new FormControl('', [Validators.required]);

    constructor(
        public officeService:  OfficeService,
        public victimService:  VictimService,
        public guiltyService:  AccusedService,
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

    filterVictims(event) {
        let _self = this;
        let query = event.query;
        _self.victimService.getVictims(query).subscribe(function(victims) {
            _self.filteredVictims = _self.filterItem(query, victims);
            _self.queryVictim = query;
        });
    }

    filterGuilties(event) {
        let _self = this;
        let query = event.query;
        _self.guiltyService.getGuilties(query).subscribe(function(guilties) {
            _self.filteredGuilties = _self.filterItem(query, guilties);
            _self.queryGuilty = query;
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
        let lastName = queryArr[0];
        let firstName = queryArr.length > 1 ? queryArr[1] :  null;
        _self.userService.getUsers(lastName, firstName).subscribe(function(users) {
            _self.filteredUsers = [];
            for (let u of users) {
                _self.filteredUsers.push( {name:  u.lastName + ' ' + u.firstName , id:  u.userId});
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

    createFolder(f:  NgForm) {
        let _self = this;
        let userStored = _self.userInfoService.getUserInfo();
        if (userStored != null) {
            f.value['reporter'] = { userId :  _self.userInfoService.getUserInfo().userId };
        }
        _self.folderService.addFolder(f.value)
            .subscribe(function(folder) {
                _self.close();

            });
    }
}
