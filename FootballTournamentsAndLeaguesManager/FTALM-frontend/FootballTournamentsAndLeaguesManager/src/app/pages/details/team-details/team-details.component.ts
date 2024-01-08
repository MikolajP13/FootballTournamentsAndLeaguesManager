import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Player } from 'src/app/models/Player/player';
import { Team } from 'src/app/models/Team/team';
import { PlayerService } from 'src/app/services/playerService/player.service';
import { TeamService } from 'src/app/services/teamService/team.service';
import { LeagueService } from '../../../services/leagueService/league.service';
import { TournamentService } from 'src/app/services/tournamentService/tournament.service';
import { League } from 'src/app/models/League/league';
import { Tournament } from 'src/app/models/Tournament/tournament';

@Component({
  selector: 'app-team-details',
  templateUrl: './team-details.component.html',
  styleUrls: ['./team-details.component.css']
})
export class TeamDetailsComponent {
  teamId!: number;
  team!: Team;
  teamDataSource!: Player[];
  numberOfPlayers!: number;
  sumAge!: number;
  sumHeight!: number;
  
  displayedColumns: string[] = ['position', 'numberOfPlayers'];
  playersByPosition: PositionNumber[] = [];

  activeLeague!: League;
  isInLeague!: boolean;
  activeTournament!: Tournament;
  isInTournament!: boolean;

  constructor(private teamService: TeamService, private playerService: PlayerService, private leagueService: LeagueService, 
    private tournamentService: TournamentService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.teamId = params['teamId'];
    });

    this.getTeamById(this.teamId);
    this.getTeamPlayers(this.teamId);
    this.findActiveLeagueForTeam(this.teamId);
    this.findActiveTournament(this.teamId);
  }

  getTeamById(teamId: number){
    this.teamService.getTeamById(teamId).subscribe(team => {
      this.team = team;
    });
  }

  getTeamPlayers(teamId: number){
    this.playerService.getAllPlayersByTeamId(teamId).subscribe((players: Player[]) => {
      this.teamDataSource = [...players];
      this.numberOfPlayers = this.teamDataSource.length;
      this.sumAge = this.calculateSumAge(this.teamDataSource);
      this.sumHeight = this.teamDataSource.reduce((acc, player) => player.heightInCm !== undefined ? acc + player.heightInCm : acc, 0);
      this.getPlayersByPosition();
    });
  }

  calculateSumAge(teamDataSource: Player[]): number {
    let sum = 0;
    return teamDataSource.reduce((acc, player) => player.dateOfBirth !== undefined ? acc + this.calculateAge(player.dateOfBirth) : acc, sum);
  }

  findActiveLeagueForTeam(teamId: number){
    this.leagueService.findActiveLeagueForTeam(teamId).subscribe(activeLeague => {
      if (activeLeague.id === null) {
        this.isInLeague = false;
      } else {
        this.isInLeague = true;
        this.activeLeague = activeLeague;
      }
    });
  }

  findActiveTournament(teamId: number) {
    this.tournamentService.findActiveTournamentforTeam(teamId).subscribe(activeTournament => {
      if (activeTournament.id === null) {
        this.isInTournament = false;
      } else {
        this.isInTournament = true;
        this.activeTournament = activeTournament
      }
    });
  }

  getPlayersByPosition(){
    let counterGk: number = 0;
    let counterDef: number = 0;
    let counterMid: number = 0;
    let counterFor: number = 0;
  
    this.teamDataSource.forEach((player: Player) => {
     
      if(player.position?.toString() == 'GOALKEEPER')
        counterGk++;
      else if (player.position?.toString() === 'DEFENDER')
        counterDef++;
      else if (player.position?.toString() === 'MIDFIELDER')
        counterMid++;
      else if (player.position?.toString() === 'FORWARD')
        counterFor++;
    });
  
    this.playersByPosition.push({position: 'Goalkeeper', numberOfPlayers: counterGk});
    this.playersByPosition.push({position: 'Defender', numberOfPlayers: counterDef});
    this.playersByPosition.push({position: 'Midfielder', numberOfPlayers: counterMid});
    this.playersByPosition.push({position: 'Forward', numberOfPlayers: counterFor});
  }
  
  //TODO duplicate for now
  private calculateAge(dateOfBirth: Date): number {
    const today = new Date();
    const birthDate = new Date(dateOfBirth);

    let age = today.getFullYear() - birthDate.getFullYear();
    const monthDiff = today.getMonth() - birthDate.getMonth();

    if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birthDate.getDate())) {
        age--;
    }

    return age;
  }

}

interface PositionNumber {
  position: string;
  numberOfPlayers: number;
}