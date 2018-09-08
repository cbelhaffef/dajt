import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';

@Injectable()
export class SharedService {

    private _toggle = new Subject();
    toggle$ = this._toggle.asObservable();

    constructor() {}

    emit(event) {
        this._toggle.next(event);
    }
}
