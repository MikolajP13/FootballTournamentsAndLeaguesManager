package com.mp.footballtournamentsandleaguesmanager.service;

import com.mp.footballtournamentsandleaguesmanager.DTO.LeagueDTO;
import com.mp.footballtournamentsandleaguesmanager.model.League;
import com.mp.footballtournamentsandleaguesmanager.repository.LeagueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeagueService {
    private final LeagueRepository leagueRepository;

    @Autowired
    public LeagueService(LeagueRepository leagueRepository) {
        this.leagueRepository = leagueRepository;
    }

    public LeagueDTO getLeagueById(Long leagueId){
        return convertToDTO(leagueRepository.findById(leagueId).orElseThrow()); //TODO
    }
    public List<LeagueDTO> getAllByUserId(Long userId) {
        Optional<List<League>> leaguesOptional = leagueRepository.findAllByUserId(userId);
        List<League> leagues = leaguesOptional.orElse(Collections.emptyList());
        return leagues.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public League addLeague(League league){
        return leagueRepository.save(league);
    }
    public Boolean deleteLeagueById(Long leagueId){
        if(leagueRepository.existsById(leagueId)) {
            leagueRepository.deleteById(leagueId);
            return true;
        }else {
            System.out.println("Nie istnieje");
            return false;
        }
    }
    private LeagueDTO convertToDTO(League league) {
        LeagueDTO dto = new LeagueDTO();
        dto.setId(league.getId());
        dto.setLeagueName(league.getName());
        return dto;
    }
}
