import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Tournament, TournamentTeam } from 'src/app/models/Tournament/tournament';
import { Status, TournamentLeagueBase } from 'src/app/models/TournamentLeagueBase/tournamentLeagueBase';
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

  findTournamentById(tournamentId: number): Observable<Tournament>{
    return this.httpClient.get<Tournament>(`${this.apiServerUrl}/tournament/find/${tournamentId}`);
  }

  findActiveTournamentforTeam(teamId: number): Observable<Tournament>{
    return this.httpClient.get<Tournament>(`${this.apiServerUrl}/tournament/active/${teamId}`);
  }

  addTournament(tournament: Tournament): Observable<Tournament>{
    return this.httpClient.post<Tournament>(`${this.apiServerUrl}/tournament/add`, tournament);
  }

  addTeamToTournament(tournamentTeam: TournamentTeam): Observable<String> {
    return this.httpClient.post<String>(`${this.apiServerUrl}/tournament/addTeam`, tournamentTeam);
  }

  deleteTournament(tournamentId: number): Observable<Boolean>{
    return this.httpClient.delete<Boolean>(`${this.apiServerUrl}/tournament/delete/${tournamentId}`);
  }

  getTournamentStatus(tournamentId: number): Observable<Status>{
    return this.httpClient.get<Status>(`${this.apiServerUrl}/tournament/getStatus/${tournamentId}`);
  }

  getNumberOfTeams(tournamentId: number): Observable<number>{
    return this.httpClient.get<number>(`${this.apiServerUrl}/tournament/getNumberOfTeams/${tournamentId}`);
  }

  updateTournamentStatusByTournamentId(tournamentId: number, tournamentStatus: Tournament): Observable<Tournament>{
    return this.httpClient.put<Tournament>(`${this.apiServerUrl}/tournament/updateStatus/${tournamentId}`, tournamentStatus);
  }

}
