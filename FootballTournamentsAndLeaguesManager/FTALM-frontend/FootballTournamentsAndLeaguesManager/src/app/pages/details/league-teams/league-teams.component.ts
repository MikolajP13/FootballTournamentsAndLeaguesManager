import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { ActivatedRoute, Router } from '@angular/router';
import { League, LeagueTeam } from 'src/app/models/League/league';
import { Player } from 'src/app/models/Player/player';
import { Team } from 'src/app/models/Team/team';
import { User } from 'src/app/models/User/user';
import { LeagueService } from 'src/app/services/leagueService/league.service';
import { PlayerService } from 'src/app/services/playerService/player.service';
import { TeamService } from 'src/app/services/teamService/team.service';
import { UserService } from 'src/app/services/userService/user.service';
import { MatTableDataSource } from '@angular/material/table';
import { MatDialog } from '@angular/material/dialog';
import { TeamRemoveInformationComponent } from '../../popups/team-remove-information/team-remove-information.component';

@Component({
  selector: 'app-league-teams',
  templateUrl: './league-teams.component.html',
  styleUrls: ['./league-teams.component.css']
})
export class LeagueTeamsComponent {
  @ViewChild('input') input!: ElementRef<HTMLInputElement>;
  leagueId!: string;
  league!: League;
  authUser!: User;
  teamControl = new FormControl();
  teamsDataSource!: Team[];
  filteredTeams!: Team[];
  selectedTeam!: Team;
  isTeamSelected: boolean = false;
  numberOfTeams!: number;
  maximumNumberOfTeams!: number;

  displayedColumns: string[] = ['teamName', 'established', 'numberOfPlayers', 'details', 'remove'];
  leagueTeamDataSource: MatTableDataSource<Team> = new MatTableDataSource();

  @ViewChild('leagueTeamsPaginator', {static: true}) leagueTeamsPaginator: MatPaginator | null = null;

  constructor(private teamService: TeamService, private leagueService: LeagueService, private dialog: MatDialog,
    private playerService: PlayerService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() { 
    this.authUser = UserService.getUser();
    if(this.authUser.id !== undefined){
      this.fetchTeamData(this.authUser.id);
    }
    
    this.route.params.subscribe(params => {
        this.leagueId = params['leagueId'];
    });

    this.getCurrentLeague(parseInt(this.leagueId));
    this.fetchLeagueTeamsData(parseInt(this.leagueId));
  }

  ngAfterViewInit() {
    this.leagueTeamDataSource.paginator = this.leagueTeamsPaginator;
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

  removeTeam(team: Team) {
    if(team.id)
      this.teamService.removeTeamFromLeague(team.id, parseInt(this.leagueId)).subscribe(result => { 
        const dialogRef = this.dialog.open(TeamRemoveInformationComponent, {
            data: { 
              competitonName: this.league.name,
              teamName: team.name,
              warning: result
            }});
        
        dialogRef.afterClosed().subscribe(result => {
          if(result === 'success'){
            window.location.reload();
          }
        });
      });
  }

  getCurrentLeague(tournamentId: number){
    this.leagueService.findLeagueById(tournamentId).subscribe(league => {
      if(league.numberOfTeams)
        this.maximumNumberOfTeams = league.numberOfTeams;

      this.league = league;
    });
  }

  showTeamDetails(team: Team) {
    this.router.navigate(['/team/' + team.id]);
  }

  private fetchLeagueTeamsData(teamId: number) {
    this.teamService.findAllTeamsInLeagueByLeagueId(teamId).subscribe((teams: Team[]) => {
        this.leagueTeamDataSource.data = [...this.leagueTeamDataSource.data, ...teams];
        this.leagueTeamDataSource.data.forEach(team => {
          if (team.id)
            this.playerService.getAllPlayersByTeamId(team.id).subscribe((players: Player[]) => {
              team.numberOfPlayers = players.length;
            });     
        });
        this.numberOfTeams = this.leagueTeamDataSource.data.length;
    });
  }

  private fetchTeamData(userId: number) {
    this.teamService.findAllTeamsNotInLeague(userId).subscribe((teams: Team[]) => {
      this.teamsDataSource = [...teams];
    });
  }
}
