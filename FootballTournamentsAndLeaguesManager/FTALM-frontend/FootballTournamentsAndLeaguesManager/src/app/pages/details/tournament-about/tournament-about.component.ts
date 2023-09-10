import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { ConfirmationPopupComponent } from '../../popups/confirmation-popup/confirmation-popup.component';

@Component({
  selector: 'app-tournament-about',
  templateUrl: './tournament-about.component.html',
  styleUrls: ['./tournament-about.component.css']
})
export class TournamentAboutComponent {
  tournamentId!: number;
  objectName: string = 'tournament';

  constructor(private route: ActivatedRoute, private dialog: MatDialog) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.tournamentId = params['id'];
    });
  }

  openConfirmationPopup(){
    this.dialog.open(ConfirmationPopupComponent, {data: {
      id: this.tournamentId,
      objectName: this.objectName,
    }
    });
  }

  startTournament() {
    console.log("Start tournament " + this.tournamentId);
    //TODO: DB update tournament status to IN_PROGRESS
    //TODO: tournament can only be started once!!
    //TODO: logic for creating matches etc.
  }
}
