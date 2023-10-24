import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Match } from 'src/app/models/Match/match';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MatchService {
  private apiServerBaseUrl = environment.apiBaseUrl;
  
  constructor(private httpClient: HttpClient) { }

  public getMatchByMatchId(matchId: number): Observable<Match> {
    return this.httpClient.get<Match>(`${this.apiServerBaseUrl}/match/find/${matchId}`);
  }

  public getLeagueMatchesByLeagueId(leagueId: number): Observable<Match[]> {
    return this.httpClient.get<Match[]>(`${this.apiServerBaseUrl}/match/findAll/league/${leagueId}`);
  }

  public createMatch(match: Match): Observable<Match> {
    return this.httpClient.post<Match>(`${this.apiServerBaseUrl}/match/create`, match);
  }

}
