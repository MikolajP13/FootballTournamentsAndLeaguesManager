package com.mp.footballtournamentsandleaguesmanager.controller;

import com.mp.footballtournamentsandleaguesmanager.DTO.PlayerDTO;
import com.mp.footballtournamentsandleaguesmanager.model.Player;
import com.mp.footballtournamentsandleaguesmanager.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/player")
public class PlayerController {
    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/find/{playerId}")
    public ResponseEntity<PlayerDTO> getPlayerById(@PathVariable Long playerId){
        return new ResponseEntity<>(playerService.getPlayerById(playerId), HttpStatus.OK);
    }

    @GetMapping("/all/{teamId}")
    public ResponseEntity<List<PlayerDTO>> getAllPlayersByTeamId(@PathVariable Long teamId){
        List<PlayerDTO> teamPlayers = playerService.getAllPlayersByTeamId(teamId);
        return ResponseEntity.ok(teamPlayers);
    }

    @PostMapping("/add")
    public ResponseEntity<Player> addPlayer(@RequestBody Player player){
        Player newPlayer = playerService.addPlayer(player);
        return new ResponseEntity<>(newPlayer, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{playerId}")
    public ResponseEntity<String> deletePlayerById(@PathVariable Long playerId){
        return playerService.deletePlayerById(playerId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
