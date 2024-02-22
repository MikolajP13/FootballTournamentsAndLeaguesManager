import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Status, TournamentLeagueBase } from 'src/app/models/TournamentLeagueBase/tournamentLeagueBase';
import { League, LeagueTeam } from 'src/app/models/League/league';
import { Type } from '../../models/League/league';

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

  getLeagueStatus(leagueId: number): Observable<Status>{
    return this.httpClient.get<Status>(`${this.apiServerUrl}/league/getStatus/${leagueId}`);
  }

  getNumberOfTeams(leagueId: number): Observable<number>{
    return this.httpClient.get<number>(`${this.apiServerUrl}/league/getNumberOfTeams/${leagueId}`);
  }

  getLeagueType(leagueId: number): Observable<Type> {
    return this.httpClient.get<Type>(`${this.apiServerUrl}/league/getType/${leagueId}`);
  }

  getLeagueName(leagueId: number): Observable<string>{
    const httpOptions : Object = {
      headers: new HttpHeaders({
        'Content-Type': 'text/plain; charset=utf-8'
      }),
      responseType: 'text'
    };

    return this.httpClient.get<string>(`${this.apiServerUrl}/league/getName/${leagueId}`, httpOptions);
  }

  updateLeagueStatusByLeagueId(leagueId: number, leagueStatus: League): Observable<League>{
    return this.httpClient.put<League>(`${this.apiServerUrl}/league/updateStatus/${leagueId}`, leagueStatus);
  }

  startLeague(leagueId: number): Observable<Boolean> {
    return this.httpClient.get<Boolean>(`${this.apiServerUrl}/league/start/${leagueId}`);
  }

  checkAndTryCompleteLeague(leagueId: number): Observable<boolean> {
    return this.httpClient.get<boolean>(`${this.apiServerUrl}/league/finish/${leagueId}`);
  }

}
