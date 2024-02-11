import { Component, ViewChild } from '@angular/core';
import { TournamentStandingService } from '../../../services/tournamentStandingService/tournament-standing.service';
import { LeagueStandingService } from '../../../services/leagueStandingService/league-standing.service';
import { ActivatedRoute } from '@angular/router';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { LeagueStanding } from 'src/app/models/LeagueStanding/leagueStanding';
import { TournamentStanding } from 'src/app/models/TournamentStanding/tournamentStanding';

@Component({
  selector: 'app-team-statistics',
  templateUrl: './team-statistics.component.html',
  styleUrls: ['./team-statistics.component.css']
})
export class TeamStatisticsComponent {
  teamId!: number;
  teamLeaguesStatisticsDataSource: MatTableDataSource<LeagueStanding> = new MatTableDataSource();
  teamTournamentsStatisticsDataSource: MatTableDataSource<TournamentStanding> = new MatTableDataSource();
  displayedColumnsForLeagues: string[] = ['league', 'playedMatches', 'win', 'draw', 'loss', 'goalsForAgainst', 'goalDifference', 'points', 'rank'];
  displayedColumnsForTournaments: string[] = ['tournament', 'playedMatches', 'win', 'draw', 'loss', 'goalsForAgainst', 'goalDifference', 'groupPoints', 'round'];

  @ViewChild('leagueStatisticsPaginator', { static: true }) leagueStatisticsPaginator: MatPaginator | null = null;
  @ViewChild('tournamentStatisticsPaginator', { static: true }) tournamentStatisticsPaginator: MatPaginator | null = null;

  constructor(private route: ActivatedRoute, private tournamentStandingService: TournamentStandingService, private leagueStandingService: LeagueStandingService) { }

  ngOnInit() { 
    this.route.params.subscribe(params => {
      this.teamId = params['teamId'];
    });
    
    // console.log(this.route.snapshot.paramMap.get('teamId'));

    this.fetchTeamLeagueStatistics(this.teamId);
    this.fetchTeamTournamentStatistics(this.teamId);
  }

  ngAfterViewInit() {
    this.teamLeaguesStatisticsDataSource.paginator = this.leagueStatisticsPaginator;
    this.teamTournamentsStatisticsDataSource.paginator = this.tournamentStatisticsPaginator;
  }

  fetchTeamLeagueStatistics(teamId: number) {
    this.leagueStandingService.getAllByTeamId(teamId).subscribe(data => {
      this.teamLeaguesStatisticsDataSource.data = data;
    });
  }

  fetchTeamTournamentStatistics(teamId: number) {
    this.tournamentStandingService.getAllByTeamId(teamId).subscribe(data => {
      this.teamTournamentsStatisticsDataSource.data = data;
    });
  }

}
