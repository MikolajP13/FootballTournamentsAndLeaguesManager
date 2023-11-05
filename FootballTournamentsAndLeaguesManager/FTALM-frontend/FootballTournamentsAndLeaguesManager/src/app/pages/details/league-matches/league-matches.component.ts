import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Match } from 'src/app/models/Match/match';
import { MatchService } from 'src/app/services/matchService/match.service';

@Component({
  selector: 'app-league-matches',
  templateUrl: './league-matches.component.html',
  styleUrls: ['./league-matches.component.css']
})

export class LeagueMatchesComponent {
  leagueId!: number;
  matchweekNumber: number = 1;
  lastMatchWeekNumber!: number;
  leagueMatchesData: Match[] = [];

  constructor(private route: ActivatedRoute, private matchService: MatchService) { }

  ngOnInit(): void { 
    this.route.params.subscribe(params => {
      this.leagueId = params['id'];
    });
    
    this.matchService.getLeagueMatchesByLeagueId(this.leagueId).subscribe((match: Match[]) => {
      this.leagueMatchesData = [...match];
      this.lastMatchWeekNumber = this.getLastMatchWeekNumber(this.leagueMatchesData);
    });

  }

  previousMatchWeek(): void {
    this.matchweekNumber -= 1;
  }

  nextMatchWeek(): void {
    this.matchweekNumber += 1;
  }

  fillMatchProtocol(): void {
  
  }

  showMatchDetails(): void {
  
  }

  getLastMatchWeekNumber(leagueMatchesData: Match[]): number {
    return this.leagueMatchesData.reduce((lastMatchweek, match) => {
      if (match.matchweek != null && match.matchweek > lastMatchweek) {
          return match.matchweek;
      } else {
          return lastMatchweek;
      }
    }, -1);
  }

}
