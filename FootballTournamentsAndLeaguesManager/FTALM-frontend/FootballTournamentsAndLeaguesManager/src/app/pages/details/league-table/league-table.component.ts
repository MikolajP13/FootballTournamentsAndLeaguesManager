import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { LeagueStanding } from 'src/app/models/LeagueStanding/leagueStanding';
import { Team } from 'src/app/models/Team/team';
import { LeagueService } from 'src/app/services/leagueService/league.service';
import { LeagueStandingService } from 'src/app/services/leagueStandingService/league-standing.service';
import { TeamService } from 'src/app/services/teamService/team.service';

@Component({
  selector: 'app-league-table',
  templateUrl: './league-table.component.html',
  styleUrls: ['./league-table.component.css']
})
export class LeagueTableComponent {
  leagueName: string;
  leagueId!: number;
  positionCounter: number = 0;
  displayedColumns: string[] = ['position', 'teamName', 'matches', 'won', 'drawn', 'lost', 'goalsForAgainst', 'goalDifference', 'points'];
  leagueTableDataSource: LeagueStanding[] = [];

  constructor(private route: ActivatedRoute, private teamService: TeamService, private leagueService: LeagueService, private leagueStandingService: LeagueStandingService) { 
    this.leagueName = '';
  }
  
  ngOnInit() {
    this.route.params.subscribe(params => {
      this.leagueId = params['id'];
    });
    
    this.setLeagueName(this.leagueId);

    //Fetch test
    this.leagueStandingService.getLeagueStanding(4).subscribe((leagueStanding: LeagueStanding[]) => {
      this.leagueTableDataSource = [...leagueStanding];
    });
    

  }

  private setLeagueName(leagueId: number): void {
    this.leagueService.getLeagueName(leagueId).subscribe(result => {
      this.leagueName = result;
    }
    );
  }
}
