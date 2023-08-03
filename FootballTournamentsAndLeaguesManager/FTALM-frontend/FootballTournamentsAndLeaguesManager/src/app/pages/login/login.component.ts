import { Component } from '@angular/core';
import { UserService } from '../../services/userService/user.service';
import { Router } from '@angular/router';
import { NgForm } from '@angular/forms';
import { User } from 'src/app/models/User/user';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent {

  constructor(private userService: UserService, private router: Router) { }

  loginUser(loginForm: NgForm) {
    var userToAuthenticate: User;

    var userName = loginForm.value.login;
    var userEmailAddress = loginForm.value.login;

    if(loginForm.value.login.match(/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*\.\w{2,}$/)){
      userName = null;
    }else{
      userEmailAddress = null;
    }

    userToAuthenticate = {
      userName: userName,
      emailAddress: userEmailAddress,
      password: loginForm.value.password
    }

    this.userService.userLogin(userToAuthenticate).subscribe({
      error: () => { console.log("Ivalid username/password or account not enabled."); },
      complete: () => {
        if(userEmailAddress !== null) {
          this.userService.findUserByEmailAddress(userEmailAddress).subscribe((user: User) => {
            sessionStorage.setItem('user', JSON.stringify(user));
            this.router.navigate(['/home']);
          });
        }else {
          this.userService.findUserByUserName(userName).subscribe((user: User) => {
            sessionStorage.setItem('user', JSON.stringify(user));
            this.router.navigate(['/home']); 
          });
        }
      }
    });
  }
}
