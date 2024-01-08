import { Component } from '@angular/core';
import { Player } from 'src/app/models/Player/player';
import { Team } from 'src/app/models/Team/team';
import { GoalAssistService } from '../../../services/goalAssistService/goal-assist.service';
import { CardService } from 'src/app/services/cardService/card.service';
import { ActivatedRoute } from '@angular/router';
import { PlayerGoals } from 'src/app/models/PlayerGoals/playerGoals';
import { PlayerAssists } from 'src/app/models/PlayerAssists/playerAssists';
import { PlayerCards } from 'src/app/models/PlayerCards/playerCards';
import { LeagueStanding } from 'src/app/models/LeagueStanding/leagueStanding';
import { LeagueStandingService } from 'src/app/services/leagueStandingService/league-standing.service';

@Component({
  selector: 'app-league-statistics',
  templateUrl: './league-statistics.component.html',
  styleUrls: ['./league-statistics.component.css']
})
export class LeagueStatisticsComponent {
  leagueId!: number;
  playersGoalsDataSource: Player[] = [];
  playersAssistsDataSource: Player[] = [];
  playersCleanSheetsDataSource: Player[] = [];
  playersYellowCardsDataSource: Player[] = [];
  playersRedCardsDataSource: Player[] = [];

  teamsGoalsDataSource: LeagueStanding[] = [];
  teamsWinsDataSource: LeagueStanding[] = [];
  teamsLossesDataSource: LeagueStanding[] = [];
  teamsGoalsDisplayedColumns = ['rank', 'team', 'goals'];
  teamsWinsDisplayedColumns = ['rank', 'team', 'wins'];
  teamsLossesDisplayedColumns = ['rank', 'team', 'losses'];

  playersGoalsDisplayedColumns = ['rank', 'player', 'team', 'goals'];
  playersAssistsDisplayedColumns = ['rank', 'player', 'team', 'assists'];
  playersCleanSheetsDisplayedColumns = ['rank', 'player', 'team', 'cleanSheets'];
  playersYellowCardsDisplayedColumns = ['rank', 'player', 'team', 'yellowCards'];
  playersRedCardsDisplayedColumns = ['rank', 'player', 'team', 'redCards'];

  constructor(private route: ActivatedRoute, private goalAssistService: GoalAssistService, 
    private cardService: CardService, private leagueStandingService: LeagueStandingService) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.leagueId = params['leagueId'];
    });

    this.fetchPlayersGoalsData(this.leagueId);
    this.fetchPlayersAssistsData(this.leagueId);
    this.fetchPlayersYellowCardsData(this.leagueId);
    this.fetchPlayersRedCardsData(this.leagueId);

  }

  fetchPlayersGoalsData(leagueId: number): void {
    this.goalAssistService.getPlayersGoalsByLeagueId(leagueId).subscribe((playerGoals: PlayerGoals[]) => {
      this.playersGoalsDataSource = [...playerGoals];
    });
  }

  fetchPlayersAssistsData(leagueId: number): void {
    this.goalAssistService.getPlayersAssistsByLeagueId(leagueId).subscribe((playerAssists: PlayerAssists[]) => {
      this.playersAssistsDataSource = [...playerAssists];
    });
  }

  fetchPlayersYellowCardsData(leagueId: number): void {
    this.cardService.getPlayersYellowCardsByLeagueId(leagueId).subscribe((playersYellowCards: PlayerCards[]) => {
      this.playersYellowCardsDataSource = [...playersYellowCards].filter(player => player.yellowCards > 0);
    });
  }

  fetchPlayersRedCardsData(leagueId: number): void {
    this.cardService.getPlayersRedCardsByLeagueId(leagueId).subscribe((playerRedCards: PlayerCards[]) => {
      this.playersRedCardsDataSource = [...playerRedCards].filter(player => player.redCards > 0);
    });
  }

  fetchTeamsGoalsDataSource(leagueId: number): void {
    this.leagueStandingService.getLeagueStandingsByLeagueIdOrderByGoalsFor(leagueId).subscribe((leagueStanding: LeagueStanding[]) => {
      this.teamsGoalsDataSource = [...leagueStanding];
    });    
  }

  fetchTeamsWinsDataSource(leagueId: number): void {
    this.leagueStandingService.getLeagueStandingsByLeagueIdOrderByWins(leagueId).subscribe((leagueStandings: LeagueStanding[]) => {
      this.teamsWinsDataSource = [...leagueStandings];
    });
  }

  fetchTeamsLossesDataSource(leagueId: number): void {
    this.leagueStandingService.getLeagueStandingsByLeagueIdOrderByLosses(leagueId).subscribe((leagueStandings: LeagueStanding[]) => {
      this.teamsLossesDataSource = [...leagueStandings];
    });
  }

}
