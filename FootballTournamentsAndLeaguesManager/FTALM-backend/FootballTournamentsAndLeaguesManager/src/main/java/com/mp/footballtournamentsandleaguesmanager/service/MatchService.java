package com.mp.footballtournamentsandleaguesmanager.service;

import com.mp.footballtournamentsandleaguesmanager.DTO.MatchDTO;
import com.mp.footballtournamentsandleaguesmanager.model.Match;
import com.mp.footballtournamentsandleaguesmanager.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MatchService {
    private final MatchRepository matchRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public MatchDTO getMatchById(Long matchId){
        return convertToDTO(matchRepository.findById(matchId).orElseThrow());
    }

    public Match createMatch(Match match){
        //TODO: check if league or tournament exists/status is not end!
        return matchRepository.save(match);
    }

    public List<MatchDTO> getAllByLeagueId(Long leagueId){
        Optional<List<Match>> optionalList = matchRepository.getAllByLeagueId(leagueId);
        List<Match> matchList = optionalList.orElse(Collections.emptyList());
        return matchList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MatchDTO> getAllByTournamentId(Long tournamentId){
        Optional<List<Match>> optionalList = matchRepository.getAllByTournamentId(tournamentId);
        List<Match> matchList = optionalList.orElse(Collections.emptyList());
        return matchList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private MatchDTO convertToDTO(Match match) {
        MatchDTO dto = new MatchDTO();
        dto.setId(match.getId());
        dto.setDate(match.getDate());
        dto.setHomeTeamId(match.getHomeTeam().getId());
        dto.setAwayTeamId(match.getAwayTeam().getId());
        dto.setHomeTeamScore(match.getHomeTeamScore());
        dto.setAwayTeamScore(match.getAwayTeamScore());
        if (match.getTournament() != null) {
            dto.setTournamentId(match.getTournament().getId());
        } else {
            dto.setLeagueId(match.getLeague().getId());
        }
        dto.setMatchweek(match.getMatchweek());
        dto.setRound(match.getRound());
        dto.setMatchProtocolCreated(match.isMatchProtocolCreated());
        return dto;
    }
}
