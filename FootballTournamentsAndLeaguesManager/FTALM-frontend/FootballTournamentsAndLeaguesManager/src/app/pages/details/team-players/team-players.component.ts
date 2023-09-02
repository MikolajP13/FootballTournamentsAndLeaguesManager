import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Player } from 'src/app/models/Player/player';
import { PlayerService } from '../../../services/playerService/player.service';
import { MatDialog } from '@angular/material/dialog';
import { AddPlayerPopupComponent } from '../../popups/add-player-popup/add-player-popup.component';

@Component({
  selector: 'app-team-players',
  templateUrl: './team-players.component.html',
  styleUrls: ['./team-players.component.css']
})
export class TeamPlayersComponent {
  teamId!: number;
  teamDataSource: Player[] = [];
  displayedColumns: string[] = ['position', 'positionDetail', 'firstName', 'lastName', 'details'];

  constructor(private playerService: PlayerService, private route: ActivatedRoute, private dialog: MatDialog) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.teamId = params['id'];
    });

    this.fetchTeamPlayersData(this.teamId);
  }

  //TODO: sort by position!
  fetchTeamPlayersData(teamId: number) {
    this.playerService.getAllPlayersByTeamId(teamId).subscribe((players: Player[]) => {
      this.teamDataSource = [...players]; 
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

  private fetchLastPlayerData(){
    this.playerService.getAllPlayersByTeamId(this.teamId).subscribe((players: Player[]) => {
      const lastPlayer = players[players.length-1];
      this.teamDataSource = [...this.teamDataSource, lastPlayer]; 
    });
  }
}
