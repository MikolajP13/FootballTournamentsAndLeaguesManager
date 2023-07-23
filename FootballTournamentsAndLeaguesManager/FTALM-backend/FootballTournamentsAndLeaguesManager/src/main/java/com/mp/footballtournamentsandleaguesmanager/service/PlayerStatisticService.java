package com.mp.footballtournamentsandleaguesmanager.service;

import com.mp.footballtournamentsandleaguesmanager.DTO.PlayerStatisticDTO;
import com.mp.footballtournamentsandleaguesmanager.model.League;
import com.mp.footballtournamentsandleaguesmanager.model.PlayerStatistic;
import com.mp.footballtournamentsandleaguesmanager.repository.LeagueRepository;
import com.mp.footballtournamentsandleaguesmanager.repository.PlayerStatisticRepository;
import com.mp.footballtournamentsandleaguesmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerStatisticService {
    private final PlayerStatisticRepository playerStatisticRepository;
    private final LeagueRepository leagueRepository;
    private final UserRepository userRepository;

    @Autowired
    public PlayerStatisticService(PlayerStatisticRepository playerStatisticRepository,
                                  LeagueRepository leagueRepository,
                                  UserRepository userRepository) {
        this.playerStatisticRepository = playerStatisticRepository;
        this.leagueRepository = leagueRepository;
        this.userRepository = userRepository;
    }
    public List<PlayerStatisticDTO> getAllPlayerStatisticByPlayerId(Long playerId){
        Optional<List<PlayerStatistic>> playerStatisticsOptional = playerStatisticRepository.findAllByPlayerId(playerId);
        List<PlayerStatistic> playerStatistic = playerStatisticsOptional.orElse(Collections.emptyList());
        return playerStatistic.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public List<PlayerStatisticDTO> getAllPlayerStatisticByPlayerIdAndLeagueId(Long playerId, Long LeagueId){
        Optional<List<PlayerStatistic>> playerStatisticsOptional = playerStatisticRepository.findAllByPlayerIdAndLeagueId(playerId, LeagueId);
        List<PlayerStatistic> playerStatistics = playerStatisticsOptional.orElse(Collections.emptyList());
        return playerStatistics.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public List<PlayerStatisticDTO> getAllPlayerStatisticByPlayerIdAndTournamentId(Long playerId, Long tournamentId){
        Optional<List<PlayerStatistic>> playerStatisticsOptional = playerStatisticRepository.findAllByPlayerIdAndTournamentId(playerId, tournamentId);
        List<PlayerStatistic> playerStatistics = playerStatisticsOptional.orElse(Collections.emptyList());
        return playerStatistics.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public PlayerStatistic addPlayerStatistic(PlayerStatistic playerStatistic){
        return playerStatisticRepository.save(playerStatistic);
    }
    public Boolean updatePlayerGoalsInLeagueByPlayerIdAndLeagueId(Long playerId, Long leagueId, int goals){ //player scored x goal/s
        Optional<PlayerStatistic> playerStatisticOptional = playerStatisticRepository.findByPlayerId(playerId);
        Optional<League> leagueOptional = leagueRepository.findById(leagueId);
        if(playerStatisticOptional.isPresent() && leagueOptional.isPresent()){
            PlayerStatistic playerStatisticToUpdate = playerStatisticOptional.get();
            if(playerStatisticToUpdate.getGoals() < (playerStatisticToUpdate.getGoals() + goals) && goals > 0){
                playerStatisticToUpdate.setGoals(playerStatisticToUpdate.getGoals() + goals); //add number of goals to previous value
                playerStatisticRepository.save(playerStatisticToUpdate);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    private PlayerStatisticDTO convertToDTO(PlayerStatistic playerStatistic) {
        PlayerStatisticDTO dto = new PlayerStatisticDTO();
        dto.setId(playerStatistic.getId());
        dto.setLeagueId(playerStatistic.getLeagueId());
        dto.setTournamentId(playerStatistic.getTournamentId());
        dto.setAppearances(playerStatistic.getAppearances());
        dto.setMinutesPlayed(playerStatistic.getMinutesPlayed());
        dto.setGoals(playerStatistic.getGoals());
        dto.setAssists(playerStatistic.getAssists());
        dto.setYellowCards(playerStatistic.getYellowCards());
        dto.setRedCards(playerStatistic.getRedCards());
        return dto;
    }
}
