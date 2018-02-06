import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {FolderService} from '../../services/api/folder.service';
import {VictimService} from '../../services/api/victim.service';
import {NgForm} from '@angular/forms';
import {GuiltyService} from '../../services/api/guilty.service';
import {Court} from '../../models/court.model';
import {CourtService} from '../../services/api/court.service';


@Component({
    selector   : 's-folders-create-modal-pg',
    templateUrl: './folders.create.modal.component.html',
})

export class FoldersCreateModalComponent implements OnInit {

    @ViewChild(NgForm) mf: NgForm;

    show = false;

    number: string;
    court: Court;
    offence: string;

    listVictims = [];
    selectedVictims = [];

    listGuilties = [];
    selectedGuilties = [];

    listCourts = [];


    constructor(private router: Router,
                private folderService: FolderService,
                private victimService: VictimService,
                private guiltyService: GuiltyService,
                private courtService:  CourtService) { }

    ngOnInit(): void {
        let me = this;
        me.victimService.getVictims()
            .subscribe(function(victims) {
                victims.forEach(function(v) {
                    me.listVictims.push({id: v.id , text: v.lastName + ' ' + v.firstName});
                });
        });

        me.guiltyService.getGuilties()
            .subscribe(function(guilties) {
                guilties.forEach(function(g) {
                    me.listGuilties.push({id: g.id , text : g.lastName + ' ' + g.firstName});
                });
            });

        me.courtService.getCourts()
            .subscribe(function(courts) {
                me.listCourts = courts;
            });
    }

    addFolder(mf: NgForm) {
        let me = this;
        mf = me.mf;
        me.folderService.addFolder(mf.value).subscribe(function(folder) {
          alert(folder);
          me.closeModal();
      });
    }

    closeModal() {
        this.show = false;
    }
}
