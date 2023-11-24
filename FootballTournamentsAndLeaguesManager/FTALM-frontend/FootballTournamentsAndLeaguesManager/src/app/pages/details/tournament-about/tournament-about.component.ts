import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { ConfirmationPopupComponent } from '../../popups/confirmation-popup/confirmation-popup.component';
import { TournamentService } from 'src/app/services/tournamentService/tournament.service';
import { TeamService } from 'src/app/services/teamService/team.service';
import { Tournament, Type } from 'src/app/models/Tournament/tournament';
import { Status } from 'src/app/models/TournamentLeagueBase/tournamentLeagueBase';

@Component({
  selector: 'app-tournament-about',
  templateUrl: './tournament-about.component.html',
  styleUrls: ['./tournament-about.component.css']
})
export class TournamentAboutComponent {
  tournamentId!: number;
  objectName: string = 'tournament';
  tournament!: Tournament;
  tournamentCanBeStarted: boolean = false;
  tournamentCanBeDeletedWithWarning: boolean = false;

  constructor(private route: ActivatedRoute, private dialog: MatDialog, private tournamentService: TournamentService, private teamService: TeamService) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.tournamentId = params['id'];
    });

    this.tournamentService.findTournamentById(this.tournamentId).subscribe(tournament => {
      this.tournament = tournament;
    });

    this.tournamentService.getTournamentStatus(this.tournamentId).subscribe(status => {
      if(status === Status.FINISHED || status === Status.NOT_STARTED)
        this.tournamentCanBeDeletedWithWarning = false;
      else
        this.tournamentCanBeDeletedWithWarning = true;
    });

    this.tournamentService.getNumberOfTeams(this.tournamentId).subscribe(result1 => {
      this.teamService.countTeamsByTournamentId(this.tournamentId).subscribe(result2 => {
        if (result1 === result2)
          this.tournamentCanBeStarted = true;
        else
          this.tournamentCanBeStarted = true; //false; ONLY TEMPORARY!!!!!!!!!!!!!
      });
    });
  }

  openConfirmationPopup(){
    this.dialog.open(ConfirmationPopupComponent, {data: {
      id: this.tournamentId,
      objectName: this.objectName,
      warning: this.tournamentCanBeDeletedWithWarning
    }
    });
  }

  startTournament() {
    console.log("Start tournament " + this.tournamentId);
    //TODO: DB update tournament status to IN_PROGRESS
    const updatedStatus: Tournament = {
      status: Status.IN_PROGRESS
    }

    //this.tournamentService.updateTournamentStatusByTournamentId(this.tournamentId, updatedStatus).subscribe();

    //TODO: tournament can only be started once!!
    this.tournamentCanBeStarted = false;

    if(this.tournament.type === Type.GROUP_AND_KNOCKOUT) {
      console.log(JSON.stringify(this.tournament));
      //TODO: get all teams, split to groups, create tournament standings (groupId!!), create matches (round 0/matchweek as groupId!!) 
    }

    //TODO: logic for creating matches etc.

  }
}
