import {Injectable} from '@angular/core';
import {Subject} from 'rxjs';
import {Observable} from 'rxjs';

@Injectable()
export class SpinnerService {
    private loading = new Subject<boolean>();

    showSpinner() {
        this.loading.next(true);
    }

    hideSpinner() {
        this.loading.next(false);
    }

    getLoading():  Observable<boolean> {
        return this.loading.asObservable();
    }
}
