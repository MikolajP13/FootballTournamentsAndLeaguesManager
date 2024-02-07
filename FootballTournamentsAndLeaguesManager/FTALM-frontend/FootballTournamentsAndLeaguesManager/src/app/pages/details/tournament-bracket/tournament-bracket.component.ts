import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Tournament, Type } from 'src/app/models/Tournament/tournament';
import { TournamentStanding } from 'src/app/models/TournamentStanding/tournamentStanding';
import { TeamService } from 'src/app/services/teamService/team.service';
import { TournamentService } from 'src/app/services/tournamentService/tournament.service';
import { TournamentStandingService } from '../../../services/tournamentStandingService/tournament-standing.service';
import { Match } from 'src/app/models/Match/match';
import { MatchService } from 'src/app/services/matchService/match.service';
import { forkJoin, map } from 'rxjs';

@Component({
  selector: 'app-tournament-bracket',
  templateUrl: './tournament-bracket.component.html',
  styleUrls: ['./tournament-bracket.component.css']
})
export class TournamentBracketComponent {
  private static TOURNAMENT_GROUP_ROUND: number = 0;
  groupAndKnockout: string = Type.GROUP_AND_KNOCKOUT.toUpperCase().replaceAll(' ', '_');
  singleElimination: string = Type.SINGLE_ELIMINATION.toUpperCase().replaceAll(' ', '_');
  tournamentId!: number;
  tournament!: Tournament;
  numberOfTeams!: number;
  numberOfRounds: number[] = [];
  matchesInRound: number[] = [];
  tournamentStandingDataSource: TournamentStanding[][] = [];
  tournamentGroupMatchesDataSource: Match[][] = [];
  tournamentBracketMatchesDataSource: Map<number, Match[]> = new Map<number, Match[]>;

  displayedColumns: string[] = ['position', 'teamName', 'matches', 'won', 'drawn', 'lost', 'goalsForAgainst', 'goalDifference', 'points', 'teamForm'];

  constructor(private route: ActivatedRoute, private router: Router, private tournamentService: TournamentService,
    private teamService: TeamService, private tournamentStandingService: TournamentStandingService,
    private matchService: MatchService) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.tournamentId = params['tournamentId'];
    });

    this.tournamentService.findTournamentById(this.tournamentId).subscribe(tournament => {
      this.tournament = tournament;
    });

    this.teamService.countTeamsByTournamentId(this.tournamentId).subscribe(result => {
      const requests = [];
      this.numberOfTeams = result;
      this.numberOfRounds = Array.from({ length: Math.log2(this.numberOfTeams) }, (_, index) => index+1);
      
      this.matchService.getTournamentMatchesForBracketStage(this.tournamentId).subscribe(matches => {
        let numberOfRounds = Math.log2(this.numberOfTeams);

        for (let i = 1; i <= numberOfRounds; i++) {
          var filteredMatches: Match[] = matches.filter(match => match.round === i);
          var filteredMatchesLength = filteredMatches.length;
          var matchesPerRound = this.calculateMatchesPerRound(this.numberOfTeams, i);
 
          if(matchesPerRound === filteredMatchesLength) {
            
            this.tournamentBracketMatchesDataSource.set(i, filteredMatches);

          }else if(matchesPerRound - filteredMatchesLength < matchesPerRound){

            for(let j = 0; j < matchesPerRound - filteredMatchesLength; j++){
              filteredMatches.push({
                homeTeamName: '-',
                awayTeamName: '-',
                homeTeamScore: null,
                awayTeamScore: null,
                tournament: {id: this.tournamentId},
                round: i
              });
            }
            this.tournamentBracketMatchesDataSource.set(i, filteredMatches);

          }else {
            
            for(let k = 0; k < matchesPerRound; k++){
              filteredMatches.push({
                homeTeamName: '-',
                awayTeamName: '-',
                homeTeamScore: null,
                awayTeamScore: null,
                tournament: {id: this.tournamentId},
                round: i
              });
            }
            this.tournamentBracketMatchesDataSource.set(i, filteredMatches);
          }
        }

      });

      for (let round = 1; round <= this.numberOfRounds.length; round++) {
        const matchesInRound = this.calculateMatchesPerRound(this.numberOfTeams, round);
        this.matchesInRound.push(matchesInRound);
      }

      result /= 4;
    
      for (let i = 1; i <= result; i++) {
        const standingRequest = this.tournamentStandingService.getTournamentStandingByTournamentIdAndGroupId(this.tournamentId, i);
        const matchesRequest = this.matchService.getTournamentMatchesByRoundAndGroup(this.tournamentId, TournamentBracketComponent.TOURNAMENT_GROUP_ROUND, i);
        requests.push(
          forkJoin([standingRequest, matchesRequest]).pipe(
            map(([standing, matches]) => ({ standing, matches }))
          )
        );
      }
    
      forkJoin(requests).subscribe(results => {
        results.forEach(({ standing, matches }) => {
          this.tournamentStandingDataSource.push(standing);
          this.tournamentGroupMatchesDataSource.push(matches);
        });
      });
    });
  }

  convertGroupIdToLetter(groupId: number | undefined): string {
    if(groupId === undefined) return '';
    return String.fromCharCode('A'.charCodeAt(0) + groupId - 1);
  }


  //TODO: Remove duplication [league matches functions]
  showMatchDetails(selectedMatch: Match){
    this.router.navigate([`/tournament/${this.tournamentId}/match-details/` + selectedMatch.id])
  }

  calculateMatchesPerRound(numberOfTeams: number, roundNumber: number): number {
    return numberOfTeams / Math.pow(2, roundNumber);
  }

  getMatchesInRound(roundNumber: number): number {
    return this.matchesInRound[roundNumber - 1] || 0;
  }
}
