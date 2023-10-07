package com.mp.footballtournamentsandleaguesmanager.controller;

import com.mp.footballtournamentsandleaguesmanager.DTO.GoalDTO;
import com.mp.footballtournamentsandleaguesmanager.model.Goal;
import com.mp.footballtournamentsandleaguesmanager.service.GoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goal")
public class GoalController {
    private final GoalService goalService;

    @Autowired
    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @PostMapping("/add")
    public ResponseEntity<Goal> addGoal(@RequestBody Goal goal){
        Goal newGoal = goalService.addGoal(goal);
        return new ResponseEntity<>(newGoal, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{goalId}")
    public ResponseEntity<Boolean> deleteGoalById(@PathVariable Long goalId){
        return goalService.deleteGoalById(goalId) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @PutMapping("update/{goalId}")
    public ResponseEntity<GoalDTO> updateGoalById(@PathVariable Long goalId, @RequestBody GoalDTO goalDTO){
        Goal updatedGoal = goalService.updateGoalById(goalId, goalDTO);
        GoalDTO updatedGoalDTO = goalService.convertToDTO(updatedGoal);
        return ResponseEntity.ok(updatedGoalDTO);
    }

    @GetMapping("/findAll/player/{playerId}")
    public ResponseEntity<List<GoalDTO>> getAllByPlayerId(@PathVariable Long playerId){
        return new ResponseEntity<>(goalService.getAllByPlayerId(playerId), HttpStatus.OK);
    }

    @GetMapping("/findAll/match/{matchId}")
    public ResponseEntity<List<GoalDTO>> getAllByMatchId(@PathVariable Long matchId){
        return new ResponseEntity<>(goalService.getAllByMatchId(matchId), HttpStatus.OK);
    }

    @GetMapping("/count/player/{playerId}")
    public ResponseEntity<Integer> countGoalsByPlayerId(@PathVariable Long playerId){
        return new ResponseEntity<>(goalService.countGoalsByPlayerId(playerId), HttpStatus.OK);
    }
}
