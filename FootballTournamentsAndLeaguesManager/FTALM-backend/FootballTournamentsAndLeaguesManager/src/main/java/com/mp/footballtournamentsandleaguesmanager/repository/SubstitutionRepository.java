package com.mp.footballtournamentsandleaguesmanager.repository;

import com.mp.footballtournamentsandleaguesmanager.model.Substitution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubstitutionRepository extends JpaRepository<Substitution, Long> {
    Optional<List<Substitution>> getAllByMatchId(Long matchId);
    Optional<List<Substitution>> getAllByMatchIdAndTeamId(Long matchId, Long teamId);
    Optional<Integer> countSubstitutionsByMatchIdAndTeamId(Long matchId, Long teamId);
}
