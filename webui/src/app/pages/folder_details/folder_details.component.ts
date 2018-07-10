import {Component, OnInit} from '@angular/core';
import 'rxjs/add/operator/switchMap';
import {ActivatedRoute} from '@angular/router';
import {FolderService} from '../../services/api/folder.service';
import {Folder} from '../../models/folder.model';
import {FormBuilder, FormControl, FormGroup, NgForm, Validators} from '@angular/forms';
import {CourtService} from '../../services/api/court.service';
import {VictimService} from '../../services/api/victim.service';
import {AccusedService} from '../../services/api/accused.service';
import {ActionService} from '../../services/api/action.service';
import {Message, SelectItem} from 'primeng/api';

@Component( {
    selector   :  's-folders-pg',
    templateUrl:  './folder_details.component.html',
    styleUrls  :  [ './folder_details.scss'],
})

export class FolderDetailsComponent implements OnInit {

    public folder:  Folder = new Folder();

    public detailForm:  FormGroup;

    public submitted:  boolean;

    public genders:  SelectItem[];

    msgs:  Message[] = [];

    public isLoading = false;

    public showUpdateDetail = false;

    public filteredStatus:  any[] = [];
    public queryStatus:  string;

    public filteredCourts:  any[] = [];
    public queryCourt:  string;

    public filteredPriority:  any[] = [];
    public queryPriority:  string;

    public filteredVictims:  any[] = [];
    public queryVictim:  string;

    public filteredGuilties:  any[] = [];
    public queryGuilty:  string;

    public filteredActions:  any[] = [];
    public queryAction:  string;

    public showPencilCourt:  boolean;

    public showPencilSendingType:  boolean;

    constructor(private activateRoute:  ActivatedRoute,
                private folderService:  FolderService,
                private victimService:  VictimService,
                private guiltyService:  AccusedService,
                private actionService:  ActionService,
                private courtService:  CourtService,
                private fb:  FormBuilder) {
    }

    ngOnInit():  void {
        const me = this;
        me.isLoading = true;
        me.folderService.getFolderDetails(me.activateRoute.snapshot.params.id)
            .subscribe(function(folder) {
                me.folder = folder;
                me.isLoading = false;
            });
        this.detailForm = this.fb.group( {
            'firstname':  new FormControl('', Validators.required),
            'lastname':  new FormControl('', Validators.required),
            'password':  new FormControl('', Validators.compose([Validators.required, Validators.minLength(6)])),
            'description':  new FormControl(''),
            'gender':  new FormControl('', Validators.required)
        });

    }

    updateDetail() {
        this.showUpdateDetail = !this.showUpdateDetail;
    }

    onSubmit(value:  string) {
        this.submitted = true;
        this.msgs = [];
        this.msgs.push( {severity: 'info', summary: 'Success', detail: 'Form Submitted'});
    }

    addFolder(f:  NgForm) {
        const me = this;
        me.isLoading = true;
        me.folderService.addFolder(f.value)
            .subscribe(function(folder) {
                alert(folder);
                me.isLoading = false;
            });
    }

    filterStatus(event) {
        let _self = this;
        let query = event.query;
        _self.folderService.getFolderStatus(query).subscribe(function(status) {
            _self.filteredStatus = _self.filterItem(query, status);
            _self.queryStatus = query;
        });
    }

    filterPriority(event) {
        let _self = this;
        let query = event.query;
        _self.folderService.getFolderPriority(query).subscribe(function(priority) {
            _self.filteredPriority = _self.filterItem(query, priority);
            _self.queryPriority = query;
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

    filterActions(event) {
        let _self = this;
        let query = event.query;
        _self.actionService.getActions(query).subscribe(function(actions) {
            _self.filteredActions = _self.filterItem(query, actions);
            _self.queryAction = query;
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
}
