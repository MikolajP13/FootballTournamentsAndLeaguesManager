package com.mp.footballtournamentsandleaguesmanager.service;

import com.mp.footballtournamentsandleaguesmanager.DTO.IncompletePlayerDataDTO;
import com.mp.footballtournamentsandleaguesmanager.DTO.PlayerDTO;
import com.mp.footballtournamentsandleaguesmanager.model.League;
import com.mp.footballtournamentsandleaguesmanager.model.Player;
import com.mp.footballtournamentsandleaguesmanager.model.Tournament;
import com.mp.footballtournamentsandleaguesmanager.repository.LeagueRepository;
import com.mp.footballtournamentsandleaguesmanager.repository.PlayerRepository;
import com.mp.footballtournamentsandleaguesmanager.repository.TournamentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PlayerService {
    private final PlayerRepository playerRepository;
    private final TournamentRepository tournamentRepository;
    private final LeagueRepository leagueRepository;

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

    public PlayerDTO updatePlayer(Long playerId, IncompletePlayerDataDTO incompletePlayerDataDTO) {
        Optional<Player> optionalPlayer = playerRepository.findById(playerId);

        if (optionalPlayer.isEmpty()) {
            throw new RuntimeException("Player with id=" + playerId + " does not exist!"); //TODO: create exception classes!
        }
        System.out.println("PLAYER UPDATE");
        Player playerToUpdate = optionalPlayer.get();

        if (incompletePlayerDataDTO.getFirstName() != null && !playerToUpdate.getFirstName().equals(incompletePlayerDataDTO.getFirstName())){
            playerToUpdate.setFirstName(incompletePlayerDataDTO.getFirstName());
        }

        if (incompletePlayerDataDTO.getLastName() != null && !playerToUpdate.getLastName().equals(incompletePlayerDataDTO.getLastName())){
            playerToUpdate.setLastName(incompletePlayerDataDTO.getLastName());
        }

        if (incompletePlayerDataDTO.getDateOfBirth() != null && playerToUpdate.getDateOfBirth().compareTo(incompletePlayerDataDTO.getDateOfBirth()) != 0){
            playerToUpdate.setDateOfBirth(incompletePlayerDataDTO.getDateOfBirth());
        }

        if (incompletePlayerDataDTO.getHeightInCm() != 0 && playerToUpdate.getHeightInCm() != incompletePlayerDataDTO.getHeightInCm()){
            playerToUpdate.setHeightInCm(incompletePlayerDataDTO.getHeightInCm());
        }

        //TODO: Foot, Position and PositionDetail only if exists! create method checkIf_X_Exists(X x);
        if (incompletePlayerDataDTO.getFoot() != null && !playerToUpdate.getFoot().equals(incompletePlayerDataDTO.getFoot())){
            playerToUpdate.setFoot(incompletePlayerDataDTO.getFoot());
        }

        if (incompletePlayerDataDTO.getPosition() != null && !playerToUpdate.getPosition().equals(incompletePlayerDataDTO.getPosition())){
            playerToUpdate.setPosition(incompletePlayerDataDTO.getPosition());
        }

        if (incompletePlayerDataDTO.getPositionDetail() != null && !playerToUpdate.getPositionDetail().equals(incompletePlayerDataDTO.getPositionDetail())){
            playerToUpdate.setPositionDetail(incompletePlayerDataDTO.getPositionDetail());
        }

        playerRepository.save(playerToUpdate);

        return convertToDTO(playerToUpdate);
    }

    public Boolean deletePlayerById(Long playerId){
        Optional<Player> optionalPlayer = playerRepository.findById(playerId);

        if (optionalPlayer.isPresent()){
            Player playerToDelete = optionalPlayer.get();
            Optional<Tournament> optionalTournament = tournamentRepository.findActiveTournamentForTeam(playerToDelete.getTeam().getId());
            Optional<League> optionalLeague = leagueRepository.findActiveLeagueForTeam(playerToDelete.getTeam().getId());
            if (optionalTournament.isPresent() || optionalLeague.isPresent()) {
                return false;
            } else {
                playerRepository.deleteById(playerId);
                return true;
            }
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
