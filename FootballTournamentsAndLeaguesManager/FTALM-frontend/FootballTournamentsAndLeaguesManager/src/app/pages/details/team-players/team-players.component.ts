import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Player } from 'src/app/models/Player/player';
import { PlayerService } from '../../../services/playerService/player.service';
import { MatDialog } from '@angular/material/dialog';
import { AddPlayerPopupComponent } from '../../popups/add-player-popup/add-player-popup.component';
import { PlayerDetailsPopupComponent } from '../../popups/player-details-popup/player-details-popup.component';

@Component({
  selector: 'app-team-players',
  templateUrl: './team-players.component.html',
  styleUrls: ['./team-players.component.css']
})
export class TeamPlayersComponent {
  teamId!: number;
  positionOrder = ['GK', 'LB', 'LCB', 'CB', 'RCB', 'RB', 'LM', 'CDM', 'CM', 'RM', 'LW', 'CAM', 'RW', 'LF', 'CF', 'RF'];

  teamPlayersDataSource: Player[] = [];
  displayedColumns: string[] = ['position', 'positionDetail', 'fullName', 'doBAndAge', 'height', 'foot', 'joined', 'details' , 'edit', 'delete'];

  constructor(private playerService: PlayerService, private route: ActivatedRoute, private dialog: MatDialog) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.teamId = params['teamId'];
    });

    this.fetchTeamPlayersData(this.teamId);
  }

  fetchTeamPlayersData(teamId: number) {
    this.playerService.getAllPlayersByTeamId(teamId).subscribe((players: Player[]) => {
      this.teamPlayersDataSource = [...players];
      this.sortTeamDataSource();
      this.processPlayerDetails();
    });
  }

  openAddPlayerPopup(){
    const dialogRef = this.dialog.open(AddPlayerPopupComponent, {data: this.teamId});

    dialogRef.afterClosed().subscribe(result => {
      if(result === 'success'){
        this.fetchLastPlayerData();
      }
    });
  }

  showPlayerDetails(player: Player) {
    // console.log(player.id); TODO
    this.dialog.open(PlayerDetailsPopupComponent, {data: player});
  }
  
  showPlayerEdit(player: Player) {
    console.log(player);
  }

  deletePlayer(player: Player) {
    console.log(player);
  }

  private fetchLastPlayerData(){
    this.playerService.getAllPlayersByTeamId(this.teamId).subscribe((players: Player[]) => {
      const lastPlayer = players[players.length-1];
      this.teamPlayersDataSource = [...this.teamPlayersDataSource, lastPlayer];
      this.sortTeamDataSource();
      this.processPlayerDetails();
    });
  }

  private sortTeamDataSource(){
    this.teamPlayersDataSource.sort((p1, p2) => {
      if(p1.positionDetail && p2.positionDetail){
        return this.positionOrder.indexOf(p1.positionDetail) - this.positionOrder.indexOf(p2.positionDetail);
      }else 
        return 0;
    });
  }

  private processPlayerDetails(){
    this.teamPlayersDataSource.forEach(player => {
      if(player.dateOfBirth){
        player.age = this.calculateAge(player.dateOfBirth);
      }
    });
  }

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
