import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CompetitionType, TournamentLeagueBase } from 'src/app/models/TournamentLeagueBase/tournamentLeagueBase';
import { User } from 'src/app/models/User/user';
import { LeagueService } from 'src/app/services/leagueService/league.service';
import { TournamentService } from '../../services/tournamentService/tournament.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  authUser!: User;


  displayedColumns: string[] = ['competitionName', 'numberOfTeams', 'startDate', 'status', 'details'];
  dataSource:TournamentLeagueBase[] = [];

  constructor(private router: Router, private leagueService: LeagueService, private tournamentService: TournamentService){ }

  ngOnInit() {
    this.getUser();
    if(this.authUser.id !== undefined){
      this.leagueService.findAllLeaguesByUserId(this.authUser.id).subscribe((leagues: TournamentLeagueBase[]) => {
        leagues.forEach(league => league.competitionType = CompetitionType.LEAGUE);
        this.dataSource.push(...leagues);
      });

      this.tournamentService.findAllTournamentsByUserId(this.authUser.id).subscribe((tournaments: TournamentLeagueBase[]) => {
        tournaments.forEach(tournament => tournament.competitionType = CompetitionType.TOURNAMENT);
        this.dataSource = [...this.dataSource, ...tournaments];
      });
    }
  }

  showCompetitionDetails(row: TournamentLeagueBase): void {
    console.log(row.id + ' ' + row.competitionType);
  }

  getUser() {
    const user = sessionStorage.getItem('user');
    
    if(user){
      this.authUser = JSON.parse(user);
    }
  }

}
