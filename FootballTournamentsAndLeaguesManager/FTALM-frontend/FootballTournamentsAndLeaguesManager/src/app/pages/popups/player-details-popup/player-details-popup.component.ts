import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Player, Position } from 'src/app/models/Player/player';
import { PositionDetail } from '../../../models/Player/player';

@Component({
  selector: 'app-player-details-popup',
  templateUrl: './player-details-popup.component.html',
  styleUrls: ['./player-details-popup.component.css']
})
export class PlayerDetailsPopupComponent {
  goalkeeper = Position[0]; //Position.GOALKEEPER
  positionDetail!: string;

  constructor(public dialogRef: MatDialogRef<PlayerDetailsPopupComponent>, @Inject(MAT_DIALOG_DATA) public player: Player) { 
    this.positionDetail = PositionDetail[player.positionDetail?.toString() as keyof typeof PositionDetail];
  }

  closePopup(){
    this.dialogRef.close();
  }
}
