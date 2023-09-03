import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/models/User/user';
import { SidenavService } from 'src/app/services/sidenavService/sidenav.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {
  authUser!: User;
  
  constructor(private router: Router, private sidenavService: SidenavService){ }

  ngOnInit(){
    const user = sessionStorage.getItem('user');
    
    if(user){
      this.authUser = JSON.parse(user);
    }
    
  }

  logOut(){
    sessionStorage.clear();
    this.router.navigate(["/"]);
  }

  isDetailPage(): boolean {
    return this.router.url.includes('/team/') || this.router.url.includes('/league/') || this.router.url.includes('/tournament/');
  }

  toggleSidenav(): void{
    this.sidenavService.toggleSidenav();
  }
}
