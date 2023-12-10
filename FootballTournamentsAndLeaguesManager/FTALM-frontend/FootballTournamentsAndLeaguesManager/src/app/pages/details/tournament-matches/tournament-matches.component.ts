import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Match } from 'src/app/models/Match/match';
import { MatchService } from 'src/app/services/matchService/match.service';
import { MatchWeekNumberService } from 'src/app/services/matchweekService/match-week-number.service';
import { TournamentService } from '../../../services/tournamentService/tournament.service';
import { Tournament, Type } from 'src/app/models/Tournament/tournament';

@Component({
  selector: 'app-tournament-matches',
  templateUrl: './tournament-matches.component.html',
  styleUrls: ['./tournament-matches.component.css']
})
export class TournamentMatchesComponent {
  groupAndKnockout: string = Type.GROUP_AND_KNOCKOUT.toUpperCase().replaceAll(' ', '_');
  singleElimination: string = Type.SINGLE_ELIMINATION.toUpperCase().replaceAll(' ', '_');
  tournamentId!: number;
  tournamentType!: string;
  numberOfTeams!: number;
  roundNumber: number = 1 ;
  groupNumber: number = 1 ;
  lastGroupNumber?: number;
  lastRoundNumber?: number;
  tournamentBracketStageMatchesData: Match[] = [];
  tournamentGroupStageMatchesData: Match[] = [];
  rounds: number[] = [];
  groups: number[] = [];
  selectedView = 'groupStage';

  constructor(private route: ActivatedRoute, private router: Router, private matchService: MatchService,
    private tournamentService: TournamentService, public matchweekNumberService: MatchWeekNumberService) { }

  ngOnInit(): void { 
    this.route.params.subscribe(params => {
      this.tournamentId = params['id'];
    });
    
    this.roundNumber = this.matchweekNumberService.roundNumber;
    this.groupNumber = this.matchweekNumberService.groupNumber;

    this.tournamentService.findTournamentById(this.tournamentId).subscribe((tournament: Tournament) => {
      this.tournamentType = tournament.type?.toString() || '';
      this.numberOfTeams = tournament.numberOfTeams || 0; 
    });

    this.matchService.getTournamentMatchesByTournamentId(this.tournamentId).subscribe((matches: Match[]) => {

      if (this.tournamentType === this.groupAndKnockout) {
        this.tournamentGroupStageMatchesData = matches.filter(match => match.matchweek !== 0);
        this.tournamentBracketStageMatchesData = matches.filter(match => match.round !== 0);
        this.lastGroupNumber = this.getLastGroupNumber();
        this.lastRoundNumber = Math.log2(this.lastGroupNumber*2); // two teams from each group will play in the bracket stage
        this.groups = [...Array(this.lastGroupNumber).keys()].map(i => i + 1);
      
      } else if (this.tournamentType === this.singleElimination) {
        this.tournamentBracketStageMatchesData = matches;
        this.lastRoundNumber = Math.log2(this.numberOfTeams);
      }
      
      this.rounds = [...Array(this.lastRoundNumber).keys()].map(i => i + 1);

    });
  }

  previousRoundOrGroup(isRoundContainer: boolean): void {
    if (isRoundContainer) {
      this.roundNumber -= 1;
      this.matchweekNumberService.roundNumber -=1;
    }else {
      this.groupNumber -= 1;
      this.matchweekNumberService.groupNumber -=1;
    }
  }

  nextRoundOrGroup(isRoundContainer: boolean): void {
    if (isRoundContainer) {
      this.roundNumber += 1;
      this.matchweekNumberService.roundNumber +=1;
    }else {
      this.groupNumber += 1;
      this.matchweekNumberService.groupNumber +=1;
    }
  }

  fillMatchProtocol(selectedMatch: Match): void {
    this.router.navigate([`/tournament/${this.tournamentId}/match/` + selectedMatch.id])
  }

  showMatchDetails(selectedMatch: Match): void {
    this.router.navigate([`/tournament/${this.tournamentId}/match-details/` + selectedMatch.id])
  }

  getLastGroupNumber(): number {
    return this.tournamentGroupStageMatchesData.reduce((lastGroup, match) => {
      if (match.matchweek != null && match.matchweek > lastGroup) {
          return match.matchweek;
      } else {
          return lastGroup;
      }
    }, -1);
  }

  jumpToRoundOrGroup(selectedRoundOrGroup: number, isRoundContainer: boolean):void {
      if (isRoundContainer) {
        this.roundNumber = selectedRoundOrGroup;
      } else {
        this.groupNumber = selectedRoundOrGroup;
      }
  }
}
