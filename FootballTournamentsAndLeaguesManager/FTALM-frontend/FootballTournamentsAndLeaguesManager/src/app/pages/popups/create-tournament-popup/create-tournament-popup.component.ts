import { Component, Inject } from '@angular/core';
import { NgForm } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Tournament } from 'src/app/models/Tournament/tournament';
import { Status } from 'src/app/models/TournamentLeagueBase/tournamentLeagueBase';
import { TournamentService } from 'src/app/services/tournamentService/tournament.service';

@Component({
  selector: 'app-create-tournament-popup',
  templateUrl: './create-tournament-popup.component.html',
  styleUrls: ['./create-tournament-popup.component.css']
})
export class CreateTournamentPopupComponent {
  name!: string;
  startDate!: Date;
  numberOfTeams!: number;

  constructor(public dialogRef: MatDialogRef<CreateTournamentPopupComponent>, private tournamentService: TournamentService, @Inject(MAT_DIALOG_DATA) public userId: number) { }

  createTournament(tournamentForm: NgForm) {
    const tournament: Tournament = {
      user: {id: this.userId},
      name: tournamentForm.value.name,
      numberOfTeams: tournamentForm.value.numberOfTeams,
      startDate: tournamentForm.value.startDate,
      status: Status.NOT_STARTED
    }

    this.tournamentService.addTournament(tournament).subscribe(result => { });

    this.dialogRef.close('success');
  }

  closePopup() {
    this.dialogRef.close();
  }
}
