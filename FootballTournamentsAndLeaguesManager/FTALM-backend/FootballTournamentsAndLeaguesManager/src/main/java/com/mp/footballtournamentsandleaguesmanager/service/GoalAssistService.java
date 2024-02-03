package com.mp.footballtournamentsandleaguesmanager.service;

import com.mp.footballtournamentsandleaguesmanager.DTO.GoalAssistDTO;
import com.mp.footballtournamentsandleaguesmanager.DTO.PlayerAssistsDTO;
import com.mp.footballtournamentsandleaguesmanager.DTO.PlayerGoalsDTO;
import com.mp.footballtournamentsandleaguesmanager.model.GoalAssist;
import com.mp.footballtournamentsandleaguesmanager.model.Player;
import com.mp.footballtournamentsandleaguesmanager.repository.GoalAssistRepository;
import com.mp.footballtournamentsandleaguesmanager.repository.PlayerRepository;
import com.mp.footballtournamentsandleaguesmanager.repository.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class GoalAssistService {
    private final GoalAssistRepository goalAssistRepository;
    private final PlayerRepository playerRepository;

    public GoalAssist addGoalAssist(GoalAssist goalAssist){
        return goalAssistRepository.save(goalAssist);
    }

    public List<GoalAssist> addGoalAssists(List<GoalAssist> goalAssistList) {
        return goalAssistRepository.saveAll(goalAssistList);
    }

    public Boolean deleteGoalAssistById(Long goalAssistId){
        if(goalAssistRepository.existsById(goalAssistId)){
            goalAssistRepository.deleteById(goalAssistId);
            return true;
        }else{
            return false;
        }
    }

    public GoalAssist updateGoalById(Long goalId, GoalAssistDTO goalAssistDTO){
        GoalAssist goalAssistToUpdate = this.goalAssistRepository.findById(goalId).orElseThrow();
        goalAssistToUpdate.setMinute(goalAssistDTO.getMinute());
        goalAssistToUpdate.setScorerPlayer(playerRepository.findById(goalAssistDTO.getScorerPlayerId()).orElseThrow());
//        goalAssistToUpdate.setAssistPlayer(playerRepository.findById(goalAssistDTO.getAssistPlayerId()).orElseThrow());

        return goalAssistRepository.save(goalAssistToUpdate);
    }
    public List<GoalAssistDTO> getAllGoalsByPlayerId(Long playerId){
        Optional<List<GoalAssist>> optionalGoalList = goalAssistRepository.getAllByScorerPlayerId(playerId);
        List<GoalAssist> goalList = optionalGoalList.orElse(Collections.emptyList());
        return goalList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public List<GoalAssistDTO> getAllAssistsByPlayerId(Long playerId){
        Optional<List<GoalAssist>> optionalGoalList = goalAssistRepository.getAllByAssistPlayerId(playerId);
        List<GoalAssist> goalList = optionalGoalList.orElse(Collections.emptyList());
        return goalList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public List<GoalAssistDTO> getAllByMatchId(Long matchId){
        Optional<List<GoalAssist>> optionalGoalList = goalAssistRepository.getAllByMatchId(matchId);
        List<GoalAssist> cardList = optionalGoalList.orElse(Collections.emptyList());
        return cardList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public int countGoalsByPlayerId(Long playerId){
        Optional<Integer> optionalPlayerAssists = goalAssistRepository.countGoalsByScorerPlayerId(playerId);
        return optionalPlayerAssists.orElse(0);
    }
    public int countAssistsByPlayerId(Long playerId){
        Optional<Integer> optionalPlayerAssists = goalAssistRepository.countAssistsByAssistPlayerId(playerId);
        return optionalPlayerAssists.orElse(0);
    }

    public List<PlayerGoalsDTO> getPlayersGoalsByLeagueId(Long leagueId) {
        Optional<List<PlayerGoalsDTO>> optionalGoalScorerDTOList = goalAssistRepository.getPlayersGoalsByLeagueId(leagueId);
        return optionalGoalScorerDTOList.orElse(Collections.emptyList());
    }

    public List<PlayerAssistsDTO> getPlayersAssistsByLeagueId(Long leagueId){
        Optional<List<PlayerAssistsDTO>> optionalPlayerAssistsDTOList = goalAssistRepository.getPlayersAssistsByLeagueId(leagueId);
        return optionalPlayerAssistsDTOList.orElse(Collections.emptyList());
    }

    public GoalAssistDTO convertToDTO(GoalAssist goalAssist){
        Optional<Player> assistPlayerOptional = Optional.empty();
        GoalAssistDTO dto = new GoalAssistDTO();
        Long assistPlayerId = goalAssist.getAssistPlayerId();
        if (assistPlayerId != null)
            assistPlayerOptional = playerRepository.findById(assistPlayerId);

        dto.setId(goalAssist.getId());
        dto.setMatchId(goalAssist.getMatch().getId());
        dto.setTeamId(goalAssist.getTeam().getId());
        dto.setTeamName(goalAssist.getTeam().getName());
        dto.setMinute(goalAssist.getMinute());
        dto.setScorerPlayerId(goalAssist.getScorerPlayer().getId());
        dto.setScorerPlayerFirstName(goalAssist.getScorerPlayer().getFirstName());
        dto.setScorerPlayerLastName(goalAssist.getScorerPlayer().getLastName());
        dto.setAssistPlayerId(goalAssist.getAssistPlayerId());

        if (assistPlayerOptional.isPresent()) {
            dto.setAssistPlayerFirstName(assistPlayerOptional.get().getFirstName());
            dto.setAssistPlayerLastName(assistPlayerOptional.get().getLastName());
        }

        return  dto;
    }
}
