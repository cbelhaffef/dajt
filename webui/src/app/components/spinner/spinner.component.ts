import {Component, OnDestroy} from '@angular/core';
import {Subscription} from 'rxjs/Subscription';
import {SpinnerService} from '../../services/spinner.service';

@Component({
    selector: 'app-spinner',
    templateUrl: 'spinner.component.html'
})

export class SpinnerComponent implements OnDestroy {
    isLoading: boolean;
    subscription: Subscription;

    constructor(private spinnerService: SpinnerService) {
        // subscribe to home component messages
        this.subscription = this.spinnerService.getLoading()
            .subscribe(loading => { this.isLoading = loading; });
    }

    ngOnDestroy() {
        // unsubscribe to ensure no memory leaks
        this.subscription.unsubscribe();
    }
}
