import { Component, Inject } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Foot, Player, Position, PositionDetail } from 'src/app/models/Player/player';
import { PlayerService } from 'src/app/services/playerService/player.service';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

@Component({
  selector: 'app-add-player-popup',
  templateUrl: './add-player-popup.component.html',
  styleUrls: ['./add-player-popup.component.css']
})
export class AddPlayerPopupComponent {
  firstName!: string;
  lastName!: string;
  dateOfBirth!: Date;
  heightInCm!: number;
  selectedFoot!: string;
  selectedPosition!: string;
  selectedPositionDetail!: string;
  startDefenderIndex: number = 10;
  startMidfielderIndex: number = 5;
  startForwardIndex: number = 0;
  
  foots = Object.values(Foot).filter(value => typeof value === 'string');
  positions = Object.values(Position).filter(value => typeof value === 'string');
  positionDefenderDetails = Object.values(PositionDetail).filter((value, index) => typeof value === 'string' && index >= 10 && index <= 14);
  positionMidfielderDetails = Object.values(PositionDetail).filter((value, index) => typeof value === 'string' && index >= 5 && index <= 9);
  positionForwardDetails = Object.values(PositionDetail).filter((value, index) => typeof value === 'string' && index >= 0 && index <= 4);
  
  positionDefenderDetailIndexMap = new Map<string, number>();
  positionMidfielderDetailIndexMap = new Map<string, number>();
  positionForwardDetailIndexMap = new Map<string, number>();

  constructor(public dialogRef: MatDialogRef<AddPlayerPopupComponent>, private playerService: PlayerService, @Inject(MAT_DIALOG_DATA) public teamId: number) {
    this.positionDefenderDetails.forEach((detail, index) => {
      if(index < 4) 
        this.positionForwardDetailIndexMap.set(detail, index);
      else if(index > 4 && index < 10)
        this.positionMidfielderDetailIndexMap.set(detail, index);
      else
        this.positionDefenderDetailIndexMap.set(detail, index);
    });
  }

  addPlayer(playerForm: NgForm): void {
    let positionDetail = Object.values(PositionDetail).indexOf(PositionDetail.GK);
    let foot; 
    
    if (playerForm.value.foot === 'right'){
      foot = Object.values(Foot).indexOf(Foot.RIGHT);
    }else if(playerForm.value.foot === 'left'){
      foot = Object.values(Foot).indexOf(Foot.LEFT);
    }else{
      foot = Object.values(Foot).indexOf(Foot.BOTH);
    }

    if (playerForm.value.position !== 'GOALKEEPER')
      positionDetail = playerForm.value.positionDetail;

    const player: Player = {
      team: {id: this.teamId},
      firstName: playerForm.value.firstName,
      lastName: playerForm.value.lastName,
      dateOfBirth: playerForm.value.dateOfBirth,
      heightInCm: playerForm.value.heightInCm,
      foot: foot as unknown as Foot,
      joinedDate: new Date(),
      position: playerForm.value.position,
      positionDetail: positionDetail as unknown as PositionDetail
    }

    this.playerService.addPlayer(player).subscribe(result => { });

    this.dialogRef.close('success');
  }

  closePopup(){
    this.dialogRef.close();
  }

  getPositionName(position: number): string {
    return Position[position];
  }
}
