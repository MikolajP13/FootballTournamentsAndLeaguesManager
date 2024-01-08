import { Component, ElementRef, ViewChild } from '@angular/core';
import { FormControl } from '@angular/forms';
import { TeamService } from '../../../services/teamService/team.service';
import { User } from 'src/app/models/User/user';
import { UserService } from '../../../services/userService/user.service';
import { Team } from 'src/app/models/Team/team';
import { ActivatedRoute, Router } from '@angular/router';
import { TournamentService } from '../../../services/tournamentService/tournament.service';
import { Tournament, TournamentTeam } from 'src/app/models/Tournament/tournament';

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

  displayedColumns: string[] = ['teamName', 'details', 'remove'];
  tournamentTeamDataSource: Team[] = [];

  constructor(private teamService: TeamService, private tournamentService: TournamentService, private route: ActivatedRoute, private router: Router) { }

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
    console.log(team);
  }

  getCurrentTournament(tournamentId: number){
    this.tournamentService.findTournamentById(tournamentId).subscribe(tournament => {
      if(tournament.numberOfTeams)
        this.maximumNumberOfTeams = tournament.numberOfTeams;


        console.log(tournament);
      this.tournament = tournament;
    });
  }

  showTeamDetails(team: Team) {
    this.router.navigate(['/team/' + team.id]);
  }

  private fetchTournamentTeamsData(teamId: number) {
    this.teamService.findAllTeamsInTournamentByTournamentId(teamId).subscribe((teams: Team[]) => {
        this.tournamentTeamDataSource = [...this.tournamentTeamDataSource, ...teams];
        this.numberOfTeams = this.tournamentTeamDataSource.length;
    });
  }

  private fetchTeamData(userId: number) {
    this.teamService.findAllTeamsNotInTournament(userId).subscribe((teams: Team[]) => {
      this.teamsDataSource = [...teams];
    });
  }
}
