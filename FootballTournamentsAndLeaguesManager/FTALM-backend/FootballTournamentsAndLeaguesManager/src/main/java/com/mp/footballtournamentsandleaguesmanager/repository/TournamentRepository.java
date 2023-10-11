package com.mp.footballtournamentsandleaguesmanager.repository;

import com.mp.footballtournamentsandleaguesmanager.model.Tournament;
import com.mp.footballtournamentsandleaguesmanager.model.TournamentLeagueBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    Optional<List<Tournament>> findAllByUserId(Long userId);
    @Query("SELECT t FROM Tournament t " +
            "JOIN t.teams t2 " +
            "WHERE t2.id = :teamId AND t.endDate IS NULL")
    Optional<Tournament> findActiveTournamentForTeam(Long teamId);
    @Query("SELECT t.status FROM Tournament t WHERE t.id = :tournamentId")
    TournamentLeagueBase.Status getTournamentStatusByTournamentId(Long tournamentId);
    @Query("SELECT t.numberOfTeams FROM Tournament t WHERE t.id = :tournamentId")
    Optional<Integer> getNumberOfTeamsByTournamentId(Long tournamentId);

}
