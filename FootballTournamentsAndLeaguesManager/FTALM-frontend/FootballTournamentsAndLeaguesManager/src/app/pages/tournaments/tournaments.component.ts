import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Router } from '@angular/router';
import { Status, TournamentLeagueBase } from 'src/app/models/TournamentLeagueBase/tournamentLeagueBase';
import { User } from 'src/app/models/User/user';
import { TournamentService } from 'src/app/services/tournamentService/tournament.service';
import { UserService } from 'src/app/services/userService/user.service';
import { CreateTournamentPopupComponent } from '../popups/create-tournament-popup/create-tournament-popup.component';

@Component({
  selector: 'app-tournaments',
  templateUrl: './tournaments.component.html',
  styleUrls: ['./tournaments.component.css']
})
export class TournamentsComponent {
  authUser!: User;
  notStarted: Status = Status.NOT_STARTED;
  inProgress: Status = Status.IN_PROGRESS;
  finished: Status = Status.FINISHED;
  groupAndKnockout: string = 'GROUP_AND_KNOCKOUT';
  singleElimination: string = 'SINGLE_ELIMINATION';

  displayedColumns: string[] = ['tournamentName', 'type', 'numberOfTeams', 'startDate', 'endDate', 'status', 'details'];
  tournamentDataSource: TournamentLeagueBase[] = [];

  constructor(private router: Router, private tournamentService: TournamentService, private dialog: MatDialog) { }

  ngOnInit() {
    this.authUser = UserService.getUser();
    if(this.authUser.id !== undefined)
      this.tournamentService.findAllTournamentsByUserId(this.authUser.id).subscribe((tournaments: TournamentLeagueBase[]) => {
        this.tournamentDataSource = [...tournaments];
      });
  }

  showTournamentDetails(tournament: TournamentLeagueBase): void {
    this.router.navigate(['/tournament/' + tournament.id]);
  }

  openCreateTournamentPopup() {
    const dialogRef = this.dialog.open(CreateTournamentPopupComponent, {data: this.authUser.id});

    dialogRef.afterClosed().subscribe(result => {
      if(result === 'success'){
        this.fetchLastTournamentData();
      }
    });
  }

  private fetchLastTournamentData() {
    this.authUser = UserService.getUser();

    if(this.authUser.id !== undefined)
      this.tournamentService.findAllTournamentsByUserId(this.authUser.id).subscribe((tournaments: TournamentLeagueBase[]) => {
        const lastTournament = tournaments[tournaments.length-1];
        this.tournamentDataSource = [...this.tournamentDataSource, lastTournament];
      });
  }

}
