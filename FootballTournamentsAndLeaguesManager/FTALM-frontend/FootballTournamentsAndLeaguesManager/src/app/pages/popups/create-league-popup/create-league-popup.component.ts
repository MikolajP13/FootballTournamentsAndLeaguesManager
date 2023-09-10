import { Component, Inject } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { League } from 'src/app/models/League/league';
import { Tournament } from 'src/app/models/Tournament/tournament';
import { Status } from 'src/app/models/TournamentLeagueBase/tournamentLeagueBase';
import { LeagueService } from 'src/app/services/leagueService/league.service';

@Component({
  selector: 'app-create-league-popup',
  templateUrl: './create-league-popup.component.html',
  styleUrls: ['./create-league-popup.component.css']
})
export class CreateLeaguePopupComponent {
  name!: string;
  startDate!: Date;
  numberOfTeams!: number;

  constructor(public dialogRef: MatDialogRef<CreateLeaguePopupComponent>, private leagueService: LeagueService, @Inject(MAT_DIALOG_DATA) public userId: number) { }

  createLeague(leagueForm: NgForm) {
    const league: League = {
      user: {id: this.userId},
      name: leagueForm.value.name,
      numberOfTeams: leagueForm.value.numberOfTeams,
      startDate: leagueForm.value.startDate,
      status: Status.NOT_STARTED
    }

    this.leagueService.addLeague(league).subscribe(result => { });

    this.dialogRef.close('success');
  }

  closePopup() {
    this.dialogRef.close();
  }
}
