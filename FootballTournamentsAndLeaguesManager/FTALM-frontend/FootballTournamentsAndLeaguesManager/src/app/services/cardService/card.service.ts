import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Card } from 'src/app/models/Card/card';
import { PlayerCards } from 'src/app/models/PlayerCards/playerCards';
import { TeamCards } from 'src/app/models/TeamCards/teamCards';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CardService {
  private apiServerUrl = environment.apiBaseUrl;
  
  constructor(private httpClient: HttpClient) { }

  addCard(card: Card): Observable<Card> {
    return this.httpClient.post<Card>(`${this.apiServerUrl}/card/add`, card);
  }

  addCards(cards: Card[]): Observable<Card[]> {
    return this.httpClient.post<Card[]>(`${this.apiServerUrl}/card/addAll`, cards);
  }

  deleteCard(cardId: number): Observable<Boolean> {
    return this.httpClient.delete<Boolean>(`${this.apiServerUrl}/card/delete/${cardId}`);
  }

  updateCard(cardId: number, card: Card): Observable<Card>{
    return this.httpClient.put<Card>(`${this.apiServerUrl}/card/update/${cardId}`, card);
  }

  findAllPlayerCards(playerId: number): Observable<Card[]> {
    return this.httpClient.get<Card[]>(`${this.apiServerUrl}/card/findAll/player/${playerId}`);
  }

  findAllCardsInMatch(matchId: number): Observable<Card[]> {
    return this.httpClient.get<Card[]>(`${this.apiServerUrl}/card/findAll/match/${matchId}`);
  }

  countAllPlayerCards(playerId: number): Observable<number> {
    return this.httpClient.get<number>(`${this.apiServerUrl}/card/countAll/player/${playerId}`);
  }

  countSpecificPlayerCards(playerId: number, cardTypeString: string): Observable<number>{
    return this.httpClient.get<number>(`${this.apiServerUrl}/card/countSpecific/player/${playerId}?cardTypeString=${cardTypeString}`);
  }

  getPlayersYellowCardsByLeagueId(leagueId: number): Observable<PlayerCards[]> {
    return this.httpClient.get<PlayerCards[]>(`${this.apiServerUrl}/card/getPlayersYellowCards/league/${leagueId}`);
  }

  getPlayersRedCardsByLeagueId(leagueId: number): Observable<PlayerCards[]>{
    return this.httpClient.get<PlayerCards[]>(`${this.apiServerUrl}/card/getPlayersRedCards/league/${leagueId}`);
  }

  getPlayersYellowCardsByTournamentId(tournamentId: number): Observable<PlayerCards[]> {
    return this.httpClient.get<PlayerCards[]>(`${this.apiServerUrl}/card/getPlayersYellowCards/tournament/${tournamentId}`);
  }

  getPlayersRedCardsByTournamentId(tournamentId: number): Observable<PlayerCards[]>{
    return this.httpClient.get<PlayerCards[]>(`${this.apiServerUrl}/card/getPlayersRedCards/tournament/${tournamentId}`);
  }

  getCardsOverallByLeagueIdAndTeamId(leagueId: number, teamId: number): Observable<TeamCards> {
    return this.httpClient.get<TeamCards>(`${this.apiServerUrl}/card/getTeamCards/league/${leagueId}/team/${teamId}`);
  }

  getCardByPlayerIdAndMatchId(playerId: number, matchId: number): Observable<Card> {
    return this.httpClient.get<Card>(`${this.apiServerUrl}/card/getPlayerCard/${playerId}/match/${matchId}`);
  }
}
