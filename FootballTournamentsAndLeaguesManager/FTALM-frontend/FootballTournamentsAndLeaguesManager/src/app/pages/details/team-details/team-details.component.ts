import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Player, Position } from 'src/app/models/Player/player';
import { Team } from 'src/app/models/Team/team';
import { PlayerService } from 'src/app/services/playerService/player.service';
import { TeamService } from 'src/app/services/teamService/team.service';
import { LeagueService } from '../../../services/leagueService/league.service';
import { TournamentService } from 'src/app/services/tournamentService/tournament.service';
import { TournamentLeagueBase } from 'src/app/models/TournamentLeagueBase/tournamentLeagueBase';
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
  
  displayedColumns: string[] = ['position', 'numberOfPlayers'];
  playersByPosition: PositionNumber[] = [];

  activeLeague!: League;
  activeTournament!: Tournament;

  constructor(private teamService: TeamService, private playerService: PlayerService, private leagueService: LeagueService, 
    private tournamentService: TournamentService, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.teamId = params['id'];
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
      this.getPlayersByPosition();
    });
  }

  findActiveLeagueForTeam(teamId: number){
    this.leagueService.findActiveLeagueForTeam(teamId).subscribe(activeLeague => {
      console.log(activeLeague);
      this.activeLeague = activeLeague;
    });
  }

  findActiveTournament(teamId: number) {
    this.tournamentService.findActiveTournamentforTeam(teamId).subscribe(activeTournament => {
      this.activeTournament = activeTournament
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
  
}

interface PositionNumber {
  position: string;
  numberOfPlayers: number;
}