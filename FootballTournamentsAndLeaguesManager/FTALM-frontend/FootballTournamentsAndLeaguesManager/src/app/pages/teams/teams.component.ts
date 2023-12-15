import { Component, ViewChild } from '@angular/core';
import { Team } from 'src/app/models/Team/team';
import { TeamService } from 'src/app/services/teamService/team.service';
import { UserService } from '../../services/userService/user.service';
import { User } from 'src/app/models/User/user';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { CreateTeamPopupComponent } from '../popups/create-team-popup/create-team-popup.component';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-teams',
  templateUrl: './teams.component.html',
  styleUrls: ['./teams.component.css']
})
export class TeamsComponent {
  authUser!: User;

  displayedColumns: string[] = ['teamName', 'established', 'league', 'tournament', 'details'];
  teamDataSource: MatTableDataSource<Team> = new MatTableDataSource();

  @ViewChild(MatPaginator, { static: true }) paginator: MatPaginator | null = null;

  constructor(private router: Router, private teamService: TeamService, private dialog: MatDialog) { }

  ngOnInit() {
    this.fetchTeamsData();
  }

  ngAfterViewInit() {
    this.teamDataSource.paginator = this.paginator;
  }

  showTeamDetails(team: Team) {
    this.router.navigate(['/team/' + team.id]);
  }

  openCreateTeamPopup() {
    const dialogRef = this.dialog.open(CreateTeamPopupComponent, { data: this.authUser.id });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 'success') {
        this.fetchLastTeamData();
      }
    });
  }

  private fetchTeamsData() {
    this.authUser = UserService.getUser();

    if (this.authUser.id !== undefined) {
      this.teamService.findAllTeamsByUserId(this.authUser.id).subscribe((teams: Team[]) => {
        this.teamDataSource.data = [...this.teamDataSource.data, ...teams];
      });
    }
  }

  private fetchLastTeamData() {
    this.authUser = UserService.getUser();

    if (this.authUser.id !== undefined) {
      this.teamService.findAllTeamsByUserId(this.authUser.id).subscribe((teams: Team[]) => {
        const lastTeam = teams[teams.length - 1];
        this.teamDataSource.data = [...this.teamDataSource.data, lastTeam];
      });
    }
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value.toLowerCase();
  
    this.teamDataSource.filterPredicate = (data: Team, filter: string) => {
      return data.name.toLowerCase().includes(filter);
    };
  
    this.teamDataSource.filter = filterValue;
  }
}