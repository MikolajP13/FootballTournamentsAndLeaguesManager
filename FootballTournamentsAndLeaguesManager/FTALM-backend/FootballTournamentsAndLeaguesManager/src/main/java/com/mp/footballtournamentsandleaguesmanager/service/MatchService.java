package com.mp.footballtournamentsandleaguesmanager.service;

import com.mp.footballtournamentsandleaguesmanager.DTO.MatchDTO;
import com.mp.footballtournamentsandleaguesmanager.model.Match;
import com.mp.footballtournamentsandleaguesmanager.model.Team;
import com.mp.footballtournamentsandleaguesmanager.repository.MatchRepository;
import com.mp.footballtournamentsandleaguesmanager.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MatchService {
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public MatchService(MatchRepository matchRepository,
                        TeamRepository teamRepository) {
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
    }

    public MatchDTO getMatchById(Long matchId){
        return convertToDTO(matchRepository.findById(matchId).orElseThrow());
    }

    public Match createMatch(Match match){
        //TODO: check if league or tournament exists/status is not end!
        return matchRepository.save(match);
    }

    public List<Match> createMatches(List<Match> matchList){
        return matchRepository.saveAll(matchList);
    }

    public Match updateMatch(Long matchId, MatchDTO matchDTO) {
        Match matchToUpdate = this.matchRepository.findById(matchId).orElseThrow();
        matchToUpdate.setDate(matchDTO.getDate());
        matchToUpdate.setAwayTeamScore(matchDTO.getAwayTeamScore());
        matchToUpdate.setHomeTeamScore(matchDTO.getHomeTeamScore());
        matchToUpdate.setMatchProtocolCreated(matchDTO.isMatchProtocolCreated());
        return matchRepository.save(matchToUpdate);
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

    public MatchDTO getMatchByHomeTeamIdAndAwayTeamIdAndMatchProtocolCreatedAndLeagueId(Long homeTeamId, Long awayTeamId, boolean matchProtocolCreated, Long leagueId){
        Optional<Match> optionalMatch = this.matchRepository
                .getMatchByHomeTeamIdAndAwayTeamIdAndIsMatchProtocolCreatedAndLeagueId(homeTeamId, awayTeamId,
                        matchProtocolCreated, leagueId);

        return optionalMatch.map(this::convertToDTO).orElse(null);
    }

    public Integer countMatchesByLeagueId(Long leagueId){
        Optional<Integer> optionalNumberOfAllLeagueMatches = this.matchRepository.countMatchesByLeagueId(leagueId);
        return optionalNumberOfAllLeagueMatches.orElse(0);
    }

    public Integer countAllByLeagueIdAndIsMatchProtocolCreated(Long leagueId, boolean isProtocolCreated){
        Optional<Integer> optionalNumberOfCreatedMatchProtocols = this.matchRepository.countAllByLeagueIdAndIsMatchProtocolCreated(leagueId, isProtocolCreated);
        return optionalNumberOfCreatedMatchProtocols.orElse(0);
    }

    public MatchDTO convertToDTO(Match match) {
        MatchDTO dto = new MatchDTO();

        dto.setId(match.getId());
        dto.setDate(match.getDate());
        dto.setHomeTeamId(match.getHomeTeamId());
        dto.setAwayTeamId(match.getAwayTeamId());
        dto.setHomeTeamScore(match.getHomeTeamScore());
        dto.setAwayTeamScore(match.getAwayTeamScore());

        if (match.getHomeTeamId() != null && teamRepository.findById(match.getHomeTeamId()).isPresent())
            dto.setHomeTeamName(teamRepository.findById(match.getHomeTeamId()).get().getName());
        else
            dto.setHomeTeamName("Blank Team");

        if (match.getAwayTeamId() != null && teamRepository.findById(match.getAwayTeamId()).isPresent())
            dto.setAwayTeamName(teamRepository.findById(match.getAwayTeamId()).get().getName());
        else
            dto.setAwayTeamName("Blank Team");

        if (match.getTournament() != null)
            dto.setTournamentId(match.getTournament().getId());
        else
            dto.setLeagueId(match.getLeague().getId());

        dto.setMatchweek(match.getMatchweek());
        dto.setRound(match.getRound());
        dto.setMatchProtocolCreated(match.isMatchProtocolCreated());

        return dto;
    }
}
