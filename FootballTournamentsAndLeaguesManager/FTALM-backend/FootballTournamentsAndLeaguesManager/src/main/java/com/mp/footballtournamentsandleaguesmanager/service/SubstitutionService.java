package com.mp.footballtournamentsandleaguesmanager.service;

import com.mp.footballtournamentsandleaguesmanager.DTO.SubstitutionDTO;
import com.mp.footballtournamentsandleaguesmanager.model.Substitution;
import com.mp.footballtournamentsandleaguesmanager.repository.PlayerRepository;
import com.mp.footballtournamentsandleaguesmanager.repository.SubstitutionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubstitutionService {
    private final SubstitutionRepository substitutionRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public SubstitutionService(SubstitutionRepository substitutionRepository, PlayerRepository playerRepository) {
        this.substitutionRepository = substitutionRepository;
        this.playerRepository = playerRepository;
    }

    public Substitution addSubstitution(Substitution substitution){
        return substitutionRepository.save(substitution);
    }
    public Boolean deleteSubstitutionById(Long substitutionId){
        if(substitutionRepository.existsById(substitutionId)){
            substitutionRepository.deleteById(substitutionId);
            return true;
        }else{
            return false;
        }
    }

    public Substitution updateSubstitutionById(Long substitutionId, SubstitutionDTO substitutionDTO){
        Substitution substitutionToUpdate = this.substitutionRepository.findById(substitutionId).orElseThrow();
        substitutionToUpdate.setMinute(substitutionDTO.getMinute());
        substitutionToUpdate.setEnteringPlayer(playerRepository.findById(substitutionDTO.getEnteringPlayerId()).orElseThrow());
        substitutionToUpdate.setExitingPlayer(playerRepository.findById(substitutionDTO.getExitingPlayerId()).orElseThrow());
        return substitutionRepository.save(substitutionToUpdate);
    }

    public List<SubstitutionDTO> getAllByMatchId(Long matchId){
        Optional<List<Substitution>> optionalSubstitutionList = substitutionRepository.getAllByMatchId(matchId);
        List<Substitution> substitutionList = optionalSubstitutionList.orElse(Collections.emptyList());
        return substitutionList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<SubstitutionDTO> getAllByMatchIdAndTeamId(Long matchId, Long teamId){
        Optional<List<Substitution>> optionalSubstitutionList = substitutionRepository.getAllByMatchIdAndTeamId(matchId, teamId);
        List<Substitution> substitutionList = optionalSubstitutionList.orElse(Collections.emptyList());
        return substitutionList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public int countSubstitutionsByMatchIdAndTeamId(Long matchId, Long teamId){
        return substitutionRepository.countSubstitutionsByMatchIdAndTeamId(matchId, teamId).orElse(0);
    }

    public SubstitutionDTO convertToDTO(Substitution substitution){
        SubstitutionDTO dto = new SubstitutionDTO();
        dto.setId(substitution.getId());
        dto.setEnteringPlayerId(substitution.getEnteringPlayer().getId());
        dto.setEnteringPlayerFirstName(substitution.getEnteringPlayer().getFirstName());
        dto.setEnteringPlayerLastName(substitution.getEnteringPlayer().getLastName());
        dto.setExitingPlayerId(substitution.getExitingPlayer().getId());
        dto.setExitingPlayerFirstName(substitution.getExitingPlayer().getFirstName());
        dto.setExitingPlayerLastName(substitution.getExitingPlayer().getLastName());
        dto.setMatchId(substitution.getMatch().getId());
        dto.setTeamId(substitution.getTeam().getId());
        dto.setMinute(substitution.getMinute());

        return dto;
    }
}
