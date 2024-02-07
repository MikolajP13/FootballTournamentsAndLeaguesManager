package com.mp.footballtournamentsandleaguesmanager.repository;

import com.mp.footballtournamentsandleaguesmanager.model.LeagueStanding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeagueStandingRepository extends JpaRepository<LeagueStanding, Long> {
    Optional<List<LeagueStanding>> getLeagueStandingByLeagueId(Long leagueId);
    Optional<List<LeagueStanding>> getLeagueStandingsByLeagueIdOrderByGoalsForDesc(Long leagueId);
    Optional<List<LeagueStanding>> getLeagueStandingsByLeagueIdOrderByWinsDesc(Long leagueId);
    Optional<List<LeagueStanding>> getLeagueStandingsByLeagueIdOrderByLossesDesc(Long leagueId);
    Optional<LeagueStanding> findByLeagueIdAndTeamId(Long leagueId, Long teamId);
}
