import { Component, OnInit,TemplateRef, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import 'rxjs/add/operator/mergeMap';


@Component( {
	selector:  'app-transmissions-pg',
	templateUrl:  './transmissions.component.html',
    styleUrls:  [ './transmissions.scss'],
})

export class TransmissionsComponent implements OnInit {
    @ViewChild('orderStatusCellTpl') statusCellTpl:  TemplateRef<any>;
    @ViewChild('orderIdTpl') orderIdTpl:  TemplateRef<any>;
    columns: any[];
    rows: any[];
    orderByStatusData:  any[] = [];
    isLoading: boolean=false;
    constructor(private router:  Router) { }

    ngOnInit() {
        var me = this;

        this.columns=[
             {prop: "orderId"         , name:  "ID"           , width: 65, cellTemplate:  this.orderIdTpl   },
             {prop: "orderDate"       , name:  "Order Date"   , width: 105 },
             {prop: "orderStatus"     , name:  "Status"       , width: 85, cellTemplate:  this.statusCellTpl },
             {prop: "customerName"    , name:  "Name"         , width: 150 },
             {prop: "customerEmail"   , name:  "Email"        , width: 200 },
             {prop: "customerCompany" , name:  "Company"      , width: 110 },
             {prop: "paymentType"     , name:  "Pay Type"     , width: 80  },
             {prop: "paidDate"        , name:  "Pay Date"     , width: 105 },
             {prop: "shippedDate"     , name:  "Ship Date"    , width: 105 },
             {prop: "shipCountry"     , name:  "Ship Country" , width: 110 }
        ];
    }



}
