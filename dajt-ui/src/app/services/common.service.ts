import {Injectable} from '@angular/core';


@Injectable()
export class CommonService {

    constructor() {}

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


