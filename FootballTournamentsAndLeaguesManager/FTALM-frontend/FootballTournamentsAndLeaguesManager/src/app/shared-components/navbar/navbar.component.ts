import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SidenavService } from 'src/app/services/sidenavService/sidenav.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent {

  constructor(private router: Router, private sidenavService: SidenavService){ }

  logOut(){
    sessionStorage.clear();
    this.router.navigate(["/"]);
  }

  isNotHomePage(): boolean {
    return this.router.url !== '/home';
  }

  toggleSidenav(): void{
    this.sidenavService.toggleSidenav();
  }
}
