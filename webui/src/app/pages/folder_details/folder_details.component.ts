import { Component, OnInit,TemplateRef, ViewChild } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { FormBuilder, FormGroup , FormControl,Validators} from '@angular/forms';
import 'rxjs/add/operator/switchMap';
import {FolderService} from '../../services/api/folder.service';

@Component({
    selector   : 's-folders-pg',
    templateUrl: './folder_details.component.html',
    styleUrls  : [ './folder_details.scss'],
})

export class FolderDetailsComponent implements OnInit {

    public folderNumber:number;
    public frmFolderDetail: FormGroup;
    public folderDetails: any = { number: '', affairs: []};
    public isFolderOnHold: boolean = false;

    public rows=[];
    public columns =[
        {prop:"number"           ,  name: "Numéro d'affaire"    , width:105 },
        {prop:"status"           ,  name: "Status"    , width:80 },
        {prop:"createDate"       ,   name: "Date de création"       , width:70  },
        {prop:"modificationDate" , name: "Date de modification"   , width:100 },
        {prop:"closeDate"        , name: "Date de clôture"   , width:100 },
        {prop:"topic"            , name: "Thème" , width:70  },
        {prop:"prosecutor"       , name: "Procureur" , width:70  },
        {prop:"defendant"        , name: "Défendeur" , width:70  },
        {prop:"defendant"        , name: "Défendeur" , width:70  },
        {prop:"judiciaryCommission"        , name: "Corps judiciare", width:70 }
    ];

    constructor(
        private route: ActivatedRoute,
        private router: Router,
        private formBuilder: FormBuilder,
        private folderService: FolderService
    ) {}

    ngOnInit(): void {
        this.getData();
    }

    getData(){
        var me = this;
        this.route.params
            .switchMap( function(params: Params){
                me.folderNumber = params['folderNumber'];
                return me.folderService.getFolderDetails(params['folderNumber']);
            })
            .subscribe(function(resp){
                console.log("Folder details", resp);
                me.folderDetails = resp;
            });
    }

    goBack(){
        console.log("Back");
    }



}
