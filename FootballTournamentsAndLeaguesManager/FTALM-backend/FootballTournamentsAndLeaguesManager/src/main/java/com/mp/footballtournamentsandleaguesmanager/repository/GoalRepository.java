package com.mp.footballtournamentsandleaguesmanager.repository;

import com.mp.footballtournamentsandleaguesmanager.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GoalRepository extends JpaRepository<Goal, Long > {
    Optional<Integer> countGoalsByPlayerId(Long playerId);
    Optional<Integer> countGoalsByMatchId(Long matchId);
    Optional<List<Goal>> getAllByPlayerId(Long playerId);
    Optional<List<Goal>> getAllByMatchId(Long matchId);
}
