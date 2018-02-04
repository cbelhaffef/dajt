import {Court} from './court.model';
import {Victim} from './victim.model';
import {Guilty} from './guilty.model';
import {User} from './user.model';

export class Folder {

    constructor(public id: number,
                public number: string,
                public status: string,
                public offence: string,
                public judgementStatus: string,
                public court: Court,
                public user: User,
                public victims: Victim[],
                public guilties: Guilty[]) {}
}
