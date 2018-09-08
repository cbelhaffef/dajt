var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
import { Component, Input } from '@angular/core';
var LogoComponent = /** @class */ (function () {
    function LogoComponent() {
        this.fontColor = "#63666A";
        this.iconColor = "#E3642B";
    }
    __decorate([
        Input(),
        __metadata("design:type", Object)
    ], LogoComponent.prototype, "fontColor", void 0);
    __decorate([
        Input(),
        __metadata("design:type", String)
    ], LogoComponent.prototype, "iconColor", void 0);
    LogoComponent = __decorate([
        Component({
            selector: 'app-logo',
            templateUrl: './logo.component.html'
        })
    ], LogoComponent);
    return LogoComponent;
}());
export { LogoComponent };
//# sourceMappingURL=logo.component.js.map