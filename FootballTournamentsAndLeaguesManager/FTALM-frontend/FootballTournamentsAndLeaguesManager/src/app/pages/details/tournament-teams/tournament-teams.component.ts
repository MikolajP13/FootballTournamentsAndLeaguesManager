import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { TeamService } from '../../../services/teamService/team.service';
import { User } from 'src/app/models/User/user';
import { UserService } from '../../../services/userService/user.service';
import { Team } from 'src/app/models/Team/team';
import { ActivatedRoute, Router } from '@angular/router';
import { TournamentService } from '../../../services/tournamentService/tournament.service';
import { Tournament, TournamentTeam } from 'src/app/models/Tournament/tournament';
import { PlayerService } from 'src/app/services/playerService/player.service';
import { Player } from 'src/app/models/Player/player';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { TeamRemoveInformationComponent } from '../../popups/team-remove-information/team-remove-information.component';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-tournament-teams',
  templateUrl: './tournament-teams.component.html',
  styleUrls: ['./tournament-teams.component.css']
})
export class TournamentTeamsComponent {
  @ViewChild('input') input!: ElementRef<HTMLInputElement>;
  tournamentId!: string;
  tournament!: Tournament;
  authUser!: User;
  teamControl = new FormControl();
  teamsDataSource!: Team[];
  filteredTeams!: Team[];
  selectedTeam!: Team;
  isTeamSelected: boolean = false;
  numberOfTeams!: number;
  maximumNumberOfTeams!: number;

  displayedColumns: string[] = ['teamName', 'established', 'numberOfPlayers', 'details', 'remove'];
  tournamentTeamDataSource: MatTableDataSource<Team> = new MatTableDataSource();

  @ViewChild('tournamentTeamsPaginator', {static: true}) tournamentTeamsPaginator: MatPaginator | null = null;

  constructor(private teamService: TeamService, private tournamentService: TournamentService, private dialog: MatDialog,
    private playerService: PlayerService, private route: ActivatedRoute, private router: Router) { }

  ngOnInit() { 
    this.authUser = UserService.getUser();
    if(this.authUser.id !== undefined){
      this.fetchTeamData(this.authUser.id);
    }
    
    this.route.params.subscribe(params => {
        this.tournamentId = params['tournamentId'];
    });

    this.getCurrentTournament(parseInt(this.tournamentId));
    this.fetchTournamentTeamsData(parseInt(this.tournamentId));
  }

  ngAfterViewInit() {
    this.tournamentTeamDataSource.paginator = this.tournamentTeamsPaginator;
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

  addTeamToTournament() {
    const teamId = this.selectedTeam.id ?? 0;

    const tournamentTeam: TournamentTeam = {
      teamId: teamId,
      tournamentId: parseInt(this.tournamentId)
    }

    this.tournamentService.addTeamToTournament(tournamentTeam).subscribe(result => { });

    this.teamService.updateIsInTournament(teamId, true).subscribe(result => { });
    
    window.location.reload();
  }

  removeTeam(team: Team) { 
    if(team.id)
      this.teamService.removeTeamFromTournament(team.id, parseInt(this.tournamentId)).subscribe(result => { 
        const dialogRef = this.dialog.open(TeamRemoveInformationComponent, {
          data: { 
            competitonName: this.tournament.name,
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

  getCurrentTournament(tournamentId: number){
    this.tournamentService.findTournamentById(tournamentId).subscribe(tournament => {
      if(tournament.numberOfTeams)
        this.maximumNumberOfTeams = tournament.numberOfTeams;

      this.tournament = tournament;
    });
  }

  showTeamDetails(team: Team) {
    this.router.navigate(['/team/' + team.id]);
  }

  private fetchTournamentTeamsData(teamId: number) {
    this.teamService.findAllTeamsInTournamentByTournamentId(teamId).subscribe((teams: Team[]) => {
        this.tournamentTeamDataSource.data = [...this.tournamentTeamDataSource.data, ...teams];
        this.tournamentTeamDataSource.data.forEach(team => {
          if (team.id)
            this.playerService.getAllPlayersByTeamId(team.id).subscribe((players: Player[]) => {
              team.numberOfPlayers = players.length;
            });     
        });
        this.numberOfTeams = this.tournamentTeamDataSource.data.length;
    });
  }

  private fetchTeamData(userId: number) {
    this.teamService.findAllTeamsNotInTournament(userId).subscribe((teams: Team[]) => {
      this.teamsDataSource = [...teams];
    });
  }
}
