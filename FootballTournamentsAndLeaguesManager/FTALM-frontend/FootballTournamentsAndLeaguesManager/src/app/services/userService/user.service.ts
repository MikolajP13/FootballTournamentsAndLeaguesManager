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

  static getUser(): User {
    const user = sessionStorage.getItem('user');
    
    return user ? JSON.parse(user) : undefined;
  }

  userLogin(user: User): Observable<boolean>{
    return this.httpClient.post<boolean>(`${this.apiServerUrl}/user/login`, user);
  }

  findUserByUserName(userName: string): Observable<User>{
    return this.httpClient.get<User>(`${this.apiServerUrl}/user/findUserByName/${userName}`);
  }

  findUserByEmailAddress(emailAddress: string): Observable<User>{
    return this.httpClient.get<User>(`${this.apiServerUrl}/user/findUserByEmail/${emailAddress}`);
  }

  addUser(user: User): Observable<User>{
    return this.httpClient.post<User>(`${this.apiServerUrl}/user/add`, user);
  }
}
