import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { GoalAssist } from 'src/app/models/GoalAssist/goalAssist';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class GoalAssistService {
  private apiServerUrl = environment.apiBaseUrl;
  
  constructor(private httpClient: HttpClient) { }

  addGoalAssist(goalAssist: GoalAssist): Observable<GoalAssist> {
    return this.httpClient.post<GoalAssist>(`${this.apiServerUrl}/goalAssist/add`, goalAssist);
  }

  deleteGoalAssist(goalAssistId: number): Observable<Boolean> {
    return this.httpClient.delete<Boolean>(`${this.apiServerUrl}/goalAssist/delete/${goalAssistId}`);
  }

  updateGoalAssist(goalAssistId: number, goalAssist: GoalAssist): Observable<GoalAssist> {
    return this.httpClient.put<GoalAssist>(`${this.apiServerUrl}/goalAssist/update/${goalAssistId}`, goalAssist);
  }

  findAllPlayerGoals(playerId: number): Observable<GoalAssist[]> {
    return this.httpClient.get<GoalAssist[]>(`${this.apiServerUrl}/goalAssist/findAll/goals/player/${playerId}`);
  }

  findAllPlayerAssists(playerId: number): Observable<GoalAssist[]> {
    return this.httpClient.get<GoalAssist[]>(`${this.apiServerUrl}/goalAssist/findAll/assists/player/${playerId}`);
  }

  findAllGoalAssistsInMatch(matchId: number): Observable<GoalAssist[]> {
    return this.httpClient.get<GoalAssist[]>(`${this.apiServerUrl}/goalAssist/findAll/match/${matchId}`);
  }

  countPlayerGoals(playerId: number): Observable<number>{
    return this.httpClient.get<number>(`${this.apiServerUrl}/goalAssist/count/player/goals/${playerId}`);
  }

  countPlayerAssists(playerId: number): Observable<number>{
    return this.httpClient.get<number>(`${this.apiServerUrl}/goalAssist/count/player/assists/${playerId}`);
  }

}
