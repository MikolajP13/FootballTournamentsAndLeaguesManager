import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Substitution } from 'src/app/models/Substitution/substitution';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SubstitutionService {
  private apiServerUrl = environment.apiBaseUrl;
  
  constructor(private httpClient: HttpClient) { }

  addSubstitution(substitution: Substitution): Observable<Substitution> {
    return this.httpClient.post<Substitution>(`${this.apiServerUrl}/substitution/add`, substitution);
  }

  addSubstitutions(substitutions: Substitution[]): Observable<Substitution[]> {
    return this.httpClient.post<Substitution[]>(`${this.apiServerUrl}/substitution/addAll`, substitutions);
  }

  deleteSubstitution(substitutionId: number): Observable<Boolean> {
    return this.httpClient.delete<Boolean>(`${this.apiServerUrl}/substitution/delete/${substitutionId}`);
  }

  updateSubstitution(substitutionId: number, substitution: Substitution): Observable<Substitution> {
    return this.httpClient.put(`${this.apiServerUrl}/substitution/update/${substitutionId}`, substitution);
  }

  findAllSubstitutionsInMatch(matchId: number): Observable<Substitution[]> {
    return this.httpClient.get<Substitution[]>(`${this.apiServerUrl}/substitution/findAll/match/${matchId}`);
  }

  findAllByMatchIdAndTeamId(matchId: number, teamId: number): Observable<Substitution[]> {
    return this.httpClient.get<Substitution[]>(`${this.apiServerUrl}/substitution/findAll/match/${matchId}/team/${teamId}`);
  }

  countAllByMatchIdAndTeamId(matchId: number, teamId: number): Observable<number> {
    return this.httpClient.get<number>(`${this.apiServerUrl}/substitution/countAll/match/${matchId}/team/${teamId}`);
  }

}
