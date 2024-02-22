import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { ConfirmationPopupComponent } from '../../popups/confirmation-popup/confirmation-popup.component';
import { TournamentService } from 'src/app/services/tournamentService/tournament.service';
import { TeamService } from 'src/app/services/teamService/team.service';
import { Tournament, Type } from 'src/app/models/Tournament/tournament';
import { Status } from 'src/app/models/TournamentLeagueBase/tournamentLeagueBase';
import { Team } from 'src/app/models/Team/team';
import { StandingsUtils } from 'src/app/utils/StandingUtils';
import { TournamentStandingService } from 'src/app/services/tournamentStandingService/tournament-standing.service';
import { TournamentStanding } from 'src/app/models/TournamentStanding/tournamentStanding';
import { Match } from '../../../models/Match/match';
import { MatchService } from 'src/app/services/matchService/match.service';

@Component({
  selector: 'app-tournament-about',
  templateUrl: './tournament-about.component.html',
  styleUrls: ['./tournament-about.component.css']
})
export class TournamentAboutComponent {
  tournamentId!: number;
  objectName: string = 'tournament';
  tournament!: Tournament;
  teams!: Team[];
  tournamentCanBeStarted: boolean = false;
  tournamentCanBeDeletedWithWarning: boolean = false;

  constructor(private route: ActivatedRoute, private dialog: MatDialog, private teamService: TeamService, private matchService: MatchService,
    private tournamentService: TournamentService, private tournamentStandingService: TournamentStandingService) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.tournamentId = params['tournamentId'];
    });

    this.tournamentService.findTournamentById(this.tournamentId).subscribe(tournament => {
      this.tournament = tournament;
    });

    this.tournamentService.getTournamentStatus(this.tournamentId).subscribe(status => {
      if(status === Status.FINISHED || status === Status.NOT_STARTED)
        this.tournamentCanBeDeletedWithWarning = false;
      else
        this.tournamentCanBeDeletedWithWarning = true;
    });

    this.tournamentService.getNumberOfTeams(this.tournamentId).subscribe(result1 => {
      this.teamService.countTeamsByTournamentId(this.tournamentId).subscribe(result2 => {
        if (result1 === result2 && this.tournament.status === Status.NOT_STARTED)
          this.tournamentCanBeStarted = true;
        else
          this.tournamentCanBeStarted = false;
      });
    });
  }

  openConfirmationPopup(){
    this.dialog.open(ConfirmationPopupComponent, {data: {
      id: this.tournamentId,
      objectName: this.objectName,
      warning: this.tournamentCanBeDeletedWithWarning,
      position: {top: '-200px'}
    }
    });
  }

  startTournament() {    
    this.tournamentCanBeStarted = false;
    this.tournamentService.startTournament(this.tournamentId).subscribe();
  }

}
