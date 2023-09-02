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

  addTeam(team: Team): Observable<Team> {
    return this.httpClient.post<Team>(`${this.apiServerUrl}/team/add`, team);
  }

  deleteTam(teamId: number): Observable<Boolean> {
    return this.httpClient.delete<Boolean>(`${this.apiServerUrl}/team/delete/${teamId}`);
  }
}
