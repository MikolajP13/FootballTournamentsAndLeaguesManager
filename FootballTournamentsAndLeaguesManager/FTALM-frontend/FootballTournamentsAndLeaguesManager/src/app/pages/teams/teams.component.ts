import { Component } from '@angular/core';
import { Team } from 'src/app/models/Team/team';
import { TeamService } from 'src/app/services/teamService/team.service';
import { UserService } from '../../services/userService/user.service';
import { User } from 'src/app/models/User/user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.css']
})
export class TeamsComponent {
  authUser!: User;

  displayedColumns: string[] = ['teamName', 'details'];
  teamDataSource:Team[] = [];

  constructor(private router: Router, private teamService: TeamService){ }

  ngOnInit(){
    this.authUser = UserService.getUser();

    if(this.authUser.id !== undefined)
      this.teamService.findAllTeamsByUserId(this.authUser.id).subscribe((teams: Team[]) => {
        this.teamDataSource = [...this.teamDataSource, ...teams];
      });
  }

  showTeamDetails(team: Team) {
    this.router.navigate(['/team/' + team.id]);
  }

}
