import { Component, Inject } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, NgForm, ValidatorFn, Validators } from '@angular/forms';
import { MatDialogConfig, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Tournament, Type } from 'src/app/models/Tournament/tournament';
import { Status } from 'src/app/models/TournamentLeagueBase/tournamentLeagueBase';
import { Validator } from 'src/app/security/validators/validator';
import { TournamentService } from 'src/app/services/tournamentService/tournament.service';

@Component({
  selector: 'app-create-tournament-popup',
  templateUrl: './create-tournament-popup.component.html',
  styleUrls: ['./create-tournament-popup.component.css']
})
export class CreateTournamentPopupComponent {
  
  form: FormGroup = this.formBuilder.group({
    name: ['', Validators.compose([this.competitionNameValidator()])],
    numberOfTeams: ['', Validators.compose([this.numberOfTeamsValidator()])]
  });
  
  name!: string;
  startDate!: Date;
  numberOfTeams!: number;
  selectedType!: string;
  types = Object.values(Type).filter(value => typeof value === 'string');

  isCompetitionNameFocused: boolean = false;
  isCompetitionNameCorrect: boolean = false;
  isNumberOfTeamsFocused: boolean = false;
  isNumberOfTeamsCorrect: boolean = false;
  isStartDateFocused: boolean = false;
  isStartDateCorrect: boolean = false;
  isTypeSelected: boolean = false;

  isAddTournamentButtonEnable: boolean = false;

  constructor(public dialogRef: MatDialogRef<CreateTournamentPopupComponent>, private tournamentService: TournamentService, @Inject(MAT_DIALOG_DATA) public userId: number, private formBuilder: FormBuilder) { }

  ngOnInit() {
    const matDialogConfig = new MatDialogConfig();
    matDialogConfig.position = {top: '170px'};
    this.dialogRef.updatePosition(matDialogConfig.position);
  }

  createTournament(tournamentForm: NgForm) {
    let typeId: number;

    if(this.selectedType === 'Single Elimination'){
      typeId = Object.values(Type).indexOf(Type.SINGLE_ELIMINATION);
    } else{
        typeId = Object.values(Type).indexOf(Type.GROUP_AND_KNOCKOUT);
    }
    // else if(this.selectedType === 'Group and Knockout'){
    //   typeId = Object.values(Type).indexOf(Type.GROUP_AND_KNOCKOUT);
    // } else{
    //   typeId = Object.values(Type).indexOf(Type.DOUBLE_ELIMINATION);
    // }

    const tournament: Tournament = {
      user: {id: this.userId},
      name: tournamentForm.value.name,
      numberOfTeams: tournamentForm.value.numberOfTeams,
      startDate: tournamentForm.value.startDate,
      status: Status.NOT_STARTED,
      type: typeId as unknown as Type
    }

    this.tournamentService.addTournament(tournament).subscribe(result => { });

    this.dialogRef.close('success');
  }

  competitionNameValidator(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      return Validator.isCompetitionNameValid(control.value) ? null : { invalidCompetitionName: { value: control.value } };
    };
  }

  numberOfTeamsValidator(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      return Validator.isTournamentNumberOfTeamsValid(control.value) ? null : { invalidNumberOfTeams: { value: control.value } };
    };
  }

  competitionStartDateValidator() {
    this.startDate === undefined ? this.isStartDateCorrect = false : this.isStartDateCorrect = Validator.isCompetitionStartDateValid(this.startDate);
  }

  onTypeChange() {
    this.selectedType !== null ? this.isTypeSelected = true : this.isTypeSelected = false;
  }

  private isFieldNotEmpty(field: string): boolean {
    return field.trim() !== '';
  }

  onInputChange(inputId: string, inputValue: string) {
    switch(inputId) {
      case 'name':
        {
          if(this.isFieldNotEmpty(inputValue)){
            Validator.isCompetitionNameValid(inputValue) ? this.isCompetitionNameCorrect = true : this.isCompetitionNameCorrect = false;
          }else{
            this.isCompetitionNameCorrect = false;
          }
        }
        break;
      case 'numberOfTeams':
        {
          if(this.isFieldNotEmpty(inputValue)){
            Validator.isTournamentNumberOfTeamsValid(inputValue) ? this.isNumberOfTeamsCorrect = true : this.isNumberOfTeamsCorrect = false;
          }else{
            this.isNumberOfTeamsCorrect = false;
          }
        }
        break;
    }

    this.isAddTournamentButtonEnable = this.isCompetitionNameCorrect && this.isNumberOfTeamsCorrect ;
  }

  closePopup() {
    this.dialogRef.close();
  }
}
