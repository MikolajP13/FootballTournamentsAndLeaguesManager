import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TournamentLeagueBase } from 'src/app/models/TournamentLeagueBase/tournamentLeagueBase';
import { League } from 'src/app/models/League/league';

@Injectable({
  providedIn: 'root'
})
export class LeagueService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private httpClient: HttpClient) { }

  findAllLeaguesByUserId(userId: number): Observable<TournamentLeagueBase[]>{
    return this.httpClient.get<TournamentLeagueBase[]>(`${this.apiServerUrl}/league/all/${userId}`);
  }

  findActiveLeagueForTeam(teamId: number): Observable<League>{
    return this.httpClient.get<League>(`${this.apiServerUrl}/League/active/${teamId}`);
  }

  addLeague(League: League): Observable<League>{
    return this.httpClient.post<League>(`${this.apiServerUrl}/league/add`, League);
  }

  deleteLeague(leagueId: number): Observable<Boolean>{
    return this.httpClient.delete<Boolean>(`${this.apiServerUrl}/league/delete/${leagueId}`);
  }
}
