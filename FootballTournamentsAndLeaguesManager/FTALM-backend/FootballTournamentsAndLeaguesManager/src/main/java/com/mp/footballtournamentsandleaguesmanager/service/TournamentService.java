package com.mp.footballtournamentsandleaguesmanager.service;

import com.mp.footballtournamentsandleaguesmanager.DTO.TournamentDTO;
import com.mp.footballtournamentsandleaguesmanager.DTO.TournamentStandingDTO;
import com.mp.footballtournamentsandleaguesmanager.model.*;
import com.mp.footballtournamentsandleaguesmanager.repository.MatchRepository;
import com.mp.footballtournamentsandleaguesmanager.repository.TeamRepository;
import com.mp.footballtournamentsandleaguesmanager.repository.TournamentRepository;
import com.mp.footballtournamentsandleaguesmanager.repository.TournamentStandingRepository;
import com.mp.footballtournamentsandleaguesmanager.utils.CompetitionMatchCreator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TournamentService {
    private final TournamentRepository tournamentRepository;
    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;
    private final TournamentStandingService tournamentStandingService;
    private final TournamentStandingRepository tournamentStandingRepository;

    public TournamentDTO getTournamentById(Long tournamentId){
        return convertToDTO(tournamentRepository.findById(tournamentId).orElseThrow()); //TODO
    }
    public TournamentDTO findActiveTournamentForTeam(Long teamId){
        return tournamentRepository.findActiveTournamentForTeam(teamId)
                .map(this::convertToDTO)
                .orElseGet(TournamentDTO::new);
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
    public boolean startTournament(Long tournamentId) {
        Optional<Tournament> tournamentOptional = tournamentRepository.findById(tournamentId);
        List<Match> matches;
        List<TournamentStanding> tournamentStandings;

        if(tournamentOptional.isEmpty())
            throw new RuntimeException();

        Tournament tournament = tournamentOptional.get();
        tournament.setStatus(TournamentLeagueBase.Status.IN_PROGRESS);

        Optional<List<Team>> optionalTeamList = teamRepository.findAllTeamsInTournamentByTournamentId(tournamentId);
        List<Team> teams = optionalTeamList.orElse(Collections.emptyList());

        // if tournament type is Group and Knockout then create tournament standings and group matches
        if (tournament.getType().equals(Tournament.TournamentType.GROUP_AND_KNOCKOUT)) {
            // create tournament standings and matches
            CompetitionMatchCreator.TournamentStandingAndMatches tournamentStandingAndMatches = CompetitionMatchCreator.createTournamentStandingAndMatches(tournament, teams);
            matches = tournamentStandingAndMatches.getMatches();
            tournamentStandings = tournamentStandingAndMatches.getTournamentStandings();
            matchRepository.saveAll(matches);
            tournamentStandingRepository.saveAll(tournamentStandings);
        } else if (tournament.getType().equals(Tournament.TournamentType.SINGLE_ELIMINATION)) {
            // create tournament matches
            matches = CompetitionMatchCreator.createTournamentPairMatches(tournament, teams, 1, true);
            matchRepository.saveAll(matches);
        }
        // save updated league status and created records
        tournamentRepository.save(tournament);

        return true;
    }

    public boolean checkAndTryCompleteTournament(Long tournamentId) {
        Optional<Tournament> optionalTournament = this.tournamentRepository.findById(tournamentId);
        int numberOfTeams;
        int numberOfRounds;
        int currentCheckingRound = 1;
        boolean allRoundsCompleted = true;

        if(optionalTournament.isEmpty())
            throw new RuntimeException("Tournament with id=" + tournamentId + "does not exist!");

        Tournament tournament = optionalTournament.get();
        numberOfTeams = this.tournamentRepository.getNumberOfTeamsByTournamentId(tournamentId).orElse(-1);

        if(numberOfTeams < 0)
            throw new RuntimeException();

        if (tournament.getType().equals(Tournament.TournamentType.SINGLE_ELIMINATION)) {
            numberOfRounds = (int) (Math.log(numberOfTeams) / Math.log(2));

            return this.createNextRoundMatches(allRoundsCompleted, numberOfRounds, currentCheckingRound, tournamentId, tournament);
        } else if (tournament.getType().equals(Tournament.TournamentType.GROUP_AND_KNOCKOUT)) {
            numberOfRounds = (int) (Math.log(numberOfTeams/2.0) / Math.log(2));

            if (this.matchRepository.getNumberOfMatchesInTournamentInSelectedRoundWithoutCreatedMatchProtocol(tournamentId, 0).orElse(-1) != 0) {
                return false;
            }

            if (this.matchRepository.getNumberOfMatchesInTournamentBySelectedRound(tournamentId, 1).orElse(-1) == 0) {
                // create matches for 1st round
                int numberOfGroups = numberOfTeams / 4;
                List<Team> promotedTeams = new ArrayList<>();

                for (int i = 0; i < numberOfGroups; i++) {
                    List<TournamentStandingDTO> standing = this.tournamentStandingService.getTournamentStandingByTournamentIdAndGroupId(tournamentId, i);
                    promotedTeams.add(this.teamRepository.findById(standing.get(0).getTeamId()).orElseThrow());
                    promotedTeams.add(this.teamRepository.findById(standing.get(1).getTeamId()).orElseThrow());
                }

                if (numberOfTeams > 4) {
                    promotedTeams = this.sortListOfTeamsForTournamentBracketMatches(promotedTeams, numberOfTeams);
                }

                //create and save next round matches
                List<Match> nextRoundMatches = CompetitionMatchCreator.createTournamentPairMatches(tournament, promotedTeams, currentCheckingRound, false);
                this.matchRepository.saveAll(nextRoundMatches);

                return false;
            }
            return this.createNextRoundMatches(allRoundsCompleted, numberOfRounds, currentCheckingRound, tournamentId, tournament);
        }

        return false;
    }

    private boolean createNextRoundMatches(boolean allRoundsCompleted, int numberOfRounds, int currentCheckingRound,
                                           Long tournamentId, Tournament tournament) {
        for (int i = 1; i <= numberOfRounds; i++, currentCheckingRound++) {
            int matchesInRound = this.matchRepository.getNumberOfMatchesInTournamentBySelectedRound(tournamentId, i).orElse(-1);
            int numberOfProtocolsToFillOut = this.matchRepository.getNumberOfMatchesInTournamentInSelectedRoundWithoutCreatedMatchProtocol(tournamentId, i).orElse(-1);
            if ((matchesInRound > 0 && numberOfProtocolsToFillOut > 0) || (matchesInRound == 0 && numberOfProtocolsToFillOut == 0)) {
                allRoundsCompleted = false;
                break;
            }
        }

        if (allRoundsCompleted) {
            this.finishTournament(tournamentId, tournament);
            return true;
        } else {
            if (this.matchRepository.getNumberOfMatchesInTournamentBySelectedRound(tournamentId, currentCheckingRound).orElse(-1) == 0 &&
                    this.matchRepository.getNumberOfMatchesInTournamentInSelectedRoundWithoutCreatedMatchProtocol(tournamentId, currentCheckingRound).orElse(-1) == 0) {
                List<Team> winnersInPreviousRound = new ArrayList<>();
                Optional<List<Match>> optionalMatches = this.matchRepository.getMatchesInTournamentBySelectedRound(tournamentId, currentCheckingRound - 1);
                List<Match> previousRoundMatches = optionalMatches.orElse(Collections.emptyList());

                if (previousRoundMatches.size() == 0) {
                    throw new RuntimeException();
                }

                //get winners
                List<Long> winnersId = previousRoundMatches.stream().map(match ->
                        match.getHomeTeamScore() > match.getAwayTeamScore() ? match.getHomeTeamId() : match.getAwayTeamId()).toList();

                winnersId.forEach(winnerTeamId -> winnersInPreviousRound.add(this.teamRepository.findById(winnerTeamId).orElseThrow()));

                // create matches for currentCheckingRound
                List<Match> nextRoundMatches = CompetitionMatchCreator.createTournamentPairMatches(tournament, winnersInPreviousRound, currentCheckingRound, false);
                this.matchRepository.saveAll(nextRoundMatches);
            }
            return false;
        }
    }

    private List<Team> sortListOfTeamsForTournamentBracketMatches(List<Team> teams, int numberOfTeams) {
        List<Team> sortedTeamList = new ArrayList<>();
        List<Integer> order8Teams = Arrays.asList(0, 3, 1, 2);
        List<Integer> order16Teams = Arrays.asList(0, 3, 4, 7, 1, 2, 5, 6);
        List<Integer> order32Teams = Arrays.asList(0, 3, 4, 7, 8, 11, 12, 15, 1, 2, 5, 6, 9, 10, 13, 14);

        if (numberOfTeams == 8) {
            for (Integer order8Team : order8Teams) {
                sortedTeamList.add(teams.get(order8Team));
            }
        } else if (numberOfTeams == 16) {
            for (Integer order16Team : order16Teams) {
                sortedTeamList.add(teams.get(order16Team));
            }
        } else if (numberOfTeams == 32) {
            for (Integer order32Team : order32Teams) {
                sortedTeamList.add(teams.get(order32Team));
            }
        }

        return sortedTeamList;
    }

    private void finishTournament(Long tournamentId, Tournament tournament) {
        tournament.setStatus(TournamentLeagueBase.Status.FINISHED);
        tournament.setEndDate(Date.valueOf(LocalDate.now()));

        Optional<List<Team>> allTeamsInTournamentByTournamentId = this.teamRepository.findAllTeamsInTournamentByTournamentId(tournamentId);
        List<Team> tournamentTeamList = allTeamsInTournamentByTournamentId.orElse(Collections.emptyList());

        tournamentTeamList.forEach(t -> t.setInTournament(false));

        this.tournamentRepository.save(tournament);
        this.teamRepository.saveAll(tournamentTeamList);
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
