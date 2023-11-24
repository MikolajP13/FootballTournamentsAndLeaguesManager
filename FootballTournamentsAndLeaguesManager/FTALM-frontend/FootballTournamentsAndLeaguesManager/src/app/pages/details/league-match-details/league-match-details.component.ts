import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { forkJoin } from 'rxjs';
import { Match } from 'src/app/models/Match/match';
import { CardService } from 'src/app/services/cardService/card.service';
import { GoalAssistService } from 'src/app/services/goalAssistService/goal-assist.service';
import { MatchService } from 'src/app/services/matchService/match.service';
import { SubstitutionService } from 'src/app/services/substitutionService/substitution.service';

@Component({
  selector: 'app-league-match-details',
  templateUrl: './league-match-details.component.html',
  styleUrls: ['./league-match-details.component.css']
})
export class LeagueMatchDetailsComponent {
  GOAL_ASSIST_EVENT_ID: string = 'G';
  CARD_EVENT_ID: string = 'C';
  SUBSTITUTION_EVENT_ID: string = 'S';
  displayedColumns: string[] = ['1', '2', '3'];
  matchId!: number;
  homeTeamId?: number;
  awayTeamId?: number;
  match?: Match;
  isTimeLineView: boolean = false;
  matchEvents: any[] = [];

  constructor(private route: ActivatedRoute, private matchService: MatchService, 
    private goalAssistService: GoalAssistService, private cardService: CardService, 
    private substitutionService: SubstitutionService) { }

  ngOnInit(): void { 
    this.route.params.subscribe(params => {
      this.matchId = params['id'];
    });

    this.matchService.getMatchByMatchId(this.matchId).subscribe(match =>{
      this.match = match;
      this.homeTeamId = match.homeTeamId;
      this.awayTeamId = match.awayTeamId;
    });

    this.fetchMatchEvents(this.matchId);
  }

  fetchMatchEvents(matchId: number) {
    //forkJoin, sort by minute, add type
    const goalAssistObservable = this.goalAssistService.findAllGoalAssistsInMatch(matchId);
    const cardObservable = this.cardService.findAllCardsInMatch(matchId);
    const substitutionObservable = this.substitutionService.findAllSubstitutionsInMatch(matchId);

    forkJoin({
      goalAssist: goalAssistObservable,
      card: cardObservable,
      substitution: substitutionObservable
    }).subscribe(result => {
      this.matchEvents = [];
      this.matchEvents = this.matchEvents.concat(result.goalAssist.map(details => ({details, type: this.GOAL_ASSIST_EVENT_ID})));
      this.matchEvents = this.matchEvents.concat(result.card.map(details => ({details, type: this.CARD_EVENT_ID})));
      this.matchEvents = this.matchEvents.concat(result.substitution.map(details => ({details, type: this.SUBSTITUTION_EVENT_ID})));
    
      this.matchEvents.sort((event1, event2) => event2.details.minute - event1.details.minute);
    });
  }
}
