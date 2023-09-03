import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { TeamService } from 'src/app/services/teamService/team.service';

@Component({
  selector: 'app-confirmation-popup',
  templateUrl: './confirmation-popup.component.html',
  styleUrls: ['./confirmation-popup.component.css']
})
export class ConfirmationPopupComponent {
  teamId: number;
  object: string;

  constructor(public dialogRef: MatDialogRef<ConfirmationPopupComponent>, private teamService: TeamService, private router: Router, @Inject(MAT_DIALOG_DATA) public data: any) {
    this.object = data.objectName;
    this.teamId = data.id;
  }

  deleteTeam(){
    //TODO: check if team takes part in the legaue or tournament
    this.teamService.deleteTam(this.teamId).subscribe(result => {});
    this.dialogRef.close();
    window.location.href = '/teams/'; // navigate to teams page and refresh
  }

  closePopup(){
    this.dialogRef.close();
  }
}
