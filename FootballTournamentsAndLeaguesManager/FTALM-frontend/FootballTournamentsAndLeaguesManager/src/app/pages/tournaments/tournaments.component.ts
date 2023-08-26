import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { TournamentLeagueBase } from 'src/app/models/TournamentLeagueBase/tournamentLeagueBase';
import { User } from 'src/app/models/User/user';
import { TournamentService } from 'src/app/services/tournamentService/tournament.service';
import { UserService } from 'src/app/services/userService/user.service';

@Component({
  selector: 'app-tournaments',
  templateUrl: './tournaments.component.html',
  styleUrls: ['./tournaments.component.css']
})
export class TournamentsComponent {
  authUser!: User;

  displayedColumns: string[] = ['tournamentName', 'numberOfTeams', 'startDate', 'status', 'details'];
  tournamentDataSource:TournamentLeagueBase[] = [];

  constructor(private router: Router, private tournamentService: TournamentService) { }

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
}
