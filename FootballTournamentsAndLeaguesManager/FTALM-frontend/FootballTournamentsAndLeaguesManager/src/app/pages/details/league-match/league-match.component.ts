import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { GoalAssist } from 'src/app/models/GoalAssist/goalAssist';
import { Match } from 'src/app/models/Match/match';
import { Player } from 'src/app/models/Player/player';
import { MatchService } from 'src/app/services/matchService/match.service';
import { PlayerService } from 'src/app/services/playerService/player.service';
import { Card, CardType } from 'src/app/models/Card/card';
import { forkJoin } from 'rxjs';
import { SnackBarComponent } from 'src/app/shared-components/snack-bar/snack-bar.component';
import { Substitution } from '../../../models/Substitution/substitution';
import { GoalAssistService } from 'src/app/services/goalAssistService/goal-assist.service';
import { CardService } from 'src/app/services/cardService/card.service';
import { SubstitutionService } from '../../../services/substitutionService/substitution.service';
import { MatchWeekNumberService } from '../../../services/matchweekService/match-week-number.service';
import { LeagueStandingService } from 'src/app/services/leagueStandingService/league-standing.service';
import { LeagueStanding } from 'src/app/models/LeagueStanding/leagueStanding';

@Component({
  selector: 'app-league-match',
  templateUrl: './league-match.component.html',
  styleUrls: ['./league-match.component.css']
})
export class LeagueMatchComponent {
  GOAL_ASSIST_EVENT_ID: string = 'G';
  CARD_EVENT_ID: string = 'C';
  SUBSTITUTION_EVENT_ID: string = 'S';
  matchId!: number;
  leagueId!: number;
  match?: Match;
  events: any[] = [];
  eventTypes: string[] = ['Goal', 'Card', 'Substitution'];
  goalTypes: string[] = ['Normal', 'Penalty kick', 'Free kick'];
  selectedGoalType: string = 'Normal';
  selectedGoalType2: string = 'Normal';
  selectedEventType?: string;
  selectedEventType2?: string;
  homeTeamPlayers: Player[] = [];
  awayTeamPlayers: Player[] = [];
  firstPlayer?: Player;
  secondPlayer?: Player;  
  firstPlayer2?: Player;
  secondPlayer2?: Player;
  minute?: number;
  minute2?: number;
  card?: Card;
  card2?: Card;
  cardTypes: CardType[] = [CardType.YELLOW, CardType.RED];
  homeTeamScore: number = 0;
  awayTeamScore: number = 0;

  constructor(private route: ActivatedRoute, private matchService: MatchService, private playerService: PlayerService, 
    private goalAssistService: GoalAssistService, private cardService: CardService, private substitutionService: SubstitutionService, 
    private leagueStandingService: LeagueStandingService, private snackBar: SnackBarComponent,
    private matchWeekNumberService: MatchWeekNumberService, private router: Router) {}

  ngOnInit(): void { 
    this.route.params.subscribe(params => {
      this.matchId = params['id'];
      this.leagueId = params['leagueId'];
    });

    this.matchService.getMatchByMatchId(this.matchId).subscribe(match =>{
      this.match = match;
      if(this.match.homeTeamId && this.match.awayTeamId){
        const homeTeamPlayersObservable = this.playerService.getAllPlayersByTeamId(this.match.homeTeamId);
        const awayTeamPlayersObservable = this.playerService.getAllPlayersByTeamId(this.match.awayTeamId);
        
        forkJoin({
          homeTeamPlayers: homeTeamPlayersObservable,
          awayTeamPlayers: awayTeamPlayersObservable
        }).subscribe(result => {
          this.homeTeamPlayers = result.homeTeamPlayers;
          this.awayTeamPlayers = result.awayTeamPlayers;
        });
      }
    });
  }

