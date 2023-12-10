import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { forkJoin } from 'rxjs';
import { Card, CardType } from 'src/app/models/Card/card';
import { GoalAssist } from 'src/app/models/GoalAssist/goalAssist';
import { LeagueStanding } from 'src/app/models/LeagueStanding/leagueStanding';
import { Match } from 'src/app/models/Match/match';
import { Player } from 'src/app/models/Player/player';
import { Substitution } from 'src/app/models/Substitution/substitution';
import { CardService } from 'src/app/services/cardService/card.service';
import { GoalAssistService } from 'src/app/services/goalAssistService/goal-assist.service';
import { MatchService } from 'src/app/services/matchService/match.service';
import { MatchWeekNumberService } from 'src/app/services/matchweekService/match-week-number.service';
import { PlayerService } from 'src/app/services/playerService/player.service';
import { SubstitutionService } from 'src/app/services/substitutionService/substitution.service';
import { SnackBarComponent } from 'src/app/shared-components/snack-bar/snack-bar.component';
import { TournamentStandingService } from '../../../services/tournamentStandingService/tournament-standing.service';
import { TournamentStanding } from 'src/app/models/TournamentStanding/tournamentStanding';

@Component({
  selector: 'app-tournament-match',
  templateUrl: './tournament-match.component.html',
  styleUrls: ['./tournament-match.component.css']
})
export class TournamentMatchComponent {
  GOAL_ASSIST_EVENT_ID: string = 'G';
  CARD_EVENT_ID: string = 'C';
  SUBSTITUTION_EVENT_ID: string = 'S';
  matchId!: number;
  tournamentId!: number;
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
    private tournamentStandingService: TournamentStandingService, private snackBar: SnackBarComponent,
    private matchWeekNumberService: MatchWeekNumberService, private router: Router) {}

  // DUPLICATE CODE NEED TO BE REMOVED

  ngOnInit(): void { 
    this.route.params.subscribe(params => {
      this.matchId = params['id'];
      this.tournamentId = params['tournamentId'];
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
      matchProtocolCreated: true,
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

      if(this.tournamentId && this.match?.homeTeamId && this.match?.awayTeamId 
          && this.match?.matchweek && this.match?.matchweek > 0) {
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
        
        var tournamentStandingForHomeTeam: TournamentStanding = {
          goalsFor: this.homeTeamScore,
          goalsAgainst: this.awayTeamScore,
          wins: homeTeamWin,
          draws: draw,
          losses: homeTeamLose
        }

        var tournamentStandingForAwayTeam: TournamentStanding = {
          goalsFor: this.awayTeamScore,
          goalsAgainst: this.homeTeamScore,
          wins: awayTeamWin,
          draws: draw,
          losses: awayTeamLose
        }

        this.tournamentStandingService.updateTournamentStanding(this.tournamentId, this.match.matchweek, this.match.homeTeamId, tournamentStandingForHomeTeam).subscribe();
        this.tournamentStandingService.updateTournamentStanding(this.tournamentId, this.match.matchweek, this.match.awayTeamId, tournamentStandingForAwayTeam).subscribe();
      
      } else if (this.tournamentId && this.match?.homeTeamId && this.match?.awayTeamId) {
        // Create match for next round
      }

    }

    this.navigateToMatches();
    
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
    if(this.match?.matchweek && this.match?.round) {
      if(this.match?.matchweek > 0)
        this.matchWeekNumberService.groupNumber = this.match?.matchweek;
      else
        this.matchWeekNumberService.roundNumber = this.match?.round;
    }
    
    this.router.navigate([`/tournament/${this.tournamentId}/matches/`])
  }

}