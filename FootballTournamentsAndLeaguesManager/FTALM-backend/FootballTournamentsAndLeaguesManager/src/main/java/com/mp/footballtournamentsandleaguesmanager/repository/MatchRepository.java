package com.mp.footballtournamentsandleaguesmanager.repository;

import com.mp.footballtournamentsandleaguesmanager.model.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchRepository extends JpaRepository<Match, Long> {
    Optional<List<Match>> getAllByLeagueId(Long leagueId);
    Optional<List<Match>> getAllByTournamentId(Long tournamentId);
    Optional<List<Match>> getAllByTournamentIdAndRoundAndMatchweek(Long tournamentId, int round, int matchweek);
    Optional<List<Match>> getAllByTournamentIdAndRoundIsGreaterThanEqual(Long tournamentId, int firstRound);
    Optional<Match> getMatchByHomeTeamIdAndAwayTeamIdAndIsMatchProtocolCreatedAndLeagueId(Long homeTeamId, Long awayTeamId, boolean matchProtocolCreated, Long leagueId);
    Optional<Integer> countMatchesByLeagueId(Long leagueId);
    Optional<Integer> countAllByLeagueIdAndIsMatchProtocolCreated(Long leagueId, boolean matchProtocolCreated);
}
