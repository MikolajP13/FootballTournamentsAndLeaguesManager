import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { TournamentLeagueBase } from 'src/app/models/TournamentLeagueBase/tournamentLeagueBase';
import { User } from 'src/app/models/User/user';
import { LeagueService } from 'src/app/services/leagueService/league.service';
import { UserService } from 'src/app/services/userService/user.service';

@Component({
  selector: 'app-leagues',
  templateUrl: './leagues.component.html',
  styleUrls: ['./leagues.component.css']
})
export class LeaguesComponent {
  authUser!: User;

  displayedColumns: string[] = ['leagueName', 'numberOfTeams', 'startDate', 'status', 'details'];
  leagueDataSource:TournamentLeagueBase[] = [];

  constructor(private router: Router, private leagueService: LeagueService) { }

  ngOnInit() {
    this.authUser = UserService.getUser();
    if(this.authUser.id !== undefined)
      this.leagueService.findAllLeaguesByUserId(this.authUser.id).subscribe((leagues: TournamentLeagueBase[]) => {
        this.leagueDataSource = [...leagues];
      });
  }

  showLeagueDeatils(league: TournamentLeagueBase): void {
    this.router.navigate(['/league/' + league.id]);
  }
}
