package com.mp.footballtournamentsandleaguesmanager.service;

import com.mp.footballtournamentsandleaguesmanager.DTO.LeagueDTO;
import com.mp.footballtournamentsandleaguesmanager.model.League;
import com.mp.footballtournamentsandleaguesmanager.model.Team;
import com.mp.footballtournamentsandleaguesmanager.repository.LeagueRepository;
import com.mp.footballtournamentsandleaguesmanager.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LeagueService {
    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public LeagueService(LeagueRepository leagueRepository,
                         TeamRepository teamRepository) {
        this.leagueRepository = leagueRepository;
        this.teamRepository = teamRepository;
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
            return false;
        }
    }
    public Boolean addTeamToLeagueByIds(Long teamId, Long leagueId){
        if(leagueRepository.existsById(leagueId) && teamRepository.existsById(teamId)){
            League league = leagueRepository.findById(leagueId).orElseThrow();
            Team team = teamRepository.findById(teamId).orElseThrow();
            league.addTeamToLeague(team);
            leagueRepository.save(league);
            return true;
        }else{
            return false;
        }
    }
    public LeagueDTO findActiveLeagueForTeam(Long teamId){
        return convertToDTO(leagueRepository.findActiveLeagueForTeam(teamId));
    }

    private LeagueDTO convertToDTO(League league) {
        LeagueDTO dto = new LeagueDTO();
        dto.setId(league.getId());
        dto.setName(league.getName());
        dto.setStartDate(league.getStartDate());
        dto.setEndDate(league.getEndDate());
        dto.setNumberOfTeams(league.getNumberOfTeams());
        dto.setStatus(league.getStatus());
        dto.setType(league.getType());
        return dto;
    }
}
