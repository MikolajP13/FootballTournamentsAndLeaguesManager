package com.mp.footballtournamentsandleaguesmanager.controller;

import com.mp.footballtournamentsandleaguesmanager.DTO.PlayerStatisticDTO;
import com.mp.footballtournamentsandleaguesmanager.model.PlayerStatistic;
import com.mp.footballtournamentsandleaguesmanager.service.PlayerStatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playerstatistic")
public class PlayerStatisticController {
    private final PlayerStatisticService playerStatisticService;

    @Autowired
    public PlayerStatisticController(PlayerStatisticService playerStatisticService) {
        this.playerStatisticService = playerStatisticService;
    }

    @GetMapping("/all/{playerId}")
    public ResponseEntity<List<PlayerStatisticDTO>> getAllPlayerStatisticByPlayerId(@PathVariable Long playerId){
        List<PlayerStatisticDTO> playerStatistic = playerStatisticService.getAllPlayerStatisticByPlayerId(playerId);
        return new ResponseEntity<>(playerStatistic, HttpStatus.OK);
    }

    @GetMapping("/all/league/{playerId}/{leagueId}")
    public ResponseEntity<List<PlayerStatisticDTO>> getAllPlayerStatisticByPlayerIdAndLeagueId(@PathVariable Long playerId, @PathVariable Long leagueId){
        List<PlayerStatisticDTO> playerStatistic = playerStatisticService.getAllPlayerStatisticByPlayerIdAndLeagueId(playerId, leagueId);
        return new ResponseEntity<>(playerStatistic, HttpStatus.OK);
    }

    @GetMapping("/all/tournament/{playerId}/{tournamentId}")
    public ResponseEntity<List<PlayerStatisticDTO>> getAllPlayerStatisticByPlayerIdAndTournamentId(@PathVariable Long playerId, @PathVariable Long tournamentId) {
        List<PlayerStatisticDTO> playerStatistic = playerStatisticService.getAllPlayerStatisticByPlayerIdAndTournamentId(playerId, tournamentId);
        return new ResponseEntity<>(playerStatistic, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<PlayerStatistic> addPlayerStatistic(@RequestBody PlayerStatistic playerStatistic){
        PlayerStatistic newPlayerStatistic = playerStatisticService.addPlayerStatistic(playerStatistic);
        return new ResponseEntity<>(newPlayerStatistic, HttpStatus.CREATED);
    }

    @PutMapping("updateLeague/{playerId}/{leagueId}/{goals}")
    public ResponseEntity<Void> updatePlayerGoalsInLeagueByPlayerIdAndLeagueId(@PathVariable Long playerId, @PathVariable Long leagueId, @PathVariable int goals){
        return playerStatisticService.updatePlayerGoalsInLeagueByPlayerIdAndLeagueId(playerId, leagueId, goals) ? new ResponseEntity<>(HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
