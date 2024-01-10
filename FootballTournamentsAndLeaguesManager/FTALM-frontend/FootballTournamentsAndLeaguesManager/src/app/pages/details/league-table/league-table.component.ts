import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { LeagueStanding } from 'src/app/models/LeagueStanding/leagueStanding';
import { LeagueService } from 'src/app/services/leagueService/league.service';
import { LeagueStandingService } from 'src/app/services/leagueStandingService/league-standing.service';
import { TeamService } from 'src/app/services/teamService/team.service';
import { MatchService } from '../../../services/matchService/match.service';
import { CardService } from 'src/app/services/cardService/card.service';

@Component({
  selector: 'app-league-table',
  templateUrl: './league-table.component.html',
  styleUrls: ['./league-table.component.css']
})
export class LeagueTableComponent {
  leagueName: string;
  leagueId!: number;
  positionCounter: number = 0;
  displayedColumns: string[] = ['position', 'teamName', 'matches', 'won', 'drawn', 'lost', 'goalsForAgainst', 'goalDifference', 'points', 'form'];
  leagueTableDataSource: LeagueStanding[] = [];

  formArray: string[] = ['w', 'w', 'l', 'd', 'l'];

  constructor(private route: ActivatedRoute, private teamService: TeamService, private matchService: MatchService, private cardService: CardService,
    private leagueService: LeagueService, private leagueStandingService: LeagueStandingService) { 
    this.leagueName = '';
  }
  
  ngOnInit() {
    this.route.params.subscribe(params => {
      this.leagueId = params['leagueId'];
    });
    
    this.setLeagueName(this.leagueId);
    
    this.leagueStandingService.getLeagueStanding(this.leagueId).subscribe((leagueStanding: LeagueStanding[]) => {
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
