import { Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
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
  playersGoalsDataSource: MatTableDataSource<Player> = new MatTableDataSource();
  playersAssistsDataSource: MatTableDataSource<Player> = new MatTableDataSource();
  playersCleanSheetsDataSource: MatTableDataSource<Player> = new MatTableDataSource();
  playersYellowCardsDataSource: MatTableDataSource<Player> = new MatTableDataSource();
  playersRedCardsDataSource: MatTableDataSource<Player> = new MatTableDataSource();

  playersGoalsDisplayedColumns = ['rank', 'player', 'team', 'goals'];
  playersAssistsDisplayedColumns = ['rank', 'player', 'team', 'assists'];
  playersCleanSheetsDisplayedColumns = ['rank', 'player', 'team', 'cleanSheets'];
  playersYellowCardsDisplayedColumns = ['rank', 'player', 'team', 'yellowCards'];
  playersRedCardsDisplayedColumns = ['rank', 'player', 'team', 'redCards'];

  @ViewChild('paginatorPlayersGoals', { static: true }) paginatorPlayersGoals: MatPaginator | null = null;
  @ViewChild('paginatorPlayersAssists', { static: true }) paginatorPlayersAssists: MatPaginator | null = null;
  @ViewChild('paginatorPlayersYellowCards', { static: true }) paginatorPlayersYellowCards: MatPaginator | null = null;
  @ViewChild('paginatorPlayersRedCards', { static: true }) paginatorPlayersRedCards: MatPaginator | null = null;

  constructor(private route: ActivatedRoute, private goalAssistService: GoalAssistService, 
    private cardService: CardService) { }

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.tournamentId = params['tournamentId'];
    });

    this.fetchPlayersGoalsData(this.tournamentId);
    this.fetchPlayersAssistsData(this.tournamentId);
    this.fetchPlayersYellowCardsData(this.tournamentId);
    this.fetchPlayersRedCardsData(this.tournamentId);

  }

  ngAfterViewInit() {
    this.playersGoalsDataSource.paginator = this.paginatorPlayersGoals;
    this.playersAssistsDataSource.paginator = this.paginatorPlayersAssists;
    this.playersYellowCardsDataSource.paginator = this.paginatorPlayersYellowCards;
    this.playersRedCardsDataSource.paginator = this.paginatorPlayersRedCards;
  }

  fetchPlayersGoalsData(tournamentId: number): void {
    this.goalAssistService.getPlayersGoalsByTournamentId(tournamentId).subscribe((playerGoals: PlayerGoals[]) => {
      this.playersGoalsDataSource.data = [...playerGoals];
    });
  }

  fetchPlayersAssistsData(leagueId: number): void {
    this.goalAssistService.getPlayersAssistsByTournamentId(leagueId).subscribe((playerAssists: PlayerAssists[]) => {
      this.playersAssistsDataSource.data = [...playerAssists];
    });
  }

  fetchPlayersYellowCardsData(leagueId: number): void {
    this.cardService.getPlayersYellowCardsByTournamentId(leagueId).subscribe((playersYellowCards: PlayerCards[]) => {
      this.playersYellowCardsDataSource.data = [...playersYellowCards].filter(player => player.yellowCards > 0);
    });
  }

  fetchPlayersRedCardsData(leagueId: number): void {
    this.cardService.getPlayersRedCardsByTournamentId(leagueId).subscribe((playerRedCards: PlayerCards[]) => {
      this.playersRedCardsDataSource.data = [...playerRedCards].filter(player => player.redCards > 0);
    });
  }
}
