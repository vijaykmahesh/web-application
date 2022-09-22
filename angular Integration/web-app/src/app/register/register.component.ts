import { Component, OnInit } from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { UserDTO } from '../models/UserDTO';

import { Router } from '@angular/router';
import { RequestService } from '../services/request.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  registrationForm!: FormGroup;

  isValidPassword: boolean = false;

  City: string[] = ['Bangalore', 'Belagum', 'Shimoga', 'Mangalore'];

  constructor(private router: Router, private reqSer: RequestService) {}

  ngOnInit(): void {
    this.registrationForm = new FormGroup({
      firstName: new FormControl('', [
        Validators.required,
        Validators.minLength(5),
      ]),
      lastName: new FormControl('', [
        Validators.required,
        Validators.minLength(2),
      ]),
      password: new FormControl('', [
        Validators.required,
        Validators.minLength(10),
      ]),

      cityName: new FormControl('', Validators.required),

      confirmPassword: new FormControl('', [Validators.required]),

      dob: new FormControl('', [
        Validators.required,
        Validators.pattern(
          /^\d{4}\-(0[1-9]|1[012])\-(0[1-9]|[12][0-9]|3[01])$/
        ),
      ]),

      gender: new FormControl('', Validators.required),

      emailId: new FormControl('', [Validators.required, Validators.email]),
      phoneNumber: new FormControl('', [
        Validators.required,
        Validators.pattern('^((\\+91-?)|0)?[0-9]{10}$'),
      ]),
    });
  }

  // dateString = '1968-11-16T00:00:00';
  // newDate = new Date(dateString);

  // format date in typescript
  // getFormatedDate(date: Date, format: string) {
  //   const datePipe = new DatePipe('en-US');
  //   return datePipe.transform(date, format);
  // }

  registration() {
    let newDate = new Date(this.registrationForm.value.dob);
    console.log(newDate);

    console.log(typeof newDate);

    this.reqSer.registerNewUser(this.registrationForm.value).subscribe(
      (result: UserDTO) => {
        this.router.navigate(['login']);
      },
      (err: Error) => {
        console.log(err);
      }
    );
  }

  confirm() {
    if (
      this.registrationForm.value.password !==
      this.registrationForm.value.confirmPassword
    ) {
      this.isValidPassword = true;
    } else this.isValidPassword = false;
  }

  reset() {
    this.registrationForm.reset();
  }
}
