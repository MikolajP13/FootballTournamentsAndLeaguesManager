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
}
