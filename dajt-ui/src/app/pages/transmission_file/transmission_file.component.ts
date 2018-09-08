import { Component, OnInit } from '@angular/core';
import {FormGroup, FormControl, Validators} from '@angular/forms';

@Component( {
	selector:  'app-transmission_file-pg',
	templateUrl:  './transmission_file.component.html',
    styleUrls:  [ './transmission_file.scss'],
})

export class TransmissionFileComponent {

    submitted = false;

    subjects = [
         {id :  '1' , name :  'اهانة'}
    ];

    professors = [
         {id :  '1' , name :  'بن براح عبد اللطيف'}
    ];

    onSubmit() {

    }

    addNewEmployeeAddress() {
        this.submitted = false;
    }
}
