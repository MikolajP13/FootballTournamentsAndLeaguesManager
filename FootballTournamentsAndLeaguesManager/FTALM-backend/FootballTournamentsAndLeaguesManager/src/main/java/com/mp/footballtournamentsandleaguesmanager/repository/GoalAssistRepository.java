package com.mp.footballtournamentsandleaguesmanager.repository;

import com.mp.footballtournamentsandleaguesmanager.DTO.PlayerAssistsDTO;
import com.mp.footballtournamentsandleaguesmanager.DTO.PlayerGoalsDTO;
import com.mp.footballtournamentsandleaguesmanager.model.GoalAssist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GoalAssistRepository extends JpaRepository<GoalAssist, Long> {
    Optional<Integer> countGoalsByScorerPlayerId(Long scorerPlayerId);
    Optional<Integer> countAssistsByAssistPlayerId(Long assistPlayerId);
    Optional<List<GoalAssist>> getAllByScorerPlayerId(Long scorerPlayerId);
    Optional<List<GoalAssist>> getAllByAssistPlayerId(Long assistPlayerId);
    Optional<List<GoalAssist>> getAllByMatchId(Long matchId);
    @Query("SELECT new com.mp.footballtournamentsandleaguesmanager.DTO.PlayerGoalsDTO(p.id, " +
            "p.firstName, p.lastName, t.id, t.name, COUNT(g.id)) " +
            "FROM GoalAssist g INNER JOIN g.match m INNER JOIN g.team t INNER JOIN g.scorerPlayer p " +
            "WHERE m.league.id = :leagueId " +
            "GROUP BY p.id, t.id " +
            "ORDER BY COUNT(g.id) DESC")
    Optional<List<PlayerGoalsDTO>> getPlayersGoalsByLeagueId(Long leagueId);
    @Query("SELECT new com.mp.footballtournamentsandleaguesmanager.DTO.PlayerAssistsDTO(p.id, " +
            "p.firstName, p.lastName, t.id, t.name, COUNT(g.id)) " +
            "FROM GoalAssist g INNER JOIN g.match m INNER JOIN g.team t INNER JOIN g.assistPlayer p " +
            "WHERE m.league.id = :leagueId " +
            "GROUP BY p.id, t.id " +
            "ORDER BY COUNT(g.id) DESC")
    Optional<List<PlayerAssistsDTO>> getPlayersAssistsByLeagueId(Long leagueId);
}
