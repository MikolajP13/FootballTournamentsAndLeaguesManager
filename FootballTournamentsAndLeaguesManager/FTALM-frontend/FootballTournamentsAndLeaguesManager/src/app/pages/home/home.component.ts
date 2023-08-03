import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/User/user';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  authUser!: User;

  constructor(private router: Router){ }

  ngOnInit() {
    this.getUser();
  }

  getUser() {
    const user = sessionStorage.getItem('user');
    
    if(user){
      this.authUser = JSON.parse(user);
    }
  }

  logOut(){
    sessionStorage.clear();
    this.router.navigate(["/"]);
  }
}
