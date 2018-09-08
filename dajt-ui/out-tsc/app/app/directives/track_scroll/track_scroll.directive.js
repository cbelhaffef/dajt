var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var __param = (this && this.__param) || function (paramIndex, decorator) {
    return function (target, key) { decorator(target, key, paramIndex); }
};
import { Directive, Output, HostListener, EventEmitter, Inject } from '@angular/core';
import { DOCUMENT } from '@angular/common';
var TrackScrollDirective = /** @class */ (function () {
    function TrackScrollDirective(document) {
        this.document = document;
        this.bottom = new EventEmitter();
    }
    TrackScrollDirective.prototype.track = function () {
        /*
        if (document.body.scrollHeight == window.scrollY + window.innerHeight) {
             this.bottom.emit(true);
        }
        */
        if (Math.abs(document.body.scrollHeight - (window.scrollY + window.innerHeight)) <= 100) {
            this.bottom.emit(true);
        }
    };
    __decorate([
        Output(),
        __metadata("design:type", Object)
    ], TrackScrollDirective.prototype, "bottom", void 0);
    __decorate([
        HostListener('document: scroll', []),
        __metadata("design:type", Function),
        __metadata("design:paramtypes", []),
        __metadata("design:returntype", void 0)
    ], TrackScrollDirective.prototype, "track", null);
    TrackScrollDirective = __decorate([
        Directive({ selector: '[trackScroll]' }),
        __param(0, Inject(DOCUMENT)),
        __metadata("design:paramtypes", [Document])
    ], TrackScrollDirective);
    return TrackScrollDirective;
}());
export { TrackScrollDirective };
//# sourceMappingURL=track_scroll.directive.js.map