import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { ConfirmationPopupComponent } from '../../popups/confirmation-popup/confirmation-popup.component';
import { LeagueService } from 'src/app/services/leagueService/league.service';
import { Status } from 'src/app/models/TournamentLeagueBase/tournamentLeagueBase';
import { TeamService } from 'src/app/services/teamService/team.service';
import { League } from 'src/app/models/League/league';
import { Team } from 'src/app/models/Team/team';
import { Match } from 'src/app/models/Match/match';
import { MatchService } from 'src/app/services/matchService/match.service';
import { LeagueStanding } from '../../../models/LeagueStanding/leagueStanding';
import { LeagueStandingService } from '../../../services/leagueStandingService/league-standing.service';

@Component({
  selector: 'app-league-about',
  templateUrl: './league-about.component.html',
  styleUrls: ['./league-about.component.css']
})
export class LeagueAboutComponent {
  private static BLANK_TEAM_NAME: string = 'Blank Team';
  leagueId!: number;
  objectName: string = 'league';
  league!: League;
  leagueCanBeStarted: boolean = false;
  leagueCanBeDeletedWithWarning: boolean = false;
  teams: Team[] = [];

  constructor(private route: ActivatedRoute, private dialog: MatDialog, private matchService: MatchService,
    private leagueService: LeagueService, private teamService: TeamService, private leagueStandingService: LeagueStandingService) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.leagueId = +params['leagueId'];
    });

    this.leagueService.findLeagueById(this.leagueId).subscribe(league => {
      this.league = league;
    });

    this.leagueService.getLeagueStatus(this.leagueId).subscribe(status => {
      if(status === Status.NOT_STARTED || status === Status.FINISHED)
        this.leagueCanBeDeletedWithWarning = false;
      else
        this.leagueCanBeDeletedWithWarning = true;
    });

    this.leagueService.getNumberOfTeams(this.leagueId).subscribe(result1 => {
      this.teamService.countTeamsByLeagueId(this.leagueId).subscribe(result2 => {
        if (result1 === result2 && this.league.status == Status.NOT_STARTED)
          this.leagueCanBeStarted = true;
        else
          this.leagueCanBeStarted = false;
      });
    });
  }

  openConfirmationPopup(){
    this.dialog.open(ConfirmationPopupComponent, {data: {
      id: this.leagueId,
      objectName: this.objectName,
      warning: this.leagueCanBeDeletedWithWarning
    }
    });
  }

  startLeague() {
    this.leagueCanBeStarted = false;
    this.leagueService.startLeague(this.leagueId).subscribe();
  }
}
