import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css'],
})
export class ChangePasswordComponent implements OnInit {
  changePasswordForm!: FormGroup;

  isValidPassword: boolean = false;

  constructor(public dialog: MatDialog) {}

  ngOnInit(): void {
    this.changePasswordForm = new FormGroup({
      password: new FormControl('', [
        Validators.required,
        Validators.minLength(10),
      ]),

      confirmPassword: new FormControl('', [Validators.required]),
    });
  }

  changePassword() {
    console.log(this.changePasswordForm.value.password);
  }

  confirm() {
    if (
      this.changePasswordForm.value.password !==
      this.changePasswordForm.value.confirmPassword
    ) {
      this.isValidPassword = true;
    } else this.isValidPassword = false;
  }

  onNoClick(): void {
    this.dialog.closeAll();
  }
}
