import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'FootballTournamentsAndLeaguesManager';

  constructor(private router: Router) {}

  isNotLoginOrRegistrationPage(): boolean {
    return this.router.url === '/home' || this.router.url === '/teams' || this.router.url === '/leagues' || this.router.url === '/tournaments'
    || this.router.url.includes('/team/') || this.router.url.includes('/league/') || this.router.url.includes('/tournament/');
  }
}
