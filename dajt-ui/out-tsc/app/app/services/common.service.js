var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
import { Injectable } from '@angular/core';
var CommonService = /** @class */ (function () {
    function CommonService() {
    }
    CommonService.prototype.filterItem = function (query, items) {
        var filtered = [];
        for (var i = 0; i < items.length; i++) {
            var accused = items[i];
            if (accused.name.toLowerCase().indexOf(query.toLowerCase()) === 0) {
                filtered.push(accused);
            }
        }
        return filtered;
    };
    CommonService = __decorate([
        Injectable(),
        __metadata("design:paramtypes", [])
    ], CommonService);
    return CommonService;
}());
export { CommonService };
//# sourceMappingURL=common.service.js.map