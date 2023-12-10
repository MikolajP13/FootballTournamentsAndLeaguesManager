import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TournamentStanding } from 'src/app/models/TournamentStanding/tournamentStanding';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TournamentStandingService {
  private apiServerBaseUrl = environment.apiBaseUrl;
  
  constructor(private httpClient: HttpClient) { }

  public addTournamentStanding(tournamentStanding: TournamentStanding[]): Observable<TournamentStanding[]>{
    return this.httpClient.post<TournamentStanding[]>(`${this.apiServerBaseUrl}/tournamentStanding/add`, tournamentStanding);
  }

  public getTournamentStandingByTournamentIdAndGroupId(tournamentId: number, groupId: number): Observable<TournamentStanding[]>{
    return this.httpClient.get<TournamentStanding[]>(`${this.apiServerBaseUrl}/tournamentStanding/all/tournament/${tournamentId}/group/${groupId}`);
  }

  public updateTournamentStanding(tournamentId: number, groupId: number, teamId: number, tournamentStanding: TournamentStanding): Observable<TournamentStanding> {
    return this.httpClient.put<TournamentStanding>(`${this.apiServerBaseUrl}/tournamentStanding/update/tournament/${tournamentId}/group/${groupId}/team/${teamId}`, tournamentStanding);
  }

}
