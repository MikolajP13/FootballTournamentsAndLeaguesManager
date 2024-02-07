import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationPopupComponent } from '../../popups/confirmation-popup/confirmation-popup.component';
import { ActivatedRoute } from '@angular/router';
import { LeagueService } from 'src/app/services/leagueService/league.service';
import { TournamentService } from 'src/app/services/tournamentService/tournament.service';

@Component({
  selector: 'app-team-about',
  templateUrl: './team-about.component.html',
  styleUrls: ['./team-about.component.css']
})
export class TeamAboutComponent {
  teamId!: number;
  objectName: string = 'team';
  isInLeague!: boolean;
  isInTournament!: boolean;

  constructor(private route: ActivatedRoute, private leagueService: LeagueService, 
    private tournamentService: TournamentService, private dialog: MatDialog) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.teamId = params['teamId'];
    });

    this.tournamentService.findActiveTournamentforTeam(this.teamId).subscribe(activeTournament => {
      if (activeTournament.id === null) {
        this.isInTournament = false;
      } else {
        this.isInTournament = true;
      }
    });
    this.leagueService.findActiveLeagueForTeam(this.teamId).subscribe(activeLeague => {
      if (activeLeague.id === null) {
        this.isInTournament = false;
      } else {
        this.isInTournament = true;
      }
    });
  }

  openConfirmationPopup(){
    this.dialog.open(ConfirmationPopupComponent, {data: {
      id: this.teamId,
      objectName: this.objectName,
      disable: this.isInLeague || this.isInTournament
    }
    });
  }
}
