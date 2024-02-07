import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA, MatDialogConfig } from '@angular/material/dialog';

@Component({
  selector: 'app-team-remove-information',
  templateUrl: './team-remove-information.component.html',
  styleUrls: ['./team-remove-information.component.css']
})
export class TeamRemoveInformationComponent {
  competitionName: string;
  teamName: string;
  warning: boolean;

  constructor(public dialogRef: MatDialogRef<TeamRemoveInformationComponent>, @Inject(MAT_DIALOG_DATA) public data: any) {
    this.competitionName = data.competitonName;
    this.teamName = data.teamName;
    this.warning = data.warning;
  }

  ngOnInit() {
    const matDialogConfig = new MatDialogConfig();
    matDialogConfig.position = {top: `170px`};
    this.dialogRef.updatePosition(matDialogConfig.position);
  }

  closePopup(){
    if (this.warning)
      this.dialogRef.close('success');
    else
      this.dialogRef.close();
  }
}