import { Component, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Router } from '@angular/router';
import { SidenavService } from 'src/app/services/sidenavService/sidenav.service';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent {
  @ViewChild('sidenav', { static: true }) sidenav!: MatSidenav;

  constructor(private sidenavService: SidenavService, private router: Router) {}

  ngAfterViewInit(): void {
    this.sidenavService.setSidenav(this.sidenav);
  }

  isTeamDetailPage(): boolean {
    return this.router.url.includes('/team/');
  }
  
  isCompetitionDetailPage(): boolean {
    return this.router.url.includes('/league/') || this.router.url.includes('/tournament/');
  }

  isLeagueDetailPage(): boolean {
    return this.router.url.includes('/league/');
  }
}
