import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Player } from 'src/app/models/Player/player';
import { PlayerAssists } from 'src/app/models/PlayerAssists/playerAssists';
import { PlayerCards } from 'src/app/models/PlayerCards/playerCards';
import { PlayerGoals } from 'src/app/models/PlayerGoals/playerGoals';
import { CardService } from 'src/app/services/cardService/card.service';
import { GoalAssistService } from 'src/app/services/goalAssistService/goal-assist.service';

@Component({
  selector: 'app-tournament-statistics',
  templateUrl: './tournament-statistics.component.html',
  styleUrls: ['./tournament-statistics.component.css']
})
export class TournamentStatisticsComponent {
  tournamentId!: number;
  playersGoalsDataSource: Player[] = [];
  playersAssistsDataSource: Player[] = [];
  playersCleanSheetsDataSource: Player[] = [];
  playersYellowCardsDataSource: Player[] = [];
  playersRedCardsDataSource: Player[] = [];

  playersGoalsDisplayedColumns = ['rank', 'player', 'team', 'goals'];
  playersAssistsDisplayedColumns = ['rank', 'player', 'team', 'assists'];
  playersCleanSheetsDisplayedColumns = ['rank', 'player', 'team', 'cleanSheets'];
  playersYellowCardsDisplayedColumns = ['rank', 'player', 'team', 'yellowCards'];
  playersRedCardsDisplayedColumns = ['rank', 'player', 'team', 'redCards'];

  constructor(private route: ActivatedRoute, private goalAssistService: GoalAssistService, 
    private cardService: CardService) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.tournamentId = params['id'];
    });

    this.fetchPlayersGoalsData(this.tournamentId);
    this.fetchPlayersAssistsData(this.tournamentId);
    this.fetchPlayersYellowCardsData(this.tournamentId);
    this.fetchPlayersRedCardsData(this.tournamentId);

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
}
