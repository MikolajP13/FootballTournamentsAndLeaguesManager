import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogConfig } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { ConfirmationPopupComponent } from '../confirmation-popup/confirmation-popup.component';
import { PlayerService } from '../../../services/playerService/player.service';

@Component({
  selector: 'app-delete-player-popup',
  templateUrl: './delete-player-popup.component.html',
  styleUrls: ['./delete-player-popup.component.css']
})
export class DeletePlayerPopupComponent {
  teamId: number;
  objectId: number;
  warning: boolean = false;

  constructor(public dialogRef: MatDialogRef<ConfirmationPopupComponent>, private playerService: PlayerService, 
    private router: Router, @Inject(MAT_DIALOG_DATA) public data: any) 
  {
    this.objectId = data.objectId;
    this.teamId = data.teamId;
    this.warning = data.warning;
  }

  ngOnInit() {
    const matDialogConfig = new MatDialogConfig();
    matDialogConfig.position = {top: `170px`};
    this.dialogRef.updatePosition(matDialogConfig.position);
  }

  delete() {
    if(this.objectId)
      this.playerService.deletePlayer(this.objectId).subscribe(result => {
        if (result) {
          this.dialogRef.close();
          window.location.href = `/team/${this.teamId}/players`; // navigate to team players page and refresh
        } else {
          this.warning = true;
        }
      });
  }

  closePopup(){
    this.dialogRef.close();
  }
}
