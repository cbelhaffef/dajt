import {Component, OnDestroy} from '@angular/core';
import {Subscription} from 'rxjs';
import {SpinnerService} from '../../services/spinner.service';

@Component( {
    selector:  'app-spinner',
    styleUrls  :  ['./spinner.component.scss'],
    templateUrl:  'spinner.component.html'
})

export class SpinnerComponent implements OnDestroy {
    public isLoading:  boolean;
    public subscription:  Subscription;

    constructor(private spinnerService:  SpinnerService) {
        const me = this;
        me.subscription = this.spinnerService.getLoading()
            .subscribe(loading => {
                me.isLoading = loading;
            });
    }

    ngOnDestroy() {
        // unsubscribe to ensure no memory leaks
        this.subscription.unsubscribe();
    }
}
