package com.mp.footballtournamentsandleaguesmanager.repository;

import com.mp.footballtournamentsandleaguesmanager.model.Assist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AssistRepository extends JpaRepository<Assist, Long> {
    Optional<Integer> countAssistsByPlayerId(Long playerId);
    Optional<Integer> countAssistsByMatchId(Long matchId);
    Optional<List<Assist>> getAllByPlayerId(Long playerId);
    Optional<List<Assist>> getAllByMatchId(Long matchId);
}
