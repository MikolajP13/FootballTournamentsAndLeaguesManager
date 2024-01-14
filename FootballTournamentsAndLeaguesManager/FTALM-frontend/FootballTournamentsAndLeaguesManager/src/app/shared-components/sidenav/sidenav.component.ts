import { Component, ViewChild } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { ActivatedRoute, ActivationEnd, Router } from '@angular/router';
import { filter, map } from 'rxjs';
import { SidenavService } from 'src/app/services/sidenavService/sidenav.service';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent {
  @ViewChild('sidenav', { static: true }) sidenav!: MatSidenav;
  id!: number;
  showMatchesSublist: boolean = false;

  constructor(private sidenavService: SidenavService, private router: Router, private route: ActivatedRoute) {}

  ngOnInit() {
    // Getting params of a route from outside of a router-outlet
    this.router.events.pipe(
      filter(e => (e instanceof ActivationEnd) && (Object.keys(e.snapshot.params).length > 0)),
      map(e => e instanceof ActivationEnd ? e.snapshot.params : {})
    ).subscribe(params => {
      if (params['leagueId'] !== undefined)
        this.id = params['leagueId'];
      else if (params['tournamentId'] !== undefined)
        this.id = params['tournamentId'];
      else if (params['teamId'] !== undefined)
        this.id = params['teamId'];
    });
  }

  ngAfterViewInit(): void {
    this.sidenavService.setSidenav(this.sidenav);
  }

  //display conditions
  isTeamDetailPage(): boolean {
    return this.router.url.includes('/team/');
  }
  
  isCompetitionDetailPage(): boolean {
    return this.router.url.includes('/league/') || this.router.url.includes('/tournament/');
  }

  isLeagueDetailPage(): boolean {
    return this.router.url.includes('/league/');
  }

  toggleTeamMatchesSublist() {
    this.showMatchesSublist = this.sidenavService.toggleMatchesSublist();
  }

  //navigation to the sub team pages
  showTeamData(){
    this.router.navigate([`/team/${this.id}`]);
  }

  showTeamPlayedMatches(){
    this.router.navigate([`/team/${this.id}/matches`]);
  }

  showTeamUpcomingMatches(){
    this.router.navigate([`/team/${this.id}/matches`]);
  }

  showTeamPlayers(){
    this.router.navigate([`/team/${this.id}/players`]);
  }

  showTeamStatistics(){
    this.router.navigate([`/team/${this.id}/statistics`]);
  }

  showTeamAbout(){
    this.router.navigate([`/team/${this.id}/about`]);
  }

  //navigation to the sub league/tournament pages
  showCompetitionTeams(){
    this.router.url.includes('/league/') ? this.router.navigate([`/league/${this.id}/teams`]) : this.router.navigate([`/tournament/${this.id}/teams`]);
  }

  showLeagueTable(){
    this.router.navigate([`/league/${this.id}/table`]);
  }

  showTournamentBracket(){
    this.router.navigate([`/tournament/${this.id}/bracket`]);
  }

  showCompetitionMatches(){
    this.router.url.includes('/league/') ? this.router.navigate([`/league/${this.id}/matches`]) : this.router.navigate([`/tournament/${this.id}/matches`]);
  }

  showCompetitionStatistics(){
    this.router.url.includes('/league/') ? this.router.navigate([`/league/${this.id}/statistics`]) : this.router.navigate([`/tournament/${this.id}/statistics`]);
  }

  showCompetitionAbout(){
    this.router.url.includes('/league/') ? this.router.navigate([`/league/${this.id}/about`]) : this.router.navigate([`/tournament/${this.id}/about`]);
  }
}
