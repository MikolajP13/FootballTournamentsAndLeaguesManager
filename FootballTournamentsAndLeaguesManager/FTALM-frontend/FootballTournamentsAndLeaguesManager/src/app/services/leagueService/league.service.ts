import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { TournamentLeagueBase } from 'src/app/models/TournamentLeagueBase/tournamentLeagueBase';
import { League, LeagueTeam } from 'src/app/models/League/league';

@Injectable({
  providedIn: 'root'
})
export class LeagueService {
  private apiServerUrl = environment.apiBaseUrl;

  constructor(private httpClient: HttpClient) { }

  findAllLeaguesByUserId(userId: number): Observable<TournamentLeagueBase[]>{
    return this.httpClient.get<TournamentLeagueBase[]>(`${this.apiServerUrl}/league/all/${userId}`);
  }

  findLeagueById(leagueId: number): Observable<League>{
    return this.httpClient.get<League>(`${this.apiServerUrl}/league/find/${leagueId}`); 
  }

  findActiveLeagueForTeam(teamId: number): Observable<League>{
    return this.httpClient.get<League>(`${this.apiServerUrl}/league/active/${teamId}`);
  }

  addLeague(League: League): Observable<League>{
    return this.httpClient.post<League>(`${this.apiServerUrl}/league/add`, League);
  }

  addTeamToLeague(leagueTeam: LeagueTeam): Observable<String> {
    return this.httpClient.post<String>(`${this.apiServerUrl}/league/addTeam`, leagueTeam);
  }

  deleteLeague(leagueId: number): Observable<Boolean>{
    return this.httpClient.delete<Boolean>(`${this.apiServerUrl}/league/delete/${leagueId}`);
  }
}