  addToMatchEventList(homeTeamEvent: boolean, teamId?: number, teamName?: string): void {
    var selectedEventType = homeTeamEvent ? this.selectedEventType : this.selectedEventType2;
    var firstPlayer = homeTeamEvent ? this.firstPlayer : this.firstPlayer2;
    var secondPlayer = homeTeamEvent ? this.secondPlayer : this.secondPlayer2;
    var minute = homeTeamEvent ? this.minute : this.minute2;
    var playerCard = homeTeamEvent ? this.card : this.card2;
    var selectedGoalType = homeTeamEvent ? this.selectedGoalType : this.selectedGoalType2;
    
    if(selectedEventType === 'Goal' && selectedGoalType !== 'Normal')
      secondPlayer = undefined;

    if(selectedEventType === 'Goal') {
      var goalAssist: GoalAssist = {
        scorerPlayer: firstPlayer,
        assistPlayerId: secondPlayer === undefined ? null : secondPlayer?.id,
        match: {
          id: this.matchId 
        },
        team: {
          id: teamId
        },
        teamName: teamName,
        minute: minute,
        type: this.GOAL_ASSIST_EVENT_ID
      }

      this.events.push(goalAssist);
      teamName === this.match?.homeTeamName ? this.homeTeamScore += 1 : this.awayTeamScore += 1;

    }else if(selectedEventType === 'Card') {
      var possiblyRedCard = this.events.filter(e => e.type === this.CARD_EVENT_ID 
        && e.player.id === firstPlayer?.id 
        && e.cardType === 0);

      var card: Card = {
        player: firstPlayer,
        // 0 is yellow card, 1 is red card
        cardType: possiblyRedCard.length === 1 ? 1 as unknown as CardType : playerCard as CardType,
        match: {
          id: this.matchId 
        },
        team: {
          id: teamId
        },
        teamName: teamName,
        minute: minute,
        type: this.CARD_EVENT_ID
      }

      this.events.push(card);

    }else if(selectedEventType === 'Substitution') {
      var substitution: Substitution = {
        enteringPlayer: firstPlayer,
        exitingPlayer: secondPlayer,
        match: {
          id: this.matchId 
        },
        team: {
          id: teamId
        },
        teamName: teamName,
        minute: minute,
        type: this.SUBSTITUTION_EVENT_ID
      }

      this.events.push(substitution);
    }

    this.snackBar.trigger('Match event has been added!', '');
    homeTeamEvent ? this.clearSelectedValues(true) : this.clearSelectedValues(false);
    this.events.sort((e1, e2) => e1.minute - e2.minute);
  }

  clearSelectedValues(clearHomeTeamEvent: boolean): void {
    clearHomeTeamEvent ? (this.selectedEventType = undefined,
      this.firstPlayer = undefined,
      this.secondPlayer = undefined,
      this.card = undefined,
      this.minute = undefined,
      this.selectedGoalType = 'Normal') : 
      (this.selectedEventType2 = undefined,
      this.firstPlayer2 = undefined,
      this.secondPlayer2 = undefined,
      this.card2 = undefined,
      this.minute2 = undefined,
      this.selectedGoalType2 = 'Normal')
  }

  removeEventFromList(index: number): void {
    var eventToDelete = this.events.at(index);

    if(eventToDelete.type === this.GOAL_ASSIST_EVENT_ID && eventToDelete.teamName === this.match?.homeTeamName)
      this.homeTeamScore -= 1;
    else if(eventToDelete.type === this.GOAL_ASSIST_EVENT_ID && eventToDelete.teamName === this.match?.awayTeamName)
      this.awayTeamScore -= 1;

    this.events.splice(index, 1);
  }

  saveProtocol(): void {
    var updatedMatchData: Match = {
      homeTeamScore: this.homeTeamScore,
      awayTeamScore: this.awayTeamScore,
      matchProtocolCreated: false, // TEMP!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
      date: new Date(Date.now())
    }
    var goalAssistEvents = this.events.filter(e => e.type === this.GOAL_ASSIST_EVENT_ID);
    var cardEvents = this.events.filter(e => e.type === this.CARD_EVENT_ID);
    var substitutionEvents = this.events.filter(e => e.type === this.SUBSTITUTION_EVENT_ID);

    if(goalAssistEvents.length > 0) {
      this.goalAssistService.addGoalAssists(goalAssistEvents).subscribe();
    }

    if(cardEvents.length > 0) {
      this.cardService.addCards(cardEvents).subscribe();
    }

    if(substitutionEvents.length > 0) {
      this.substitutionService.addSubstitutions(substitutionEvents).subscribe();
    }

    if(this.match?.id) {
      this.matchService.updateMatch(this.match.id, updatedMatchData).subscribe();

      if(this.leagueId && this.match?.homeTeamId && this.match?.awayTeamId) {
        var homeTeamWin: number = 0; 
        var awayTeamWin: number = 0; 
        var draw: number = 0; 
        var homeTeamLose: number = 0; 
        var awayTeamLose: number = 0;
        let goalBalanceForHomeTeam: number = this.homeTeamScore - this.awayTeamScore;

        if(goalBalanceForHomeTeam > 0){
          homeTeamWin = 1;
          awayTeamLose = 1;
        } else if(goalBalanceForHomeTeam == 0){
          draw = 1;
        } else {
          awayTeamWin = 1;
          homeTeamLose = 1;
        }
        
        var leagueStandingForHomeTeam: LeagueStanding = {
          goalsFor: this.homeTeamScore,
          goalsAgainst: this.awayTeamScore,
          wins: homeTeamWin,
          draws: draw,
          losses: homeTeamLose
        }

        var leagueStandingForAwayTeam: LeagueStanding = {
          goalsFor: this.awayTeamScore,
          goalsAgainst: this.homeTeamScore,
          wins: awayTeamWin,
          draws: draw,
          losses: awayTeamLose
        }

        this.leagueStandingService.updateLeagueStanding(this.leagueId, this.match.homeTeamId, leagueStandingForHomeTeam).subscribe();
        this.leagueStandingService.updateLeagueStanding(this.leagueId, this.match.awayTeamId, leagueStandingForAwayTeam).subscribe();
      }
    }

    if(this.match?.matchweek) {
      this.router.navigate([`/league/${this.leagueId}/matches/`])
      this.matchWeekNumberService.matchWeekNumber = this.match?.matchweek;
    }
    
  }

