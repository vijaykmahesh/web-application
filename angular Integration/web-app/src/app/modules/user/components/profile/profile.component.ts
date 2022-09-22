import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { UserDTO } from 'src/app/models/UserDTO';
import { AuthserviceService } from 'src/app/services/authservice.service';
import { RequestService } from 'src/app/services/request.service';
import { environment } from 'src/environments/environment';
@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css'],
})
export class ProfileComponent implements OnInit {
  userDetail!: any;

  byDefault: string = '';

  profile!: FormGroup;

  file!: File;
  constructor(
    private authSer: AuthserviceService,
    private reqSer: RequestService
  ) {}

  ngOnInit(): void {
    this.profile = new FormGroup({
      file: new FormControl('', [Validators.required]),
      fileSource: new FormControl('', [Validators.required]),
    });
    this.userDetail = JSON.parse(this.authSer.getUser());

    this.reqSer.getUserById(this.userDetail.userId).subscribe(
      (result: UserDTO) => {
        if (!result.imagePath) {
          this.byDefault = environment.byDefaultImage;
        } else this.byDefault = result.imagePath;
      },
      (err: Error) => {
        console.log(err);
      }
    );
  }

  onFileChange(event: any) {
    if (event.target.files.length > 0) {
      const file = event.target.files[0];
      this.profile.patchValue({
        fileSource: file,
      });

      const formData = new FormData();
      formData.append('file', this.profile.value.fileSource);

      this.reqSer.uploadFile(formData, this.userDetail.emailId).subscribe(
        (result: UserDTO) => {
          this.byDefault = result.imagePath;
          console.log(this.byDefault);
        },
        (err: Error) => {
          console.log(err);
        }
      );
    }
  }
}
