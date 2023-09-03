import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Tournament } from 'src/app/models/Tournament/tournament';
import { TournamentLeagueBase } from 'src/app/models/TournamentLeagueBase/tournamentLeagueBase';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TournamentService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private httpClient: HttpClient) { }

  findAllTournamentsByUserId(userId: number): Observable<TournamentLeagueBase[]>{
    return this.httpClient.get<TournamentLeagueBase[]>(`${this.apiServerUrl}/tournament/all/${userId}`);
  }

  findActiveTournamentforTeam(teamId: number): Observable<Tournament>{
    return this.httpClient.get<Tournament>(`${this.apiServerUrl}/tournament/active/${teamId}`);
  }
}
