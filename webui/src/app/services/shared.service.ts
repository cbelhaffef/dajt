import {Injectable} from '@angular/core';
import {Subject} from 'rxjs/Subject';

@Injectable()
export class SharedService {

    private _toggle = new Subject();
    toggle$ = this._toggle.asObservable();

    constructor() {}

    toggle(event) {
        this._toggle.next(event);
    }
}
