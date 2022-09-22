import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { UserDTO } from '../models/UserDTO';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class RequestService {
  constructor(private http: HttpClient) {}

  registerNewUser(userDto: any) {
    return this.http.post<UserDTO>(environment.apiUrl + 'createUser', userDto);
  }

  getUserById(userId: any) {
    return this.http.get<UserDTO>(
      environment.apiUrl + 'getUserById?userId=' + userId
    );
  }

  uploadFile(file: FormData, emailId: string) {
    console.log(file);
    return this.http.post<UserDTO>(
      environment.apiUrl + 'uploadFile?emailId=' + emailId,
      file
    );
  }
}
