import {Court} from './court.model';
import {Victim} from './victim.model';
import {Guilty} from './guilty.model';
import {User} from './user.model';
import {Office} from './office.model';
import {Action} from './action.model';

export class Folder {

    public id:  number;
    public number:  string;
    public status:  string;
    public priority:  string;
    public offence:  string;
    public office:  Office;
    public judgementStatus:  string;
    public court:  Court;
    public assignee:  User;
    public reporter:  User;
    public victims:  Victim[];
    public guilties:  Guilty[];
    public actions:  Action[];
    public sendingType:  string;
    public createDate:  Date;
    public modifDate:  Date;
    public closeDate:  Date;

    constructor() {
        this.court = new Court();
        this.office = new Office();
        this.assignee = new User();
        this.reporter = new User();
    }
}
