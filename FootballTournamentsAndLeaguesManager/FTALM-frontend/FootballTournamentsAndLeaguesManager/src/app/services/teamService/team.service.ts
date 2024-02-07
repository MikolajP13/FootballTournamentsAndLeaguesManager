import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Team } from 'src/app/models/Team/team';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TeamService {
  private apiServerUrl = environment.apiBaseUrl;
  
  constructor(private httpClient: HttpClient) { }

  findAllTeamsByUserId(userId: number): Observable<Team[]> {
    return this.httpClient.get<Team[]>(`${this.apiServerUrl}/team/all/${userId}`);
  }

  findAllTeamsNotInTournament(userId: number): Observable<Team[]> {
    return this.httpClient.get<Team[]>(`${this.apiServerUrl}/team/all/not-in-tournament/${userId}`);
  }

  findAllTeamsNotInLeague(userId: number): Observable<Team[]> {
    return this.httpClient.get<Team[]>(`${this.apiServerUrl}/team/all/not-in-league/${userId}`);
  }

  findAllTeamsInTournamentByTournamentId(tournamentId: number): Observable<Team[]> {
    return this.httpClient.get<Team[]>(`${this.apiServerUrl}/team/all/tournament/${tournamentId}`);
  }

  findAllTeamsInLeagueByLeagueId(leagueId: number): Observable<Team[]> {
    return this.httpClient.get<Team[]>(`${this.apiServerUrl}/team/all/league/${leagueId}`);
  }

  getTeamById(teamId: number): Observable<Team> {
    return this.httpClient.get<Team>(`${this.apiServerUrl}/team/find/${teamId}`);
  }

  countTeamsByTournamentId(tournamentId: number): Observable<number> {
    return this.httpClient.get<number>(`${this.apiServerUrl}/team/countAll/tournament/${tournamentId}`);
  }

  countTeamsByLeagueId(leagueId: number): Observable<number> {
    return this.httpClient.get<number>(`${this.apiServerUrl}/team/countAll/league/${leagueId}`);
  }

  updateIsInTournament(teamId: number, isInTournament: boolean): Observable<Team> {
    return this.httpClient.put<Team>(`${this.apiServerUrl}/team/updateIsInTournament/${teamId}`, isInTournament);
  }

  updateIsInLeague(teamId: number, isInLeague: boolean): Observable<Team> {
    return this.httpClient.put<Team>(`${this.apiServerUrl}/team/updateIsInLeague/${teamId}`, isInLeague);
  }

  addTeam(team: Team): Observable<Team> {
    return this.httpClient.post<Team>(`${this.apiServerUrl}/team/add`, team);
  }

  removeTeamFromLeague(teamId: number, leagueId: number): Observable<Boolean> {
    return this.httpClient.delete<Boolean>(`${this.apiServerUrl}/team/${teamId}/remove-from-league/${leagueId}`);
  }

  removeTeamFromTournament(teamId: number, tournamentId: number): Observable<Boolean> {
    return this.httpClient.delete<Boolean>(`${this.apiServerUrl}/team/${teamId}/remove-from-tournament/${tournamentId}`);
  }

  deleteTam(teamId: number): Observable<Boolean> {
    return this.httpClient.delete<Boolean>(`${this.apiServerUrl}/team/delete/${teamId}`);
  }
}
