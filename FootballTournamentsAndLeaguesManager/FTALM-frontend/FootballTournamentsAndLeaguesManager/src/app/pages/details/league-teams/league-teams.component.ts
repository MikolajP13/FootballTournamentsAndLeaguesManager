import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { LeagueTeam } from 'src/app/models/League/league';
import { Team } from 'src/app/models/Team/team';
import { User } from 'src/app/models/User/user';
import { LeagueService } from 'src/app/services/leagueService/league.service';
import { TeamService } from 'src/app/services/teamService/team.service';
import { UserService } from 'src/app/services/userService/user.service';

@Component({
  selector: 'app-league-teams',
  templateUrl: './league-teams.component.html',
  styleUrls: ['./league-teams.component.css']
})
export class LeagueTeamsComponent {
  @ViewChild('input') input!: ElementRef<HTMLInputElement>;
  leagueId!: string;

  authUser!: User;
  teamControl = new FormControl();
  teamsDataSource!: Team[];
  filteredTeams!: Team[];
  selectedTeam!: Team;
  isTeamSelected: boolean = false;
  numberOfTeams!: number;
  maximumNumberOfTeams!: number;

  displayedColumns: string[] = ['teamName', 'details'];
  leagueTeamDataSource: Team[] = [];

  constructor(private teamService: TeamService, private leagueService: LeagueService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() { 
    this.authUser = UserService.getUser();
    if(this.authUser.id !== undefined){
      this.fetchTeamData(this.authUser.id);
    }
    
    this.route.params.subscribe(params => {
        this.leagueId = params['id'];
    });

    this.getCurrentLeague(parseInt(this.leagueId));
    this.fetchLeagueTeamsData(parseInt(this.leagueId));
  }

  filter(): void {
    const filterValue = this.input.nativeElement.value.toLowerCase();
    this.filteredTeams = this.teamsDataSource.filter(team => team.name?.toLowerCase().includes(filterValue));
  }

  displayTeamName(team: Team) {
    return team && team.name ? team.name : '';
  }

  selectTeam() {
    this.selectedTeam = this.teamControl.value;
    this.isTeamSelected = true;
  }

  addTeamToLeague() {
    const teamId = this.selectedTeam.id ?? 0;

    const leagueTeam: LeagueTeam = {
      teamId: teamId,
      leagueId: parseInt(this.leagueId)
    }

    this.leagueService.addTeamToLeague(leagueTeam).subscribe(result => { });

    this.teamService.updateIsInLeague(teamId, true).subscribe(result => { });
    
    window.location.reload();
  }

  getCurrentLeague(tournamentId: number){
    this.leagueService.findLeagueById(tournamentId).subscribe(league => {
      this.maximumNumberOfTeams = league.numberOfTeams;
    });
  }

  showTeamDetails(team: Team) {
    this.router.navigate(['/team/' + team.id]);
  }

  private fetchLeagueTeamsData(teamId: number) {
    this.teamService.findAllTeamsInLeaguetByLeagueId(teamId).subscribe((teams: Team[]) => {
        this.leagueTeamDataSource = [...this.leagueTeamDataSource, ...teams];
        this.numberOfTeams = this.leagueTeamDataSource.length;
    });
  }

  private fetchTeamData(userId: number) {
    this.teamService.findAllTeamsNotInLeague(userId).subscribe((teams: Team[]) => {
      this.teamsDataSource = [...teams];
    });
  }
}
