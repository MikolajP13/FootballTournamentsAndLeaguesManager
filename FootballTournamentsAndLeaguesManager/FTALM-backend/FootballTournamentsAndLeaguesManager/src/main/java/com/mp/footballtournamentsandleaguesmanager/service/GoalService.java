package com.mp.footballtournamentsandleaguesmanager.service;

import com.mp.footballtournamentsandleaguesmanager.DTO.GoalDTO;
import com.mp.footballtournamentsandleaguesmanager.model.Goal;
import com.mp.footballtournamentsandleaguesmanager.repository.GoalRepository;
import com.mp.footballtournamentsandleaguesmanager.repository.PlayerRepository;
import com.mp.footballtournamentsandleaguesmanager.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GoalService {
    private final GoalRepository goalRepository;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public GoalService(GoalRepository goalRepository, PlayerRepository playerRepository,
                       TeamRepository teamRepository) {
        this.goalRepository = goalRepository;
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    public Goal addGoal(Goal goal){
        return goalRepository.save(goal);
    }
    public Boolean deleteGoalById(Long assistId){
        if(goalRepository.existsById(assistId)){
            goalRepository.deleteById(assistId);
            return true;
        }else{
            return false;
        }
    }
    //TODO: edit and update Goal
    public Goal updateGoalById(Long goalId, GoalDTO goalDTO){
        Goal goalToUpdate = this.goalRepository.findById(goalId).orElseThrow();
        goalToUpdate.setMinute(goalDTO.getMinute());
        goalToUpdate.setTeam(teamRepository.findById(goalDTO.getTeamId()).orElseThrow());
        goalToUpdate.setPlayer(playerRepository.findById(goalDTO.getPlayerId()).orElseThrow());
        return goalRepository.save(goalToUpdate);
    }
    public List<GoalDTO> getAllByPlayerId(Long playerId){
        Optional<List<Goal>> optionalGoalList = goalRepository.getAllByPlayerId(playerId);
        List<Goal> goalList = optionalGoalList.orElse(Collections.emptyList());
        return goalList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public List<GoalDTO> getAllByMatchId(Long matchId){
        Optional<List<Goal>> optionalGoalList = goalRepository.getAllByMatchId(matchId);
        List<Goal> cardList = optionalGoalList.orElse(Collections.emptyList());
        return cardList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public int countGoalsByPlayerId(Long playerId){
        Optional<Integer> optionalPlayerAssists = goalRepository.countGoalsByPlayerId(playerId);
        return optionalPlayerAssists.orElse(0);
    }

    public GoalDTO convertToDTO(Goal goal){
        GoalDTO goalDTO = new GoalDTO();
        goalDTO.setId(goal.getId());
        goalDTO.setMatchId(goal.getMatch().getId());
        goalDTO.setMinute(goal.getMinute());
        goalDTO.setPlayerId(goal.getPlayer().getId());
        goalDTO.setPlayerFirstName(goal.getPlayer().getFirstName());
        goalDTO.setPlayerLastName(goal.getPlayer().getLastName());
        goalDTO.setTeamId(goal.getTeam().getId());

        return  goalDTO;
    }
}
