package com.mp.footballtournamentsandleaguesmanager.service;

import com.mp.footballtournamentsandleaguesmanager.DTO.TournamentDTO;
import com.mp.footballtournamentsandleaguesmanager.model.Team;
import com.mp.footballtournamentsandleaguesmanager.model.Tournament;
import com.mp.footballtournamentsandleaguesmanager.model.TournamentLeagueBase;
import com.mp.footballtournamentsandleaguesmanager.repository.TeamRepository;
import com.mp.footballtournamentsandleaguesmanager.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TournamentService {
    private final TournamentRepository tournamentRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public TournamentService(TournamentRepository tournamentRepository,
                             TeamRepository teamRepository) {
        this.tournamentRepository = tournamentRepository;
        this.teamRepository = teamRepository;
    }
    public TournamentDTO getTournamentById(Long tournamentId){
        return convertToDTO(tournamentRepository.findById(tournamentId).orElseThrow()); //TODO
    }
    public TournamentDTO findActiveTournamentForTeam(Long teamId){
        return convertToDTO(tournamentRepository.findActiveTournamentForTeam(teamId).orElseThrow()); //TODO
    }
    public List<TournamentDTO> getAllByUserId(Long userId){
        Optional<List<Tournament>> tournamentsOptional = tournamentRepository.findAllByUserId(userId);
        List<Tournament> tournaments = tournamentsOptional.orElse(Collections.emptyList());
        return tournaments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    public Tournament addTournament(Tournament tournament){
        return tournamentRepository.save(tournament);
    }
    public Boolean deleteTournamentById(Long tournamentId){
        if(tournamentRepository.existsById(tournamentId)){
            tournamentRepository.deleteById(tournamentId);
            return true;
        }else{
            return false;
        }
    }
    public Boolean addTeamToTournamentByIds(Long teamId, Long tournamentId){
        if (teamRepository.existsById(teamId) && tournamentRepository.existsById(tournamentId)) {
            Team team = teamRepository.findById(teamId).orElseThrow();
            Tournament tournament = tournamentRepository.findById(tournamentId).orElseThrow();
            tournament.addTeamToTournament(team);
            tournamentRepository.save(tournament);
            return true;
        }else{
            return false;
        }
    }
    public TournamentLeagueBase.Status getTournamentStatusByTournamentId(Long tournamentId){
        return tournamentRepository.getTournamentStatusByTournamentId(tournamentId);
    }
    public Integer getNumberOfTeamsByTournamentId(Long tournamentId){
        return tournamentRepository.getNumberOfTeamsByTournamentId(tournamentId).orElse(0);
    }
    public Tournament.TournamentType getTournamentTypeByTournamentId(Long tournamentId){
        return tournamentRepository.getTournamentTypeByTournamentId(tournamentId);
    }
    public Tournament updateTournamentStatusByTournamentId(Long tournamentId, TournamentLeagueBase.Status newStatus){
        Tournament tournamentToUpdate = this.tournamentRepository.findById(tournamentId).orElseThrow();
        tournamentToUpdate.setStatus(newStatus);
        return tournamentRepository.save(tournamentToUpdate);
    }

    public TournamentDTO convertToDTO(Tournament tournament){
        TournamentDTO dto = new TournamentDTO();
        dto.setId(tournament.getId());
        dto.setName(tournament.getName());
        dto.setNumberOfTeams(tournament.getNumberOfTeams());
        dto.setStartDate(tournament.getStartDate());
        dto.setEndDate(tournament.getEndDate());
        dto.setStatus(tournament.getStatus());
        dto.setType(tournament.getType());
        return dto;
    }
}
