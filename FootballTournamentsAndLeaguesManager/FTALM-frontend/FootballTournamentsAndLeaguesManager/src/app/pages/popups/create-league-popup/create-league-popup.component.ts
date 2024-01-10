import { Component, Inject } from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, NgForm, ValidatorFn, Validators } from '@angular/forms';
import { MatDialogConfig, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { League, Type } from 'src/app/models/League/league';
import { Status } from 'src/app/models/TournamentLeagueBase/tournamentLeagueBase';
import { Validator } from 'src/app/security/validators/validator';
import { LeagueService } from 'src/app/services/leagueService/league.service';

@Component({
  selector: 'app-create-league-popup',
  templateUrl: './create-league-popup.component.html',
  styleUrls: ['./create-league-popup.component.css']
})
export class CreateLeaguePopupComponent {
  form: FormGroup = this.formBuilder.group({
    name: ['', Validators.compose([this.competitionNameValidator()])],
    numberOfTeams: ['', Validators.compose([this.numberOfTeamsValidator()])]
  });

  name!: string;
  startDate!: Date;
  numberOfTeams!: number;
  selectedType!: Type;
  types = Object.values(Type).filter(value => typeof value === 'string');

  isCompetitionNameCorrect: boolean = false;
  isCompetitionNameFocused: boolean = false;
  isNumberOfTeamsCorrect: boolean = false;
  isNumberOfTeamsFocused: boolean = false;
  isStartDateCorrect: boolean = false;
  isStartDateFocused: boolean = false;
  isTypeSelected: boolean = false;

  isAddTournamentButtonEnable: boolean = false;

  constructor(public dialogRef: MatDialogRef<CreateLeaguePopupComponent>, private leagueService: LeagueService, @Inject(MAT_DIALOG_DATA) public userId: number, private formBuilder: FormBuilder) { }

  ngOnInit() {
    const matDialogConfig = new MatDialogConfig();
    matDialogConfig.position = {top: `170px`};
    this.dialogRef.updatePosition(matDialogConfig.position);
  }

  createLeague(leagueForm: NgForm) {
    var typeId: number;

    this.selectedType === 'Standard Mode' ?  typeId = Object.values(Type).indexOf(Type.STANDARD_MODE) : typeId = Object.values(Type).indexOf(Type.SPLIT_MODE);

    const league: League = {
      user: {id: this.userId},
      name: leagueForm.value.name,
      numberOfTeams: leagueForm.value.numberOfTeams,
      startDate: leagueForm.value.startDate,
      status: Status.NOT_STARTED,
      type: typeId as unknown as Type
    }

    this.leagueService.addLeague(league).subscribe(result => { });

    this.dialogRef.close('success');
  }

  competitionNameValidator(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      return Validator.isCompetitionNameValid(control.value) ? null : { invalidCompetitionName: { value: control.value } };
    };
  }

  numberOfTeamsValidator(): ValidatorFn {
    return (control: AbstractControl): { [key: string]: any } | null => {
      return Validator.isLeagueNumberOfTeamsValid(control.value) ? null : { invalidNumberOfTeams: { value: control.value } };
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
            Validator.isLeagueNumberOfTeamsValid(inputValue) ? this.isNumberOfTeamsCorrect = true : this.isNumberOfTeamsCorrect = false;
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
