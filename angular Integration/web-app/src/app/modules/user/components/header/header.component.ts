import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthserviceService } from 'src/app/services/authservice.service';
import { MatDialog } from '@angular/material/dialog';
import { ChangePasswordComponent } from '../change-password/change-password.component';
@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css'],
})
export class HeaderComponent implements OnInit {
  userDetail!: any;
  constructor(
    private router: Router,
    private authSer: AuthserviceService,
    public dialog: MatDialog
  ) {
    this.userDetail = JSON.parse(this.authSer.getUser());
  }

  ngOnInit(): void {}

  logout() {
    localStorage.clear();
    this.router.navigate(['/login']);
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(ChangePasswordComponent, {
      disableClose: true,
      width: '425px',
      height: '425px',
    });
  }
}
