package com.mp.footballtournamentsandleaguesmanager.repository;

import com.mp.footballtournamentsandleaguesmanager.model.GoalAssist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GoalAssistRepository extends JpaRepository<GoalAssist, Long> {
    Optional<Integer> countGoalsByScorerPlayerId(Long scorerPlayerId);
    Optional<Integer> countAssistsByAssistPlayerId(Long assistPlayerId);
    Optional<List<GoalAssist>> getAllByScorerPlayerId(Long scorerPlayerId);
    Optional<List<GoalAssist>> getAllByAssistPlayerId(Long assistPlayerId);
    Optional<List<GoalAssist>> getAllByMatchId(Long matchId);
}
