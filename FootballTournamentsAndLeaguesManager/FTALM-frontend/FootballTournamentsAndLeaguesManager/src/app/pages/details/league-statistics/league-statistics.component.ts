import { Component, ViewChild } from '@angular/core';
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
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-league-statistics',
  templateUrl: './league-statistics.component.html',
  styleUrls: ['./league-statistics.component.css']
})
export class LeagueStatisticsComponent {
  leagueId!: number;
  playersGoalsDataSource: MatTableDataSource<Player> = new MatTableDataSource();
  playersAssistsDataSource: MatTableDataSource<Player> = new MatTableDataSource();
  playersCleanSheetsDataSource: MatTableDataSource<Player> = new MatTableDataSource();
  playersYellowCardsDataSource: MatTableDataSource<Player> = new MatTableDataSource();
  playersRedCardsDataSource: MatTableDataSource<Player> = new MatTableDataSource();

  teamsGoalsDataSource: MatTableDataSource<LeagueStanding> = new MatTableDataSource();
  teamsWinsDataSource: MatTableDataSource<LeagueStanding> = new MatTableDataSource();
  teamsLossesDataSource: MatTableDataSource<LeagueStanding> = new MatTableDataSource();

  teamsGoalsDisplayedColumns = ['rank', 'team', 'goals'];
  teamsWinsDisplayedColumns = ['rank', 'team', 'wins'];
  teamsLossesDisplayedColumns = ['rank', 'team', 'losses'];

  playersGoalsDisplayedColumns = ['rank', 'player', 'team', 'goals'];
  playersAssistsDisplayedColumns = ['rank', 'player', 'team', 'assists'];
  playersCleanSheetsDisplayedColumns = ['rank', 'player', 'team', 'cleanSheets'];
  playersYellowCardsDisplayedColumns = ['rank', 'player', 'team', 'yellowCards'];
  playersRedCardsDisplayedColumns = ['rank', 'player', 'team', 'redCards'];

  @ViewChild('paginatorPlayersGoals', { static: true }) paginatorPlayersGoals: MatPaginator | null = null;
  @ViewChild('paginatorPlayersAssists', { static: true }) paginatorPlayersAssists: MatPaginator | null = null;
  @ViewChild('paginatorPlayersYellowCards', { static: true }) paginatorPlayersYellowCards: MatPaginator | null = null;
  @ViewChild('paginatorPlayersRedCards', { static: true }) paginatorPlayersRedCards: MatPaginator | null = null;
  @ViewChild('paginatorTeamsGoals', { static: true }) paginatorTeamsGoals: MatPaginator | null = null;
  @ViewChild('paginatorTeamsWins', { static: true }) paginatorTeamsWins: MatPaginator | null = null;
  @ViewChild('paginatorTeamsLosses', { static: true }) paginatorTeamsLosses: MatPaginator | null = null;

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

    this.fetchTeamsGoalsDataSource(this.leagueId);
    this.fetchTeamsWinsDataSource(this.leagueId);
    this.fetchTeamsLossesDataSource(this.leagueId);
  }

  ngAfterViewInit() {
    this.playersGoalsDataSource.paginator = this.paginatorPlayersGoals;
    this.playersAssistsDataSource.paginator = this.paginatorPlayersAssists;
    this.playersYellowCardsDataSource.paginator = this.paginatorPlayersYellowCards;
    this.playersRedCardsDataSource.paginator = this.paginatorPlayersRedCards;
    this.teamsGoalsDataSource.paginator = this.paginatorTeamsGoals;
    this.teamsWinsDataSource.paginator = this.paginatorTeamsWins;
    this.teamsLossesDataSource.paginator = this.paginatorTeamsWins;
  }

  fetchPlayersGoalsData(leagueId: number): void {
    this.goalAssistService.getPlayersGoalsByLeagueId(leagueId).subscribe((playerGoals: PlayerGoals[]) => {
      this.playersGoalsDataSource.data = [...playerGoals];
    });
  }

  fetchPlayersAssistsData(leagueId: number): void {
    this.goalAssistService.getPlayersAssistsByLeagueId(leagueId).subscribe((playerAssists: PlayerAssists[]) => {
      this.playersAssistsDataSource.data = [...playerAssists];
    });
  }

  fetchPlayersYellowCardsData(leagueId: number): void {
    this.cardService.getPlayersYellowCardsByLeagueId(leagueId).subscribe((playersYellowCards: PlayerCards[]) => {
      this.playersYellowCardsDataSource.data = [...playersYellowCards].filter(player => player.yellowCards > 0);
    });
  }

  fetchPlayersRedCardsData(leagueId: number): void {
    this.cardService.getPlayersRedCardsByLeagueId(leagueId).subscribe((playerRedCards: PlayerCards[]) => {
      this.playersRedCardsDataSource.data = [...playerRedCards].filter(player => player.redCards > 0);
    });
  }

  fetchTeamsGoalsDataSource(leagueId: number): void {
    this.leagueStandingService.getLeagueStandingsByLeagueIdOrderByGoalsFor(leagueId).subscribe((leagueStanding: LeagueStanding[]) => {
      this.teamsGoalsDataSource.data = [...leagueStanding];
    });    
  }

  fetchTeamsWinsDataSource(leagueId: number): void {
    this.leagueStandingService.getLeagueStandingsByLeagueIdOrderByWins(leagueId).subscribe((leagueStandings: LeagueStanding[]) => {
      this.teamsWinsDataSource.data = [...leagueStandings];
    });
  }

  fetchTeamsLossesDataSource(leagueId: number): void {
    this.leagueStandingService.getLeagueStandingsByLeagueIdOrderByLosses(leagueId).subscribe((leagueStandings: LeagueStanding[]) => {
      this.teamsLossesDataSource.data = [...leagueStandings];
    });
  }

}
