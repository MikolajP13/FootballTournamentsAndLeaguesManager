import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Status, TournamentLeagueBase } from 'src/app/models/TournamentLeagueBase/tournamentLeagueBase';
import { User } from 'src/app/models/User/user';
import { LeagueService } from 'src/app/services/leagueService/league.service';
import { UserService } from 'src/app/services/userService/user.service';
import { CreateLeaguePopupComponent } from '../popups/create-league-popup/create-league-popup.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-leagues',
  templateUrl: './leagues.component.html',
  styleUrls: ['./leagues.component.css']
})
export class LeaguesComponent {
  authUser!: User;
  notStarted: Status = Status.NOT_STARTED;
  inProgress: Status = Status.IN_PROGRESS;
  finished: Status = Status.FINISHED;
  standard: string = 'STANDARD_MODE';
  split: string = 'SPLIT_MODE';

  displayedColumns: string[] = ['leagueName', 'type', 'numberOfTeams', 'startDate', 'endDate', 'status', 'details'];
  leagueDataSource:TournamentLeagueBase[] = [];

  constructor(private router: Router, private leagueService: LeagueService, private dialog: MatDialog) { }

  ngOnInit() {
    this.authUser = UserService.getUser();
    if(this.authUser.id !== undefined)
      this.leagueService.findAllLeaguesByUserId(this.authUser.id).subscribe((leagues: TournamentLeagueBase[]) => {
        this.leagueDataSource = [...leagues];
      });
  }

  showLeagueDeatils(league: TournamentLeagueBase): void {
    this.router.navigate(['/league/' + league.id]);
  }

  openCreateLeaguePopup() {
    const dialogRef = this.dialog.open(CreateLeaguePopupComponent, {data: this.authUser.id});

    dialogRef.afterClosed().subscribe(result => {
      if(result === 'success'){
        this.fetchLastTournamentData();
      }
    });
  }

  private fetchLastTournamentData() {
    this.authUser = UserService.getUser();

    if(this.authUser.id !== undefined)
      this.leagueService.findAllLeaguesByUserId(this.authUser.id).subscribe((leagues: TournamentLeagueBase[]) => {
        const lastLeague = leagues[leagues.length-1];
        this.leagueDataSource = [...this.leagueDataSource, lastLeague];
      });
  }
}
