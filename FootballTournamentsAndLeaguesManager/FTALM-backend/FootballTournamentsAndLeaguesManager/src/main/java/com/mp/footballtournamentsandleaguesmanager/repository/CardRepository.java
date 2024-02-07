package com.mp.footballtournamentsandleaguesmanager.repository;

import com.mp.footballtournamentsandleaguesmanager.DTO.PlayerCardsDTO;
import com.mp.footballtournamentsandleaguesmanager.DTO.TeamCardsDTO;
import com.mp.footballtournamentsandleaguesmanager.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Integer> countCardsByPlayerId(Long playerId);
    Optional<Integer> countCardsByPlayerIdAndCardType(Long playerId, Card.Type cardType);
    Optional<Integer> countCardsByMatchId(Long matchId);
    Optional<List<Card>> getAllByPlayerId(Long playerId);
    Optional<List<Card>> getAllByMatchId(Long matchId);
    @Query("SELECT new com.mp.footballtournamentsandleaguesmanager.DTO.PlayerCardsDTO(p.id, " +
            "p.firstName, p.lastName, t.id, t.name, " +
            "SUM(CASE WHEN c.cardType = 0 THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN c.cardType = 2 THEN 1 ELSE 0 END)) " +
            "FROM Card c INNER JOIN c.match m INNER JOIN c.team t INNER JOIN c.player p " +
            "WHERE m.league.id = :leagueId " +
            "GROUP BY p.id, t.id " +
            "ORDER BY " +
            "SUM(CASE WHEN c.cardType = 0 THEN 1 ELSE 0 END) DESC")
    Optional<List<PlayerCardsDTO>> getPlayersYellowCardsByLeagueId(Long leagueId);
    @Query("SELECT new com.mp.footballtournamentsandleaguesmanager.DTO.PlayerCardsDTO(p.id, " +
            "p.firstName, p.lastName, t.id, t.name, " +
            "SUM(CASE WHEN c.cardType = 0 THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN c.cardType = 2 THEN 1 ELSE 0 END)) " +
            "FROM Card c INNER JOIN c.match m INNER JOIN c.team t INNER JOIN c.player p " +
            "WHERE m.league.id = :leagueId " +
            "GROUP BY p.id, t.id " +
            "ORDER BY " +
            "SUM(CASE WHEN c.cardType = 2 THEN 1 ELSE 0 END) DESC")
    Optional<List<PlayerCardsDTO>> getPlayersRedCardsByLeagueId(Long leagueId);
    @Query("SELECT new com.mp.footballtournamentsandleaguesmanager.DTO.PlayerCardsDTO(p.id, " +
            "p.firstName, p.lastName, t.id, t.name, " +
            "SUM(CASE WHEN c.cardType = 0 THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN c.cardType = 2 THEN 1 ELSE 0 END)) " +
            "FROM Card c INNER JOIN c.match m INNER JOIN c.team t INNER JOIN c.player p " +
            "WHERE m.tournament.id = :tournamentId " +
            "GROUP BY p.id, t.id " +
            "ORDER BY " +
            "SUM(CASE WHEN c.cardType = 0 THEN 1 ELSE 0 END) DESC")
    Optional<List<PlayerCardsDTO>> getPlayersYellowCardsByTournamentId(Long tournamentId);
    @Query("SELECT new com.mp.footballtournamentsandleaguesmanager.DTO.PlayerCardsDTO(p.id, " +
            "p.firstName, p.lastName, t.id, t.name, " +
            "SUM(CASE WHEN c.cardType = 0 THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN c.cardType = 2 THEN 1 ELSE 0 END)) " +
            "FROM Card c INNER JOIN c.match m INNER JOIN c.team t INNER JOIN c.player p " +
            "WHERE m.tournament.id = :tournamentId " +
            "GROUP BY p.id, t.id " +
            "ORDER BY " +
            "SUM(CASE WHEN c.cardType = 2 THEN 1 ELSE 0 END) DESC")
    Optional<List<PlayerCardsDTO>> getPlayersRedCardsByTournamentId(Long tournamentId);
    @Query("SELECT new com.mp.footballtournamentsandleaguesmanager.DTO.TeamCardsDTO(t.id, t.name, " +
            "SUM(CASE WHEN c.cardType = 0 THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN c.cardType = 2 THEN 1 ELSE 0 END)) " +
            "FROM Card c INNER JOIN c.match m INNER JOIN c.team t " +
            "WHERE m.league.id = :leagueId AND t.id = :teamId " +
            "GROUP BY t.id")
    Optional<TeamCardsDTO> getCardsOverallByLeagueIdAndTeamId(Long leagueId, Long teamId);
    Optional<Card> getCardByPlayerIdAndMatchId(Long playerId, Long matchId);
}
