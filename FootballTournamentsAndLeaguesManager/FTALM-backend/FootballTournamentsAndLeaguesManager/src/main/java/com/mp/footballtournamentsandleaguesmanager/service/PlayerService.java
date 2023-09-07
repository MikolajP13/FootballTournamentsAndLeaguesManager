package com.mp.footballtournamentsandleaguesmanager.service;

import com.mp.footballtournamentsandleaguesmanager.DTO.LeagueDTO;
import com.mp.footballtournamentsandleaguesmanager.DTO.PlayerDTO;
import com.mp.footballtournamentsandleaguesmanager.model.League;
import com.mp.footballtournamentsandleaguesmanager.model.Player;
import com.mp.footballtournamentsandleaguesmanager.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerService {
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public PlayerDTO getPlayerById(Long playerId){
        return convertToDTO(playerRepository.findById(playerId).orElseThrow());
    }
    public List<PlayerDTO> getAllPlayersByTeamId(Long teamId){
        Optional<List<Player>> playersOptional = playerRepository.findAllByTeamId(teamId);
        List<Player> players = playersOptional.orElse(Collections.emptyList());
        return players.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public Player addPlayer(Player player){
        return playerRepository.save(player);
    }
    public Boolean deletePlayerById(Long playerId){
        if (playerRepository.existsById(playerId)){
            playerRepository.deleteById(playerId);
            return true;
        }else{
            return false;
        }
    }
    private PlayerDTO convertToDTO(Player player) {
        PlayerDTO dto = new PlayerDTO();
        dto.setId(player.getId());
        dto.setFirstName(player.getFirstName());
        dto.setLastName(player.getLastName());
        dto.setDateOfBirth(player.getDateOfBirth());
        dto.setHeightInCm(player.getHeightInCm());
        dto.setFoot(player.getFoot());
        dto.setJoinedDate(player.getJoinedDate());
        dto.setPosition(player.getPosition());
        dto.setPositionDetail(player.getPositionDetail());
        dto.setAppearances(player.getAppearances());
        dto.setMinutes(player.getMinutes());
        dto.setYellowCards(player.getYellowCards());
        dto.setSecondYellowCards(player.getSecondYellowCards());
        dto.setRedCards(player.getRedCards());
        dto.setGoalsConceded(player.getGoalsConceded());
        dto.setCleanSheets(player.getCleanSheets());
        dto.setGoals(player.getGoals());
        dto.setAssists(player.getAssists());
        dto.setInjured(player.isInjured());
        dto.setTeamCaptain(player.isTeamCaptain());
        dto.setSuspended(player.isSuspended());
        return dto;
    }
}
