package com.mp.footballtournamentsandleaguesmanager.repository;

import com.mp.footballtournamentsandleaguesmanager.model.TournamentStanding;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TournamentStandingRepository extends JpaRepository<TournamentStanding, Long> {
    Optional<List<TournamentStanding>> getTournamentStandingByTournamentIdAndGroupId(Long tournamentId, int groupId);
    Optional<TournamentStanding> findByTournamentIdAndGroupIdAndTeamId(Long tournamentId, int groupId, Long teamId);
    Optional<List<TournamentStanding>> getAllByTeamId(Long teamId);
}
