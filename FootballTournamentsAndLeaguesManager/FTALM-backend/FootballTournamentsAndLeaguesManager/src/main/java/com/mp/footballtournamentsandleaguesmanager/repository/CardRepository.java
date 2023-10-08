package com.mp.footballtournamentsandleaguesmanager.repository;

import com.mp.footballtournamentsandleaguesmanager.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    Optional<Integer> countCardsByPlayerId(Long playerId);
    Optional<Integer> countCardsByPlayerIdAndCardType(Long playerId, Card.Type cardType);
    Optional<Integer> countCardsByMatchId(Long matchId);
    Optional<List<Card>> getAllByPlayerId(Long playerId);
    Optional<List<Card>> getAllByMatchId(Long matchId);
}