  isEventValid(homeTeamEvent: boolean): boolean {
    var selectedEventType = homeTeamEvent ? this.selectedEventType : this.selectedEventType2;
    var firstPlayer = homeTeamEvent ? this.firstPlayer : this.firstPlayer2;
    var selectedGoalType = homeTeamEvent ? this.selectedGoalType : this.selectedGoalType2;
    var secondPlayer = homeTeamEvent ? this.secondPlayer : this.secondPlayer2;
    var minute = homeTeamEvent ? this.minute : this.minute2;
    var playerCard = homeTeamEvent ? this.card : this.card2;
    
    var isMinuteValid = minute !== undefined && minute >= 1 && minute <= 120;

    if(selectedEventType === 'Card')
      return firstPlayer !== undefined && playerCard !== undefined && !this.checkIfPlayerOffThePitch(firstPlayer) && isMinuteValid
    else if(selectedEventType === 'Goal')
      return selectedGoalType === 'Normal' ? this.arePlayersSelected(homeTeamEvent) 
        && !this.checkIfPlayerOffThePitch(firstPlayer) && !this.checkIfPlayerOffThePitch(secondPlayer)
        && !this.checkFirstAndSecondPlayer(homeTeamEvent) && isMinuteValid 
        :
        firstPlayer !== undefined && !this.checkIfPlayerOffThePitch(firstPlayer) && isMinuteValid;
    else
      return this.arePlayersSelected(homeTeamEvent) 
        && !this.checkIfPlayerOffThePitch(firstPlayer) && !this.checkIfPlayerOffThePitch(secondPlayer)
        && !this.checkFirstAndSecondPlayer(homeTeamEvent) && isMinuteValid 
  }

  arePlayersSelected(homeTeamEvent: boolean): boolean {
    var firstPlayer = homeTeamEvent ? this.firstPlayer : this.firstPlayer2;
    var secondPlayer = homeTeamEvent ? this.secondPlayer : this.secondPlayer2;
    return firstPlayer !== undefined && secondPlayer !== undefined;
  }

  checkFirstAndSecondPlayer(homeTeamEvent: boolean): boolean {
    var firstPlayer = homeTeamEvent ? this.firstPlayer : this.firstPlayer2;
    var secondPlayer = homeTeamEvent ? this.secondPlayer : this.secondPlayer2;
    return firstPlayer?.id === secondPlayer?.id;
  }

  checkIfPlayerOffThePitch(player?: Player): boolean {
    var redCardEvent = [];
    var substitutionEvent = [];

    if(player?.id) {
      redCardEvent = this.events.filter(e => e.type === this.CARD_EVENT_ID 
        && e.player.id === player.id 
        && e.cardType === 1);

      substitutionEvent = this.events.filter(e => e.type === this.SUBSTITUTION_EVENT_ID 
        && e.exitingPlayer.id === player.id);  
    }
    
    return redCardEvent.length === 1 || substitutionEvent.length === 1;
  }

  onGoalTypeChange(homeTeamEvent: boolean): void {
    var selectedGoalType = homeTeamEvent ? this.selectedGoalType : this.selectedGoalType2;

    if(selectedGoalType !== 'Normal' && homeTeamEvent)
      this.secondPlayer = undefined;
    else if(selectedGoalType !== 'Normal' && !homeTeamEvent)
      this.secondPlayer2 = undefined;
  }

  navigateToMatches(): void {

  }

}
