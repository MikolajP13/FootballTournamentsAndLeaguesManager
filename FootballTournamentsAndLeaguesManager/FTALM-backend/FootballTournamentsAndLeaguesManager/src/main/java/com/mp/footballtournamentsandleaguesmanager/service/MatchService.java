package com.mp.footballtournamentsandleaguesmanager.service;

import com.mp.footballtournamentsandleaguesmanager.DTO.MatchDTO;
import com.mp.footballtournamentsandleaguesmanager.DTO.MatchEventDTO;
import com.mp.footballtournamentsandleaguesmanager.model.Match;
import com.mp.footballtournamentsandleaguesmanager.model.Player;
import com.mp.footballtournamentsandleaguesmanager.repository.MatchRepository;
import com.mp.footballtournamentsandleaguesmanager.repository.PlayerRepository;
import com.mp.footballtournamentsandleaguesmanager.repository.TeamRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final static String TEAM_WIN = "W";
    private final static String TEAM_DRAW = "D";
    private final static String TEAM_LOSS = "L";

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

    public List<MatchDTO> getAllByTournamentIdAndRoundAndMatchweek(Long tournamentId, int round, int matchweek){
        Optional<List<Match>> optionalList = matchRepository.getAllByTournamentIdAndRoundAndMatchweek(tournamentId, round, matchweek);
        List<Match> matchList = optionalList.orElse(Collections.emptyList());
        return matchList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<MatchDTO> getAllByTournamentIdAndRoundIsGreaterThanEqual(Long tournamentId, int firstRound) {
        Optional<List<Match>> optionalList = matchRepository.getAllByTournamentIdAndRoundIsGreaterThanEqual(tournamentId, firstRound);
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

    public boolean updatePlayersStatistics(List<MatchEventDTO> matchEventList) {
        matchEventList.forEach(event -> {
            if (event.getEvent() == MatchEventDTO.EventType.GOAL) {
                this.updatePlayerGoalsAssistsStatistic(event.getFirstPlayerId(), event.getSecondPlayerId());
            } else if (event.getEvent() == MatchEventDTO.EventType.YELLOW_CARD ||
                    event.getEvent() == MatchEventDTO.EventType.SECOND_YELLOW_CARD ||
                    event.getEvent() == MatchEventDTO.EventType.RED_CARD) {
                this.updatePlayerCardsStatistic(event.getFirstPlayerId(), event.getEvent());
            }
        });
        return true;
    }

    private void updatePlayerGoalsAssistsStatistic(Long scorerPlayerId, Long assistPlayerId) {
        Optional<Player> optionalScorerPlayer = playerRepository.findById(scorerPlayerId);
        Optional<Player> optionalAssistPlayer = Optional.empty();

        if(assistPlayerId != null)
            optionalAssistPlayer = playerRepository.findById(assistPlayerId);

        if (optionalScorerPlayer.isEmpty() && optionalAssistPlayer.isEmpty())
            throw new RuntimeException("Scorer player with id=" + scorerPlayerId + "and assist player with id=" + assistPlayerId + " do not exist.");

        Player scorerPlayer = optionalScorerPlayer.get();

        if (optionalAssistPlayer.isEmpty()) { // penalty or free kick goal
            scorerPlayer.setGoals(scorerPlayer.getGoals() + 1);
        } else {
            Player assistPlayer = optionalAssistPlayer.get();
            scorerPlayer.setGoals(scorerPlayer.getGoals() + 1);
            assistPlayer.setAssists(assistPlayer.getAssists() + 1);
            playerRepository.save(assistPlayer);
        }

        playerRepository.save(scorerPlayer);
    }

    private void updatePlayerCardsStatistic(Long playerId, MatchEventDTO.EventType cardType) {
        Optional<Player> optionalPlayer = playerRepository.findById(playerId);

        if (optionalPlayer.isEmpty())
            throw new RuntimeException("Player with id=" + playerId + " does not exist.");

        Player player = optionalPlayer.get();

        if (cardType == MatchEventDTO.EventType.YELLOW_CARD) {
            player.setYellowCards(player.getYellowCards() + 1);
        } else if (cardType == MatchEventDTO.EventType.SECOND_YELLOW_CARD) {
            player.setSecondYellowCards(player.getSecondYellowCards() + 1);
            // receiving a second yellow card results in one red card in the statistics
            player.setYellowCards(player.getYellowCards() - 1);
            player.setRedCards(player.getRedCards() + 1);
        } else if (cardType == MatchEventDTO.EventType.RED_CARD) {
            player.setRedCards(player.getRedCards() + 1);
        }

        playerRepository.save(player);
    }

    public List<String> findLastFiveMatchesByTeamIdInLeagueId(Long leagueId, Long teamId){
        List<String> teamForm = new ArrayList<>();
        Optional<List<Match>> optionalMatches = this.matchRepository.findLastFiveMatchesByTeamIdAndLeagueId(leagueId, teamId);

        return getTeamFormStringArray(teamId, teamForm, optionalMatches);
    }

    public List<String> findLastFiveMatchesByTeamIdAndTournamentId(Long tournamentId, Long teamId, int groupId) {
        List<String> teamForm = new ArrayList<>();
        Optional<List<Match>> optionalMatches = this.matchRepository.findLastFiveMatchesByTeamIdAndTournamentId(tournamentId, teamId, groupId);

        return getTeamFormStringArray(teamId, teamForm, optionalMatches);
    }

    private List<String> getTeamFormStringArray(Long teamId, List<String> teamForm, Optional<List<Match>> optionalMatches) {
        if (optionalMatches.isPresent()) {
            List<Match> matches = optionalMatches.get();
            matches.forEach(match -> {
                if (match.getHomeTeamScore().equals(match.getAwayTeamScore())) {
                    teamForm.add(TEAM_DRAW);
                } else if (match.getHomeTeamScore() < match.getAwayTeamScore()) {
                    if (match.getHomeTeamId().equals(teamId))
                        teamForm.add(TEAM_LOSS);
                    else
                        teamForm.add(TEAM_WIN);
                } else {
                    if (match.getHomeTeamId().equals(teamId))
                        teamForm.add(TEAM_WIN);
                    else
                        teamForm.add(TEAM_LOSS);
                }
            });
        }

        return teamForm;
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
