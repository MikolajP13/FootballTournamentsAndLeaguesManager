package com.mp.footballtournamentsandleaguesmanager.service;

import com.mp.footballtournamentsandleaguesmanager.DTO.LeagueStandingDTO;
import com.mp.footballtournamentsandleaguesmanager.model.LeagueStanding;
import com.mp.footballtournamentsandleaguesmanager.repository.LeagueStandingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeagueStandingService {
    private final LeagueStandingRepository leagueStandingRepository;

    @Autowired
    public LeagueStandingService(LeagueStandingRepository leagueStandingRepository) {
        this.leagueStandingRepository = leagueStandingRepository;
    }

    public LeagueStanding addLeagueStanding(LeagueStanding leagueStanding) {
        return leagueStandingRepository.save(leagueStanding);
    }

    public List<LeagueStandingDTO> getLeagueStandingByLeagueId(Long leagueId){
        Optional<List<LeagueStanding>> optionalLeagueStandingList = leagueStandingRepository.getLeagueStandingByLeagueId(leagueId);
        List<LeagueStanding> leagueStandingList = optionalLeagueStandingList.orElse(Collections.emptyList());
        return leagueStandingList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<LeagueStandingDTO> getLeagueStandingsByLeagueIdOrderByGoalsFor(Long leagueId){
        Optional<List<LeagueStanding>> optionalLeagueStandingList = leagueStandingRepository.getLeagueStandingsByLeagueIdOrderByGoalsFor(leagueId);
        List<LeagueStanding> leagueStandingDTOList = optionalLeagueStandingList.orElse(Collections.emptyList());
        return leagueStandingDTOList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<LeagueStandingDTO> getLeagueStandingsByLeagueIdOrderByWins(Long leagueId){
        Optional<List<LeagueStanding>> optionalLeagueStandingList = leagueStandingRepository.getLeagueStandingsByLeagueIdOrderByWins(leagueId);
        List<LeagueStanding> leagueStandingList = optionalLeagueStandingList.orElse(Collections.emptyList());
        return leagueStandingList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<LeagueStandingDTO> getLeagueStandingsByLeagueIdOrderByLosses(Long leagueId){
        Optional<List<LeagueStanding>> optionalLeagueStandingList = leagueStandingRepository.getLeagueStandingsByLeagueIdOrderByLosses(leagueId);
        List<LeagueStanding> leagueStandingList = optionalLeagueStandingList.orElse(Collections.emptyList());
        return leagueStandingList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public LeagueStandingDTO convertToDTO(LeagueStanding leagueStanding){
        LeagueStandingDTO dto = new LeagueStandingDTO();
        dto.setId(leagueStanding.getId());
        dto.setLeagueId(leagueStanding.getLeague().getId());
        dto.setLeagueName(leagueStanding.getLeague().getName());
        dto.setTeamId(leagueStanding.getTeam().getId());
        dto.setTeamName(leagueStanding.getTeam().getName());
        dto.setMatches(leagueStanding.getMatches());
        dto.setPoints(leagueStanding.getPoints());
        dto.setGoalsFor(leagueStanding.getGoalsFor());
        dto.setGoalsAgainst(leagueStanding.getGoalsAgainst());
        dto.setWins(leagueStanding.getWins());
        dto.setDraws(leagueStanding.getDraws());
        dto.setLosses(leagueStanding.getLosses());

        return dto;
    }
}
