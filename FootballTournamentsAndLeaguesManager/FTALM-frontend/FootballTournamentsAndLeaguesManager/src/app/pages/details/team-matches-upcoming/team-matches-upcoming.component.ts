import { Component, ViewChild } from '@angular/core';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { ActivatedRoute, Router } from '@angular/router';
import { Match } from 'src/app/models/Match/match';
import { MatchService } from 'src/app/services/matchService/match.service';

@Component({
  selector: 'app-team-matches-upcoming',
  templateUrl: './team-matches-upcoming.component.html',
  styleUrls: ['./team-matches-upcoming.component.css']
})
export class TeamMatchesUpcomingComponent {
  teamId!: number;
  displayedColumns: string[] = ['competitionName', 'homeTeamName', 'result', 'awayTeamName'];
  teamUpcomingMatchesDataSource: MatTableDataSource<Match> = new MatTableDataSource();

  @ViewChild('teamMatchesPaginator', {static: true}) teamMatchesPaginator: MatPaginator | null = null;

  constructor(private route: ActivatedRoute, private matchService: MatchService) {}

  ngOnInit(): void { 
    this.route.params.subscribe(params => {
      this.teamId = params['teamId'];
    });

    this.matchService.getUpcomingMatchesByTeamId(this.teamId).subscribe(matches => {
      this.teamUpcomingMatchesDataSource.data = matches.filter(match => match.homeTeamName !== "Blank Team" && match.awayTeamName !== "Blank Team");
    });

  }

  ngAfterViewInit() {
    this.teamUpcomingMatchesDataSource.paginator = this.teamMatchesPaginator;
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value.toLowerCase();
  
    this.teamUpcomingMatchesDataSource.filterPredicate = (dataSource: Match, filter: string) => {
      const tournamentName = dataSource.tournamentName?.toLowerCase();
      const leagueName = dataSource.leagueName?.toLowerCase();
      return tournamentName ? tournamentName.includes(filter) : leagueName ? leagueName.includes(filter) : false;
    };
    
    this.teamUpcomingMatchesDataSource.filter = filterValue;
  }

}
