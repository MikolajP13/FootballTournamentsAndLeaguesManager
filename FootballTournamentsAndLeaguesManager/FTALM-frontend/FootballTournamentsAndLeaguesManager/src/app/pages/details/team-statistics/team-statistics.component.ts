import { Component } from '@angular/core';
import { Team } from 'src/app/models/Team/team';

@Component({
  selector: 'app-team-statistics',
  templateUrl: './team-statistics.component.html',
  styleUrls: ['./team-statistics.component.css']
})
export class TeamStatisticsComponent {
  teamLeaguesStatisticsDataSource: Team[] = [];
  teamTournamentsStatisticsDataSource: Team[] = [];
  displayedColumnsForLeagues: string[] = ['league', 'win', 'draw', 'loss', 'goals', 'goal difference', 'points', 'rank'];
  displayedColumnsForTournaments: string[] = ['tournament', 'win', 'draw', 'loss', 'goals', 'goal difference', 'group points', 'round'];
}
