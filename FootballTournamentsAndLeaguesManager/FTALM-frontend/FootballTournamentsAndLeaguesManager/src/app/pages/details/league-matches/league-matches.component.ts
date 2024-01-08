import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MatchWeekNumberService } from 'src/app/services/matchweekService/match-week-number.service';
import { Match } from 'src/app/models/Match/match';
import { MatchService } from 'src/app/services/matchService/match.service';

@Component({
  selector: 'app-league-matches',
  templateUrl: './league-matches.component.html',
  styleUrls: ['./league-matches.component.css']
})

export class LeagueMatchesComponent {
  leagueId!: number;
  matchweekNumber: number = 1 ;
  matchweekNumber2: number | null = null;
  lastMatchWeekNumber?: number;
  leagueMatchesData: Match[] = [];
  matchweeks: number[] = [];

  constructor(private route: ActivatedRoute, private router: Router, private matchService: MatchService, 
    public matchweekNumberService: MatchWeekNumberService) { }

  ngOnInit(): void { 
    this.route.params.subscribe(params => {
      this.leagueId = params['leagueId'];
    });
    
    this.matchweekNumber = this.matchweekNumberService.matchWeekNumber;

    this.matchService.getLeagueMatchesByLeagueId(this.leagueId).subscribe((match: Match[]) => {
      this.leagueMatchesData = [...match];
      this.lastMatchWeekNumber = this.getLastMatchWeekNumber();
      this.matchweeks = [...Array(this.lastMatchWeekNumber).keys()].map(i => i + 1);
    });

  }

  previousMatchWeek(): void {
    this.matchweekNumber -= 1;
    this.matchweekNumberService.matchWeekNumber -=1;
  }

  nextMatchWeek(): void {
    this.matchweekNumber += 1;
    this.matchweekNumberService.matchWeekNumber +=1;
  }

  fillMatchProtocol(selectedMatch: Match): void {
    this.router.navigate([`/league/${this.leagueId}/match/` + selectedMatch.id])
  }

  showMatchDetails(selectedMatch: Match): void {
    this.router.navigate([`/league/${this.leagueId}/match-details/` + selectedMatch.id])
  }

  getLastMatchWeekNumber(): number {
    return this.leagueMatchesData.reduce((lastMatchweek, match) => {
      if (match.matchweek != null && match.matchweek > lastMatchweek) {
          return match.matchweek;
      } else {
          return lastMatchweek;
      }
    }, -1);
  }

  jumpToMatchweek(selectedMatchweek: number):void {
      this.matchweekNumber = selectedMatchweek;
  }
}
