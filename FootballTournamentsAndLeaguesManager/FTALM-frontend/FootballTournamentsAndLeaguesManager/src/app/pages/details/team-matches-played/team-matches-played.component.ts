import { Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Match } from 'src/app/models/Match/match';
import { MatchService } from 'src/app/services/matchService/match.service';

@Component({
  selector: 'app-team-matches-played',
  templateUrl: './team-matches-played.component.html',
  styleUrls: ['./team-matches-played.component.css']
})
export class TeamMatchesPlayedComponent {
  teamId!: number;
  displayedColumns: string[] = ['competitionName', 'homeTeamName', 'result', 'awayTeamName', 'date', 'details'];
  teamPlayedMatchesDataSource: MatTableDataSource<Match> = new MatTableDataSource();

  @ViewChild('teamMatchesPaginator', {static: true}) teamMatchesPaginator: MatPaginator | null = null;
  @ViewChild(MatSort, {static: false}) sort!: MatSort;

  constructor(private route: ActivatedRoute, private matchService: MatchService, private router: Router) {}

  ngOnInit(): void { 
    this.route.params.subscribe(params => {
      this.teamId = params['teamId'];
    });

    this.matchService.getAllPlayedMatchesByTeamId(this.teamId).subscribe(matches => {
      this.teamPlayedMatchesDataSource.data = matches;
    });
    this.sort.direction = 'desc';
  }

  ngAfterViewInit() {
    this.teamPlayedMatchesDataSource.paginator = this.teamMatchesPaginator;

    this.teamPlayedMatchesDataSource.sort = this.sort;
  }

  applyFilterByCompetitionName(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value.toLowerCase();
  
    this.teamPlayedMatchesDataSource.filterPredicate = (dataSource: Match, filter: string) => {
      const tournamentName = dataSource.tournamentName?.toLowerCase();
      const leagueName = dataSource.leagueName?.toLowerCase();
      return tournamentName ? tournamentName.includes(filter) : leagueName ? leagueName.includes(filter) : false;
    };

    this.teamPlayedMatchesDataSource.filter = filterValue;
  }

  applyFilterByAgainstTeam(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value.toLowerCase();
  
    this.teamPlayedMatchesDataSource.filterPredicate = (dataSource: Match, filter: string) => {
      const homeTeamName = dataSource.homeTeamName?.toLowerCase();
      const awayTeamName = dataSource.awayTeamName?.toLowerCase();
      return homeTeamName && awayTeamName ? homeTeamName.includes(filter) || awayTeamName.includes(filter) : false;
    };

    this.teamPlayedMatchesDataSource.filter = filterValue;
  }

  showMatchDetails(match: Match): void {
    if (match.tournamentId !== null) {
      this.router.navigate([`tournament/${match.tournamentId}/match-details/${match.id}`]);
    } else if (match.leagueId !== null) {
      this.router.navigate([`league/${match.leagueId}/match-details/${match.id}`]);
    }
  }

}
