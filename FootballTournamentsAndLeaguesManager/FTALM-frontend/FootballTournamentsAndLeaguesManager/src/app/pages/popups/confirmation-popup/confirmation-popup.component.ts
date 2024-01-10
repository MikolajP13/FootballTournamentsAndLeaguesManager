import { Component, Inject } from '@angular/core';
import { MatDialogConfig, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { LeagueService } from 'src/app/services/leagueService/league.service';
import { TeamService } from 'src/app/services/teamService/team.service';
import { TournamentService } from 'src/app/services/tournamentService/tournament.service';

@Component({
  selector: 'app-confirmation-popup',
  templateUrl: './confirmation-popup.component.html',
  styleUrls: ['./confirmation-popup.component.css']
})
export class ConfirmationPopupComponent {
  id: number;
  object: string;
  warning: boolean = false;

  constructor(public dialogRef: MatDialogRef<ConfirmationPopupComponent>, private teamService: TeamService, 
    private tournamentService: TournamentService, private leagueService: LeagueService,
    private router: Router, @Inject(MAT_DIALOG_DATA) public data: any) 
  {
    this.object = data.objectName;
    this.id = data.id;
    this.warning = data.warning;
  }

  ngOnInit() {
    const matDialogConfig = new MatDialogConfig();
    matDialogConfig.position = {top: `170px`};
    this.dialogRef.updatePosition(matDialogConfig.position);
  }

  delete() {
    if(this.object === 'team'){
      this.deleteTeam();
    }else if(this.object === 'tournament'){
      this.deleteTournament();
    }else if(this.object === 'league'){
      this.deleteLeague();
    }
  }

  private deleteTeam() {
    //TODO: check if team takes part in the legaue or tournament
    this.teamService.deleteTam(this.id).subscribe(result => {});
    this.dialogRef.close();
    window.location.href = '/teams/'; // navigate to teams page and refresh
  }

  private deleteTournament() {
    //TODO: check!
    this.tournamentService.deleteTournament(this.id).subscribe(result => {});
    this.dialogRef.close();
    window.location.href = '/tournaments/'; // navigate to tournaments page and refresh
  }

  private deleteLeague() {
    //TODO: check!
    this.leagueService.deleteLeague(this.id).subscribe(result => {});
    this.dialogRef.close();
    window.location.href = '/leagues/'; // navigate to leagues page and refresh
  }

  closePopup(){
    this.dialogRef.close();
  }
}
