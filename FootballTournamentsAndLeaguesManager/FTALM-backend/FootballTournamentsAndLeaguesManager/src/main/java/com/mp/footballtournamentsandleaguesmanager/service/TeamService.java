package com.mp.footballtournamentsandleaguesmanager.service;

import com.mp.footballtournamentsandleaguesmanager.DTO.TeamDTO;
import com.mp.footballtournamentsandleaguesmanager.model.*;
import com.mp.footballtournamentsandleaguesmanager.repository.LeagueRepository;
import com.mp.footballtournamentsandleaguesmanager.repository.TeamRepository;
import com.mp.footballtournamentsandleaguesmanager.repository.TournamentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    private final LeagueRepository leagueRepository;
    private final TournamentRepository tournamentRepository;

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
    public List<TeamDTO> findAllTeamsNotInTournament(Long userId){
        Optional<List<Team>> teamsOptional = teamRepository.findAllTeamsNotInTournament(userId);
        List<Team> teams = teamsOptional.orElse(Collections.emptyList());
        return teams.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public List<TeamDTO> findAllTeamsNotInLeague(Long userId){
        Optional<List<Team>> teamsOptional = teamRepository.findAllTeamsNotInLeague(userId);
        List<Team> teams = teamsOptional.orElse(Collections.emptyList());
        return teams.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public Team updateIsInTournament(Long teamId, boolean isInTournament){
        Team updatedTeam = this.teamRepository.findById(teamId).orElseThrow();
        updatedTeam.setInTournament(isInTournament);

        return teamRepository.save(updatedTeam);
    }
    public Team updateIsInLeague(Long teamId, boolean isInLeague) {
        Team updatedTeam = this.teamRepository.findById(teamId).orElseThrow();
        updatedTeam.setInLeague(isInLeague);

        return teamRepository.save(updatedTeam);
    }
    public List<TeamDTO> findAllTeamsInTournamentByTournamentId(Long tournamentId) {
        Optional<List<Team>> teamsOptional = teamRepository.findAllTeamsInTournamentByTournamentId(tournamentId);
        List<Team> teams = teamsOptional.orElse(Collections.emptyList());
        return teams.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public List<TeamDTO> findAllTeamsInLeagueByLeagueId(Long leagueId) {
        Optional<List<Team>> teamsOptional = teamRepository.findAllTeamsInLeagueByLeagueId(leagueId);
        List<Team> teams = teamsOptional.orElse(Collections.emptyList());
        return teams.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public Integer countTeamsByLeaguesId(Long leagueId){
        return teamRepository.countTeamsByLeaguesId(leagueId).orElse(0);
    }
    public Integer countTeamsByTournamentsId(Long tournamentId){
        return teamRepository.countTeamsByTournamentsId(tournamentId).orElse(0);
    }
    public boolean removeTeamFromLeague(Long teamId, Long leagueId) {
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        Optional<League> optionalLeague = leagueRepository.findById(leagueId);

        if (optionalTeam.isPresent() && optionalLeague.isPresent()) {
            Team teamToRemove = optionalTeam.get();
            League league = optionalLeague.get();
            if (league.getStatus() == TournamentLeagueBase.Status.NOT_STARTED) {
                league.removeTeamFromLeague(teamToRemove);
                teamToRemove.setInLeague(false);
                teamRepository.save(teamToRemove);
                leagueRepository.save(league);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean removeTeamFromTournament(Long teamId, Long tournamentId) {
        Optional<Team> optionalTeam = teamRepository.findById(teamId);
        Optional<Tournament> optionalTournament = tournamentRepository.findById(tournamentId);

        if (optionalTeam.isPresent() && optionalTournament.isPresent()) {
            Team teamToRemove = optionalTeam.get();
            Tournament tournament = optionalTournament.get();
            if (tournament.getStatus() == TournamentLeagueBase.Status.NOT_STARTED) {
                tournament.removeTeamFromTournament(teamToRemove);
                teamToRemove.setInTournament(false);
                teamRepository.save(teamToRemove);
                tournamentRepository.save(tournament);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public TeamDTO convertToDTO(Team team){
        TeamDTO dto = new TeamDTO();
        dto.setId(team.getId());
        dto.setName(team.getName());
        dto.setCaptainId(team.getCaptainId());
        dto.setEstablished(team.getEstablished());
        dto.setInLeague(team.isInLeague());
        dto.setInTournament(team.isInTournament());
        return dto;
    }
}
