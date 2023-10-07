package com.mp.footballtournamentsandleaguesmanager.service;

import com.mp.footballtournamentsandleaguesmanager.DTO.AssistDTO;
import com.mp.footballtournamentsandleaguesmanager.model.Assist;
import com.mp.footballtournamentsandleaguesmanager.repository.AssistRepository;
import com.mp.footballtournamentsandleaguesmanager.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AssistService {
    private final AssistRepository assistRepository;
    private final PlayerRepository playerRepository;

    @Autowired
    public AssistService(AssistRepository assistRepository,
                         PlayerRepository playerRepository) {
        this.assistRepository = assistRepository;
        this.playerRepository = playerRepository;
    }

    public Assist addAssists(Assist assist){
        return assistRepository.save(assist);
    }
    public Boolean deleteAssistById(Long assistId){
        if(assistRepository.existsById(assistId)){
            assistRepository.deleteById(assistId);
            return true;
        }else{
            return false;
        }
    }
    //TODO: edit and update Assist
    public Assist updateAssistById(Long assistId, AssistDTO assistDTO){
        Assist assistToUpdate = this.assistRepository.findById(assistId).orElseThrow();
        assistToUpdate.setMinute(assistDTO.getMinute());
        assistToUpdate.setPlayer(playerRepository.findById(assistDTO.getPlayerId()).orElseThrow());
        return assistRepository.save(assistToUpdate);
    }
    public List<AssistDTO> getAllByPlayerId(Long playerId){
        Optional<List<Assist>> optionalAssistList = assistRepository.getAllByPlayerId(playerId);
        List<Assist> assistList = optionalAssistList.orElse(Collections.emptyList());
        return assistList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public List<AssistDTO> getAllByMatchId(Long matchId){
        Optional<List<Assist>> optionalAssistDTOList = assistRepository.getAllByMatchId(matchId);
        List<Assist> assistList = optionalAssistDTOList.orElse(Collections.emptyList());
        return assistList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public int countAssistsByPlayerId(Long playerId){
        Optional<Integer> optionalPlayerAssists = assistRepository.countAssistsByPlayerId(playerId);
        return optionalPlayerAssists.orElse(0);
    }
    public AssistDTO convertToDTO(Assist assist){
        AssistDTO dto = new AssistDTO();
        dto.setId(assist.getId());
        dto.setMatchId(assist.getMatch().getId());
        dto.setPlayerId(assist.getPlayer().getId());
        dto.setPlayerFirstName(assist.getPlayer().getFirstName());
        dto.setPlayerLastName(assist.getPlayer().getLastName());
        dto.setMinute(assist.getMinute());
        return dto;
    }
}
