import { Component, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Player } from 'src/app/models/Player/player';
import { PlayerService } from '../../../services/playerService/player.service';
import { MatDialog } from '@angular/material/dialog';
import { AddPlayerPopupComponent } from '../../popups/add-player-popup/add-player-popup.component';
import { PlayerDetailsPopupComponent } from '../../popups/player-details-popup/player-details-popup.component';
import { DeletePlayerPopupComponent } from '../../popups/delete-player-popup/delete-player-popup.component';
import { EditPlayerPopupComponent } from '../../popups/edit-player-popup/edit-player-popup.component';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-team-players',
  templateUrl: './team-players.component.html',
  styleUrls: ['./team-players.component.css']
})
export class TeamPlayersComponent {
  teamId!: number;
  positionOrder = ['GK', 'LB', 'LCB', 'CB', 'RCB', 'RB', 'LM', 'CDM', 'CM', 'RM', 'LW', 'CAM', 'RW', 'LF', 'CF', 'RF'];

  teamPlayersDataSource: MatTableDataSource<Player> = new MatTableDataSource();
  displayedColumns: string[] = ['position', 'positionDetail', 'fullName', 'doBAndAge', 'height', 'foot', 'joined', 'details' , 'edit', 'delete'];

  @ViewChild('paginatorTeamPlayers', {static: true}) paginatorTeamPlayers: MatPaginator | null = null;

  constructor(private playerService: PlayerService, private route: ActivatedRoute, private dialog: MatDialog) { }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.teamId = params['teamId'];
    });

    this.fetchTeamPlayersData(this.teamId);
  }

  ngAfterViewInit() {
    this.teamPlayersDataSource.paginator = this.paginatorTeamPlayers; 
  }

  fetchTeamPlayersData(teamId: number) {
    this.playerService.getAllPlayersByTeamId(teamId).subscribe((players: Player[]) => {
      this.teamPlayersDataSource.data = [...players];
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
    this.dialog.open(PlayerDetailsPopupComponent, {data: player});
  }
  
  showPlayerEdit(player: Player) {
    const dialogRef = this.dialog.open(EditPlayerPopupComponent, {data: player});
    
    dialogRef.afterClosed().subscribe(result => {
      if(result === 'success'){
        window.location.reload();
      }
    });
  }

  deletePlayer(player: Player) {
    this.dialog.open(DeletePlayerPopupComponent, {data: {
      objectId: player.id,
      teamId: this.teamId,
      position: {top: '-200px'}
    }
    });
  }

  private fetchLastPlayerData(){
    this.playerService.getAllPlayersByTeamId(this.teamId).subscribe((players: Player[]) => {
      const lastPlayer = players[players.length-1];
      this.teamPlayersDataSource.data = [...this.teamPlayersDataSource.data, lastPlayer];
      this.processPlayerDetails();
    });
  }

  private processPlayerDetails(){
    this.teamPlayersDataSource.data.forEach(player => {
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
