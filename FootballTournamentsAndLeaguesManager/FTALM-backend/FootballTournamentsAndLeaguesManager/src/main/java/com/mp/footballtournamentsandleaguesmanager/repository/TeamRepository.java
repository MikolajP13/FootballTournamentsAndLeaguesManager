package com.mp.footballtournamentsandleaguesmanager.repository;

import com.mp.footballtournamentsandleaguesmanager.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<List<Team>> findAllByUserId(Long userId);
    @Query("SELECT t FROM Team t WHERE t.user.id = :userId AND t.isInTournament = false")
    Optional<List<Team>> findAllTeamsNotInTournament(Long userId);
    @Query("SELECT t FROM Team t WHERE t.user.id = :userId AND t.isInLeague = false")
    Optional<List<Team>> findAllTeamsNotInLeague(Long userId);
    @Query("SELECT t FROM Team t " +
            "JOIN t.tournaments t2 " +
            "WHERE t2.id = :tournamentId")
    Optional<List<Team>> findAllTeamsInTournamentByTournamentId(Long tournamentId);
    @Query("SELECT t FROM Team t " +
            "JOIN t.leagues t2 " +
            "WHERE t2.id = :leagueId")
    Optional<List<Team>> findAllTeamsInLeagueByLeagueId(Long leagueId);
    Optional<Integer> countTeamsByLeaguesId(Long leagueId);
    Optional<Integer> countTeamsByTournamentsId(Long tournamentsId);
}
