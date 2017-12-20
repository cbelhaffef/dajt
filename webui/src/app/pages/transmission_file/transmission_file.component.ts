import { Component, OnInit } from '@angular/core';
import {FormGroup, FormControl, Validators} from '@angular/forms';

@Component({
	selector: 's-transmission_file-pg',
	templateUrl: './transmission_file.component.html',
    styleUrls: [ './transmission_file.scss'],
})

export class TransmissionFileComponent {

    employeeAddressForm = new FormGroup({
        fullName: new FormControl('', Validators.required),
        address: new FormGroup({
            postalCode: new FormControl('', Validators.required),
            country: new FormControl('', Validators.required)
        })
    });
    submitted = false;

    subjects = [
        [{id : '1' , name : 'اهانة'}]
    ]

    onSubmit() {

    }

    addNewEmployeeAddress() {
        this.employeeAddressForm.reset();
        this.submitted = false;
    }
}
