import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { ConfirmationPopupComponent } from '../../popups/confirmation-popup/confirmation-popup.component';
import { LeagueService } from 'src/app/services/leagueService/league.service';
import { Status } from 'src/app/models/TournamentLeagueBase/tournamentLeagueBase';
import { TeamService } from 'src/app/services/teamService/team.service';
import { League } from 'src/app/models/League/league';

@Component({
  selector: 'app-league-about',
  templateUrl: './league-about.component.html',
  styleUrls: ['./league-about.component.css']
})
export class LeagueAboutComponent {
  leagueId!: number;
  objectName: string = 'league';
  leagueCanBeStarted: boolean = false;
  leagueCanBeDeletedWithWarning: boolean = false;

  constructor(private route: ActivatedRoute, private dialog: MatDialog, private leagueService: LeagueService, private teamService: TeamService) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.leagueId = params['id'];
    });

    this.leagueService.getLeagueStatus(this.leagueId).subscribe(status => {
      if(status === Status.NOT_STARTED || status === Status.FINISHED)
        this.leagueCanBeDeletedWithWarning = false;
      else
        this.leagueCanBeDeletedWithWarning = true;
    });

    this.leagueService.getNumberOfTeams(this.leagueId).subscribe(result1 => {
      this.teamService.countTeamsByLeagueId(this.leagueId).subscribe(result2 => {
        if (result1 === result2)
          this.leagueCanBeStarted = true;
        else
          this.leagueCanBeStarted = true; //false; ONLY TEMPORARY!!!!!!!!!!!!!
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
    console.log("Start league " + this.leagueId);
    //TODO: DB update league status to IN_PROGRESS
    const updatedStatus: League = {
      status: Status.IN_PROGRESS
    };
    this.leagueService.updateLeagueStatusByLeagueId(this.leagueId, updatedStatus).subscribe();

    //TODO: league can only be started once!!
    this.leagueCanBeStarted = false; // check but for now not working due to testing (result1 != result2)

    //TODO: logic for creating matches, table etc.

  }
}
