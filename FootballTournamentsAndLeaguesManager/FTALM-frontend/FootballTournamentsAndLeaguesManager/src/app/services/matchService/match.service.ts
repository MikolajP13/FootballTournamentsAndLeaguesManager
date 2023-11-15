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

  public createMatches(matches: Match[]): Observable<Match[]> {
    return this.httpClient.post<Match[]>(`${this.apiServerBaseUrl}/match/createAll`, matches);
  }

  public getMatchByHomeTeamIdAndAwayTeamIdAndLeagueId(homeTeamId: number, awayTeamId: number, leagueId: number): Observable<Match> {
    return this.httpClient.get<Match>(`${this.apiServerBaseUrl}/match/find/homeTeam/${homeTeamId}/awayTeam/${awayTeamId}/league/${leagueId}`);
  }

  public countAllByLeagueIdAndIsMatchProtocolCreated(leagueId: number): Observable<number> {
    return this.httpClient.get<number>(`${this.apiServerBaseUrl}/match/countCreatedProtocols/league/${leagueId}`);
  }

  public countAllByLeagueIdAndIsMatchProtocolNotCreated(leagueId: number): Observable<number> {
    return this.httpClient.get<number>(`${this.apiServerBaseUrl}/match/countNotCreatedProtocols/league/${leagueId}`);
  }

  public updateMatch(matchId: number, match: Match): Observable<Match> {
    return this.httpClient.put<Match>(`${this.apiServerBaseUrl}/match/update/${matchId}`, match);
  }

}
