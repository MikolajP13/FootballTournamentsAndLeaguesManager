package com.mp.footballtournamentsandleaguesmanager.repository;

import com.mp.footballtournamentsandleaguesmanager.model.League;
import com.mp.footballtournamentsandleaguesmanager.model.TournamentLeagueBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LeagueRepository extends JpaRepository<League, Long> {
    Optional<List<League>> findAllByUserId(Long userId);
    @Query("SELECT l FROM League l " +
            "JOIN l.teams t " +
            "WHERE t.id = :teamId AND l.endDate IS NULL")
    League findActiveLeagueForTeam(Long teamId);
    @Query("SELECT l.status FROM League l WHERE l.id = :leagueId")
    TournamentLeagueBase.Status getStatusByLeagueId(Long leagueId);
    @Query("SELECT l.numberOfTeams FROM League l WHERE l.id = :leagueId")
    Optional<Integer> getNumberOfTeamsByLeagueId(Long leagueId);

}
