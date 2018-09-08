var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
import { Component } from '@angular/core';
var TransmissionFileComponent = /** @class */ (function () {
    function TransmissionFileComponent() {
        this.submitted = false;
        this.subjects = [
            { id: '1', name: 'اهانة' }
        ];
        this.professors = [
            { id: '1', name: 'بن براح عبد اللطيف' }
        ];
    }
    TransmissionFileComponent.prototype.onSubmit = function () {
    };
    TransmissionFileComponent.prototype.addNewEmployeeAddress = function () {
        this.submitted = false;
    };
    TransmissionFileComponent = __decorate([
        Component({
            selector: 'app-transmission_file-pg',
            templateUrl: './transmission_file.component.html',
            styleUrls: ['./transmission_file.scss'],
        })
    ], TransmissionFileComponent);
    return TransmissionFileComponent;
}());
export { TransmissionFileComponent };
//# sourceMappingURL=transmission_file.component.js.map