import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CompetitionType, Status, TournamentLeagueBase } from 'src/app/models/TournamentLeagueBase/tournamentLeagueBase';
import { User } from 'src/app/models/User/user';
import { LeagueService } from 'src/app/services/leagueService/league.service';
import { TournamentService } from '../../services/tournamentService/tournament.service';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
  authUser!: User;
  notStarted: Status = Status.NOT_STARTED;
  inProgress: Status = Status.IN_PROGRESS;
  finished: Status = Status.FINISHED;
  standard: string = 'STANDARD_MODE';
  split: string = 'SPLIT_MODE';
  groupAndKnockout: string = 'GROUP_AND_KNOCKOUT';
  singleElimination: string = 'SINGLE_ELIMINATION';

  displayedColumns: string[] = ['competitionName', 'competitionType', 'numberOfTeams', 'startDate', 'endDate', 'status', 'details'];
  dataSource:TournamentLeagueBase[] = [];

  constructor(private router: Router, private leagueService: LeagueService, private tournamentService: TournamentService){ }

  ngOnInit() {
    this.getUser();
    if(this.authUser.id !== undefined){
      const leagueObservable = this.leagueService.findAllLeaguesByUserId(this.authUser.id);
      const tournamentObservable = this.tournamentService.findAllTournamentsByUserId(this.authUser.id);

      //forkJoin operator from rjxs library wait for async requests to complete and then process data
      forkJoin([leagueObservable, tournamentObservable]).subscribe(([leagues, tournaments]) => {
        leagues.forEach(league => league.competitionType = CompetitionType.LEAGUE);
        tournaments.forEach(tournament => tournament.competitionType = CompetitionType.TOURNAMENT);
        this.dataSource = [...this.dataSource, ...leagues, ...tournaments];
      });
    }
  }

  showCompetitionDetails(competition: TournamentLeagueBase): void {
    if(competition.competitionType === CompetitionType.LEAGUE) {
      this.router.navigate(['/league/' + competition.id + '/teams']);
    }else if(competition.competitionType === CompetitionType.TOURNAMENT) {
      this.router.navigate(['/tournament/' + competition.id + '/teams']);
    }
  }

  getUser() {
    const user = sessionStorage.getItem('user');
    
    if(user){
      this.authUser = JSON.parse(user);
    }
  }

}
