import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormControl, FormGroup } from '@angular/forms';
import { AuthserviceService } from '../services/authservice.service';
import { UserDTO } from '../models/UserDTO';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  loginForm = new FormGroup({
    email: new FormControl(''),
    password: new FormControl(''),
  });

  isInvalid: boolean = false;

  constructor(private router: Router, private authSer: AuthserviceService) {}

  ngOnInit(): void {}

  onSubmit(): void {
    if (this.loginForm.valid) {
      this.authSer
        .login(this.loginForm.value.email!, this.loginForm.value.password!)
        .subscribe(
          (result: UserDTO) => {
            this.authSer.setToken(result.password);
            this.authSer.setUser(JSON.stringify(result));
            this.router.navigate(['user']);
          },
          (err: Error) => {
            this.isInvalid = true;
          }
        );
    }
  }

  register() {
    this.router.navigate(['/register']);
  }
}
