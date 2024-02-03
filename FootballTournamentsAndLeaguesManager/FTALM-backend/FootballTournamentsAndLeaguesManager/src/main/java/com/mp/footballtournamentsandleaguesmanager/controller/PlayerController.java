package com.mp.footballtournamentsandleaguesmanager.controller;

import com.mp.footballtournamentsandleaguesmanager.DTO.IncompletePlayerDataDTO;
import com.mp.footballtournamentsandleaguesmanager.DTO.PlayerDTO;
import com.mp.footballtournamentsandleaguesmanager.model.Player;
import com.mp.footballtournamentsandleaguesmanager.service.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/player")
public class PlayerController {
    private final PlayerService playerService;

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

    @PatchMapping("/{playerId}/update")
    public ResponseEntity<PlayerDTO> updatePlayer(@PathVariable Long playerId, @RequestBody IncompletePlayerDataDTO incompletePlayerDataDTO) {
        return new ResponseEntity<>(playerService.updatePlayer(playerId, incompletePlayerDataDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{playerId}")
    public ResponseEntity<Boolean> deletePlayerById(@PathVariable Long playerId){
        return playerService.deletePlayerById(playerId) ? ResponseEntity.ok(true) : ResponseEntity.ok(false);
    }
}
