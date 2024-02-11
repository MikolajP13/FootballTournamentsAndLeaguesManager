import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { LeagueStanding } from '../../models/LeagueStanding/leagueStanding';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LeagueStandingService {
  private apiServerBaseUrl = environment.apiBaseUrl;
  
  constructor(private httpClient: HttpClient) { }

  public addLeagueStanding(leagueStanding: LeagueStanding[]): Observable<LeagueStanding[]> {
    return this.httpClient.post<LeagueStanding[]>(`${this.apiServerBaseUrl}/leagueStanding/add`, leagueStanding);
  }

  public updateLeagueStanding(leagueId: number, teamId: number, leagueStanding: LeagueStanding): Observable<LeagueStanding> {
    return this.httpClient.put<LeagueStanding>(`${this.apiServerBaseUrl}/leagueStanding/update/league/${leagueId}/team/${teamId}`, leagueStanding);
  }

  public getLeagueStanding(leagueId: number): Observable<LeagueStanding[]> {
    return this.httpClient.get<LeagueStanding[]>(`${this.apiServerBaseUrl}/leagueStanding/all/${leagueId}`);
  }

  getLeagueStandingsByLeagueIdOrderByGoalsFor(leagueId: number): Observable<LeagueStanding[]> {
    return this.httpClient.get<LeagueStanding[]>(`${this.apiServerBaseUrl}/leagueStanding/getTeamsGoalsFor/${leagueId}`);
  }

  getLeagueStandingsByLeagueIdOrderByWins(leagueId: number):Observable<LeagueStanding[]> {
    return this.httpClient.get<LeagueStanding[]>(`${this.apiServerBaseUrl}/leagueStanding/getTeamsWins/${leagueId}`);
  }

  getLeagueStandingsByLeagueIdOrderByLosses(leagueId: number): Observable<LeagueStanding[]> {
    return this.httpClient.get<LeagueStanding[]>(`${this.apiServerBaseUrl}/leagueStanding/getTeamsLosses/${leagueId}`);
  }

  getAllByTeamId(teamId: number): Observable<LeagueStanding[]> {
    return this.httpClient.get<LeagueStanding[]>(`${this.apiServerBaseUrl}/leagueStanding/all/team/${teamId}`);
  }
  
}
