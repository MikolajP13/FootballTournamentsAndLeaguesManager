import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Player } from 'src/app/models/Player/player';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PlayerService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private httpClient: HttpClient) { }

  getAllPlayersByTeamId(teamId: number): Observable<Player[]> {
    return this.httpClient.get<Player[]>(`${this.apiServerUrl}/player/all/${teamId}`);
  }

  addPlayer(player: Player): Observable<Player>{
    return this.httpClient.post<Player>(`${this.apiServerUrl}/player/add`, player);
  }

  updatePlayer(playerId: number, player: Player): Observable<Player>{
    return this.httpClient.patch<Player>(`${this.apiServerUrl}/player/${playerId}/update`, player);
  }

  deletePlayer(playerId: number): Observable<boolean> {
    return this.httpClient.delete<boolean>(`${this.apiServerUrl}/player/delete/${playerId}`);
  }
}
