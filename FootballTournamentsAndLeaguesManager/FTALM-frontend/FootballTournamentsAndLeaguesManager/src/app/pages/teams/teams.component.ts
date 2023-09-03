import { Component } from '@angular/core';
import { Team } from 'src/app/models/Team/team';
import { TeamService } from 'src/app/services/teamService/team.service';
import { UserService } from '../../services/userService/user.service';
import { User } from 'src/app/models/User/user';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { CreateTeamPopupComponent } from '../popups/create-team-popup/create-team-popup.component';

@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.css']
})
export class TeamsComponent {
  authUser!: User;

  displayedColumns: string[] = ['teamName', 'league', 'tournament', 'details'];
  teamDataSource:Team[] = [];

  constructor(private router: Router, private teamService: TeamService, private dialog: MatDialog){ }

  ngOnInit(){
    this.fetchTeamsData();
  }

  showTeamDetails(team: Team) {
    this.router.navigate(['/team/' + team.id]);
  }

  openCreateTeamPopup(){
    const dialogRef = this.dialog.open(CreateTeamPopupComponent, {data: this.authUser.id});
    this.teamDataSource.forEach((team)=>{console.log(team.isInLeague);});
    dialogRef.afterClosed().subscribe(result => {
      if(result === 'success'){
        this.fetchLastTeamData();
      }
    });
  }

  private fetchTeamsData(){
    this.authUser = UserService.getUser();

    if(this.authUser.id !== undefined)
      this.teamService.findAllTeamsByUserId(this.authUser.id).subscribe((teams: Team[]) => {
        this.teamDataSource = [...this.teamDataSource, ...teams];
      });
  }

  private fetchLastTeamData(){
    this.authUser = UserService.getUser();

    if(this.authUser.id !== undefined)
      this.teamService.findAllTeamsByUserId(this.authUser.id).subscribe((teams: Team[]) => {
        const lastTeam = teams[teams.length-1];
        this.teamDataSource = [...this.teamDataSource, lastTeam];
      });
  }
}
