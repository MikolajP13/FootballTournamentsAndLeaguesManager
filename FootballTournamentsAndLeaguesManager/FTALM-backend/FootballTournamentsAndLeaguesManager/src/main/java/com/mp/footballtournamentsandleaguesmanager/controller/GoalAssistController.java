package com.mp.footballtournamentsandleaguesmanager.controller;

import com.mp.footballtournamentsandleaguesmanager.DTO.GoalAssistDTO;
import com.mp.footballtournamentsandleaguesmanager.DTO.PlayerAssistsDTO;
import com.mp.footballtournamentsandleaguesmanager.DTO.PlayerGoalsDTO;
import com.mp.footballtournamentsandleaguesmanager.model.GoalAssist;
import com.mp.footballtournamentsandleaguesmanager.service.GoalAssistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goalAssist")
public class GoalAssistController {
    private final GoalAssistService goalAssistService;

    @Autowired
    public GoalAssistController(GoalAssistService goalAssistService) {
        this.goalAssistService = goalAssistService;
    }

    @PostMapping("/add")
    public ResponseEntity<GoalAssist> addGoalAssist(@RequestBody GoalAssist goalAssist){
        GoalAssist newGoalAssist = goalAssistService.addGoalAssist(goalAssist);
        return new ResponseEntity<>(newGoalAssist, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{goalAssistId}")
    public ResponseEntity<Boolean> deleteGoalById(@PathVariable Long goalAssistId){
        return goalAssistService.deleteGoalAssistById(goalAssistId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("update/{goalId}")
    public ResponseEntity<GoalAssistDTO> updateGoalById(@PathVariable Long goalId, @RequestBody GoalAssistDTO goalAssistDTO){
        GoalAssist updatedGoalAssist = goalAssistService.updateGoalById(goalId, goalAssistDTO);
        GoalAssistDTO updatedGoalAssistDTO = goalAssistService.convertToDTO(updatedGoalAssist);
        return ResponseEntity.ok(updatedGoalAssistDTO);
    }

    @GetMapping("/findAll/goals/player/{playerId}")
    public ResponseEntity<List<GoalAssistDTO>> getAllGoalsByPlayerId(@PathVariable Long playerId){
        return new ResponseEntity<>(goalAssistService.getAllGoalsByPlayerId(playerId), HttpStatus.OK);
    }

    @GetMapping("/findAll/assists/player/{playerId}")
    public ResponseEntity<List<GoalAssistDTO>> getAllAssistsByPlayerId(@PathVariable Long playerId){
        return new ResponseEntity<>(goalAssistService.getAllAssistsByPlayerId(playerId), HttpStatus.OK);
    }

    @GetMapping("/findAll/match/{matchId}")
    public ResponseEntity<List<GoalAssistDTO>> getAllByMatchId(@PathVariable Long matchId){
        return new ResponseEntity<>(goalAssistService.getAllByMatchId(matchId), HttpStatus.OK);
    }

    @GetMapping("/count/player/goals/{playerId}")
    public ResponseEntity<Integer> countGoalsByPlayerId(@PathVariable Long playerId){
        return new ResponseEntity<>(goalAssistService.countGoalsByPlayerId(playerId), HttpStatus.OK);
    }

    @GetMapping("/count/player/assists/{playerId}")
    public ResponseEntity<Integer> countAssistsByPlayerId(@PathVariable Long playerId){
        return new ResponseEntity<>(goalAssistService.countAssistsByPlayerId(playerId), HttpStatus.OK);
    }

    @GetMapping("/getPlayersGoals/league/{leagueId}")
    public ResponseEntity<List<PlayerGoalsDTO>> getPlayersGoalsByLeagueId(@PathVariable Long leagueId){
        return new ResponseEntity<>(goalAssistService.getPlayersGoalsByLeagueId(leagueId), HttpStatus.OK);
    }

    @GetMapping("/getPlayersAssists/league/{leagueId}")
    public ResponseEntity<List<PlayerAssistsDTO>> getPlayersAssistsByLeagueId(@PathVariable Long leagueId){
        return new ResponseEntity<>(goalAssistService.getPlayersAssistsByLeagueId(leagueId), HttpStatus.OK);
    }
}
