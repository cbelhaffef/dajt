var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
import { Component } from '@angular/core';
import { SpinnerService } from '../../services/spinner.service';
var SpinnerComponent = /** @class */ (function () {
    function SpinnerComponent(spinnerService) {
        this.spinnerService = spinnerService;
        var me = this;
        me.subscription = this.spinnerService.getLoading()
            .subscribe(function (loading) {
            me.isLoading = loading;
        });
    }
    SpinnerComponent.prototype.ngOnDestroy = function () {
        // unsubscribe to ensure no memory leaks
        this.subscription.unsubscribe();
    };
    SpinnerComponent = __decorate([
        Component({
            selector: 'app-spinner',
            styleUrls: ['./spinner.component.scss'],
            templateUrl: 'spinner.component.html'
        }),
        __metadata("design:paramtypes", [SpinnerService])
    ], SpinnerComponent);
    return SpinnerComponent;
}());
export { SpinnerComponent };
//# sourceMappingURL=spinner.component.js.map