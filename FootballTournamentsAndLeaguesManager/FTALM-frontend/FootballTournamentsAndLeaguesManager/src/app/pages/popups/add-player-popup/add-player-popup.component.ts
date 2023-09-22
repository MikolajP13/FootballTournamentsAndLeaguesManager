import { Component, Inject } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, NgForm, ValidatorFn, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Foot, Player, Position, PositionDetail } from 'src/app/models/Player/player';
import { PlayerService } from 'src/app/services/playerService/player.service';
import { Validator } from 'src/app/security/validators/validator';

@Component({
  selector: 'app-add-player-popup',
  templateUrl: './add-player-popup.component.html',
  styleUrls: ['./add-player-popup.component.css']
})
export class AddPlayerPopupComponent {
  form: FormGroup = this.formBuilder.group({
    firstName: ['', Validators.compose([this.firstNameAndLastNameValidator()])],
    lastName: ['', Validators.compose([this.firstNameAndLastNameValidator()])],
    heightInCm: ['', Validators.compose([this.heightValidator()])]
  });

  isFirstnameCorrect: boolean = false;
  isFirstnameFocused: boolean = false;
  isLastnameCorrect: boolean = false;
  isLastnameFocused: boolean = false;
  isDateOfBirthCorrect: boolean = false;
  isDateOfBirthFocused: boolean = false;
  isHeightInCmCorrect: boolean = false;
  isHeightInCmFocused: boolean = false;
  isPlayerFootSelected: boolean = false;
  isPlayerFootOpened: boolean = false;
  isPlayerPositionOpened: boolean = false;
  isPlayerPositionSelected: boolean = false;
  isPlayerPositionDetailOpened: boolean = false;
  isPlayerPositionDetailSelected: boolean = false;

  isAddPlayerButtonEnable: boolean = false;

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

  constructor(public dialogRef: MatDialogRef<AddPlayerPopupComponent>, private playerService: PlayerService, @Inject(MAT_DIALOG_DATA) public teamId: number, private formBuilder: FormBuilder) {
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

  private isFieldNotEmpty(field: string): boolean {
    return field.trim() !== '';
  }
  
  heightValidator(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      return Validator.isHeightValid(control.value) ? null : { invalidHeightInCm: { value: control.value } };
    };
  }
  
  firstNameAndLastNameValidator(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      return Validator.isFirstOrLastNameValid(control.value)? null : { invalidFirstOrLastName: { value: control.value } };
    };
  }

  dateOfBirthValidator() {
    this.dateOfBirth === undefined ? this.isDateOfBirthCorrect = false : this.isDateOfBirthCorrect = Validator.isDateOfBirthValid(this.dateOfBirth);
  }

  onPlayerFootChange() {
    this.isPlayerFootSelected = this.selectedFoot !== null;
  }

  onPlayerPositionChange() {
    this.isPlayerPositionSelected = this.selectedPosition !== null;
    this.selectedPosition === 'GOALKEEPER' ? this.isPlayerPositionDetailSelected = true : this.isPlayerPositionDetailSelected = false;
  }

  onPlayerPositionDetailChange() {
    this.isPlayerPositionDetailSelected = this.selectedPositionDetail !== null;
  }

  onInputChange(inputId: string, inputValue: string) {
    switch(inputId) {
      case 'firstName':
        {
          if(this.isFieldNotEmpty(inputValue)){
            Validator.isFirstOrLastNameValid(inputValue) ? this.isFirstnameCorrect = true : this.isFirstnameCorrect = false;
          }else{
            this.isFirstnameCorrect = false;
          }
        }
        break;
      case 'lastName':
        {
          if(this.isFieldNotEmpty(inputValue)){
            Validator.isFirstOrLastNameValid(inputValue) ? this.isLastnameCorrect = true : this.isLastnameCorrect = false;
          }else{
            this.isLastnameCorrect = false;
          }
        }
        break;
      case 'heightInCm':
        {
          if(this.isFieldNotEmpty(inputValue)){
            Validator.isHeightValid(inputValue) ? this.isHeightInCmCorrect = true : this.isHeightInCmCorrect = false;
          }else{
            this.isHeightInCmCorrect = false;
          }
        }
        break;
    }

    this.isAddPlayerButtonEnable = this.isFirstnameCorrect && this.isLastnameCorrect && this.isHeightInCmCorrect;
  }

}
