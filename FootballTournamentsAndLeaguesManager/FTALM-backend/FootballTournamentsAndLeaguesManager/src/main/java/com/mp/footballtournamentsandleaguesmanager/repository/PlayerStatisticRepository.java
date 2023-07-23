package com.mp.footballtournamentsandleaguesmanager.repository;

import com.mp.footballtournamentsandleaguesmanager.model.PlayerStatistic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface PlayerStatisticRepository extends JpaRepository<PlayerStatistic, Long> {
    Optional<List<PlayerStatistic>> findAllByPlayerId(Long playerId);
    Optional<List<PlayerStatistic>> findAllByPlayerIdAndLeagueId(Long playerId, Long leagueId);
    Optional<List<PlayerStatistic>> findAllByPlayerIdAndTournamentId(Long playerId, Long tournamentId);
    Optional<PlayerStatistic> findByPlayerId(Long playerId);
}
