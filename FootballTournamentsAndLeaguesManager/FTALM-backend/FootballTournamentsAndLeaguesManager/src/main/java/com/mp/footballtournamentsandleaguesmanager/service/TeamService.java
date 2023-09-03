package com.mp.footballtournamentsandleaguesmanager.service;

import com.mp.footballtournamentsandleaguesmanager.DTO.TeamDTO;
import com.mp.footballtournamentsandleaguesmanager.model.Team;
import com.mp.footballtournamentsandleaguesmanager.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TeamService {
    private final TeamRepository teamRepository;

    @Autowired
    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }
    public TeamDTO getTeamById(Long teamId){
        return convertToDTO(teamRepository.findById(teamId).orElseThrow());
    }
    public List<TeamDTO> getAllByUserId(Long userId){
        Optional<List<Team>> teamsOptional = teamRepository.findAllByUserId(userId);
        List<Team> teams = teamsOptional.orElse(Collections.emptyList());
        return teams.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public Team addTeam(Team team){
        return teamRepository.save(team);
    }
    public Boolean deleteTeamById(Long teamId){
        if(teamRepository.existsById(teamId)){
            teamRepository.deleteById(teamId);
            return true;
        }else{
            return false;
        }
    }
    private TeamDTO convertToDTO(Team team){
        TeamDTO dto = new TeamDTO();
        dto.setId(team.getId());
        dto.setName(team.getName());
        dto.setCaptainId(team.getCaptainId());
        dto.setEstablished(team.getEstablished());
        dto.setIsInLeague(team.isInLeague());
        dto.setIsInTournament(team.isInTournament());
        return dto;
    }
}
