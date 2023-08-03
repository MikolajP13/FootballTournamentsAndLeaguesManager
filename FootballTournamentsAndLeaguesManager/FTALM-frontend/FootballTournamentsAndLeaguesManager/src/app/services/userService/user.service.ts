import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { User } from '../../models/User/user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private httpClient: HttpClient) { }

  userLogin(user: User): Observable<boolean>{
    return this.httpClient.post<boolean>(`${this.apiServerUrl}/user/login`, user);
  }

  findUserByUserName(userName: string): Observable<User>{
    return this.httpClient.get<User>(`${this.apiServerUrl}/user/findUserByName/${userName}`);
  }

  findUserByEmailAddress(emailAddress: string): Observable<User>{
    return this.httpClient.get<User>(`${this.apiServerUrl}/user/findUserByEmail/${emailAddress}`);
  }
}
