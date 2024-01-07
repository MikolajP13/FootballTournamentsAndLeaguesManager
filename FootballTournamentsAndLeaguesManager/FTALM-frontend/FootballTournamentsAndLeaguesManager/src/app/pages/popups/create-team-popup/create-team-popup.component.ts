import { Component, Inject } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { Team } from 'src/app/models/Team/team';
import { TeamService } from 'src/app/services/teamService/team.service';

@Component({
  selector: 'app-create-team-popup',
  templateUrl: './create-team-popup.component.html',
  styleUrls: ['./create-team-popup.component.css']
})
export class CreateTeamPopupComponent {
  name!: string;
  isTeamNameCorrect: boolean = false;
  isTeamNameFocused: boolean = false;

  constructor(public dialogRef: MatDialogRef<CreateTeamPopupComponent>, private teamService: TeamService, @Inject(MAT_DIALOG_DATA) public userId: number) {}

  createTeam(teamForm: NgForm): void {
    const team: Team = {
      user: {id: this.userId},
      name: teamForm.value.name,
      captainId: null,
      isInLeague: false,
      isInTournament: false,
      established: new Date()
    }

    this.teamService.addTeam(team).subscribe(result => { });

    this.dialogRef.close('success');
  }

  onInputChange(inputValue: string) {
    if (inputValue.trim() !== '')
      this.isTeamNameCorrect = true;
    else
      this.isTeamNameCorrect = false;
  }

  closePopup(){
    this.dialogRef.close();
  }
}
