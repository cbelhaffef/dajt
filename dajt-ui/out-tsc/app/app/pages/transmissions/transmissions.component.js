var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
import { Component, TemplateRef, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import 'rxjs/add/operator/mergeMap';
var TransmissionsComponent = /** @class */ (function () {
    function TransmissionsComponent(router) {
        this.router = router;
        this.orderByStatusData = [];
        this.isLoading = false;
    }
    TransmissionsComponent.prototype.ngOnInit = function () {
        var me = this;
        this.columns = [
            { prop: "orderId", name: "ID", width: 65, cellTemplate: this.orderIdTpl },
            { prop: "orderDate", name: "Order Date", width: 105 },
            { prop: "orderStatus", name: "Status", width: 85, cellTemplate: this.statusCellTpl },
            { prop: "customerName", name: "Name", width: 150 },
            { prop: "customerEmail", name: "Email", width: 200 },
            { prop: "customerCompany", name: "Company", width: 110 },
            { prop: "paymentType", name: "Pay Type", width: 80 },
            { prop: "paidDate", name: "Pay Date", width: 105 },
            { prop: "shippedDate", name: "Ship Date", width: 105 },
            { prop: "shipCountry", name: "Ship Country", width: 110 }
        ];
    };
    __decorate([
        ViewChild('orderStatusCellTpl'),
        __metadata("design:type", TemplateRef)
    ], TransmissionsComponent.prototype, "statusCellTpl", void 0);
    __decorate([
        ViewChild('orderIdTpl'),
        __metadata("design:type", TemplateRef)
    ], TransmissionsComponent.prototype, "orderIdTpl", void 0);
    TransmissionsComponent = __decorate([
        Component({
            selector: 'app-transmissions-pg',
            templateUrl: './transmissions.component.html',
            styleUrls: ['./transmissions.scss'],
        }),
        __metadata("design:paramtypes", [Router])
    ], TransmissionsComponent);
    return TransmissionsComponent;
}());
export { TransmissionsComponent };
//# sourceMappingURL=transmissions.component.js.map