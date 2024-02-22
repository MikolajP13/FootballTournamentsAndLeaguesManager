package com.mp.footballtournamentsandleaguesmanager.service;

import com.mp.footballtournamentsandleaguesmanager.DTO.LeagueDTO;
import com.mp.footballtournamentsandleaguesmanager.DTO.LeagueStandingDTO;
import com.mp.footballtournamentsandleaguesmanager.model.*;
import com.mp.footballtournamentsandleaguesmanager.repository.LeagueRepository;
import com.mp.footballtournamentsandleaguesmanager.repository.LeagueStandingRepository;
import com.mp.footballtournamentsandleaguesmanager.repository.MatchRepository;
import com.mp.footballtournamentsandleaguesmanager.repository.TeamRepository;
import com.mp.footballtournamentsandleaguesmanager.utils.CompetitionMatchCreator;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LeagueService {
    private final LeagueRepository leagueRepository;
    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;
    private final LeagueStandingRepository leagueStandingRepository;
    private final LeagueStandingService leagueStandingService;

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
        return leagueRepository.findActiveLeagueForTeam(teamId)
                .map(this::convertToDTO)
                .orElseGet(LeagueDTO::new);
    }
    public TournamentLeagueBase.Status getLeagueStatusByLeagueId(Long leagueId){
        return leagueRepository.getStatusByLeagueId(leagueId);
    }
    public Integer getNumberOfTeamsByLeagueId(Long leagueId){
        return leagueRepository.getNumberOfTeamsByLeagueId(leagueId).orElse(0);
    }
    public League.LeagueType getLeagueTypeByLeagueId(Long leagueId){
        return leagueRepository.getLeagueTypeByLeagueId(leagueId);
    }
    public String getLeagueNameByLeagueId(Long leagueId){
        return leagueRepository.getLeagueNameByLeagueId(leagueId);
    }

    public League updateLeagueStatusByLeagueId(Long leagueId, TournamentLeagueBase.Status newStatus){
        League leagueToUpdate = this.leagueRepository.findById(leagueId).orElseThrow();
        leagueToUpdate.setStatus(newStatus);
        return leagueRepository.save(leagueToUpdate);
    }

    public boolean startLeague(Long leagueId) {
        Optional<League> leagueOptional = leagueRepository.findById(leagueId);
        List<Match> matches;
        List<LeagueStanding> leagueStandings;

        if(leagueOptional.isEmpty())
            throw new RuntimeException();

        League league = leagueOptional.get();
        league.setStatus(TournamentLeagueBase.Status.IN_PROGRESS);

        Optional<List<Team>> optionalTeamList = teamRepository.findAllTeamsInLeagueByLeagueId(leagueId);
        List<Team> teams = optionalTeamList.orElse(Collections.emptyList());

        // create league matches
        matches = CompetitionMatchCreator.createMatchesForLeague(league, teams, true, false, 0);

        // create league standings
        teams.removeIf(team -> team.getId() == null); //remove extra added team if number of teams was not even
        leagueStandings = CompetitionMatchCreator.createLeagueStanding(league, teams);

        // save updated league status and created records
        matchRepository.saveAll(matches);
        leagueStandingRepository.saveAll(leagueStandings);
        leagueRepository.save(league);

        return true;
    }

    public boolean checkAndTryCompleteLeague(Long leagueId) {
        Optional<League> leagueOptional = this.leagueRepository.findById(leagueId);
        Optional<Integer> optionalNumberOfMatchesWithoutCreatedProtocol =
                this.matchRepository.getNumberOfMatchesInLeagueInSelectedRoundWithoutCreatedMatchProtocol(leagueId, 0);
        int numberOfMatchesWithoutCreatedProtocol = optionalNumberOfMatchesWithoutCreatedProtocol.orElse(-1);

        if(leagueOptional.isEmpty()) {
            throw new RuntimeException("League with id=" + leagueId + " does not exist!");
        }

        League league = leagueOptional.get();

        if(league.getType().equals(League.LeagueType.STANDARD_MODE) && numberOfMatchesWithoutCreatedProtocol == 0) {
            finishLeague(leagueId, league);

            return true;
        } else if (league.getType().equals(League.LeagueType.SPLIT_MODE) && numberOfMatchesWithoutCreatedProtocol == 0) {
            Optional<Integer> optionalNumberOfMatchesInSplitModeLeague = this.matchRepository.getNumberOfMatchesInSplitModeLeague(leagueId);
            Integer numberOfMatchesInSplitModeLeague = optionalNumberOfMatchesInSplitModeLeague.orElse(-1);

            if (numberOfMatchesInSplitModeLeague > 0) {
                Optional<Integer> optionalNumberOfMatchesWithoutCreatedMatchProtocolAfterSplit =
                        this.matchRepository.getNumberOfMatchesInLeagueInSelectedRoundWithoutCreatedMatchProtocol(leagueId, 1);
                Integer numberOfMatchesWithoutCreatedMatchProtocolAfterSplit = optionalNumberOfMatchesWithoutCreatedMatchProtocolAfterSplit.orElse(-1);

                if (numberOfMatchesWithoutCreatedMatchProtocolAfterSplit == 0) {
                    finishLeague(leagueId, league);
                    return true;
                }
                return false;
            }
            List<Team> teams = new ArrayList<>();
            List<Team> championGroupTeams = new ArrayList<>();
            List<Team> relegationGroupTeams = new ArrayList<>();
            List<LeagueStandingDTO> leagueStandingsDTO = this.leagueStandingService.getLeagueStandingByLeagueId(leagueId);
            List<LeagueStanding> leagueStandings = new ArrayList<>();

            for (LeagueStandingDTO standing : leagueStandingsDTO) {
                teams.add(teamRepository.findById(standing.getTeamId()).orElseThrow());
            }

            int numberOfTeams = teams.size();
            int division = (int) Math.ceil(numberOfTeams/2.0);

            for (int i = 0; i < numberOfTeams; i++) {
                LeagueStanding ls = leagueStandingRepository.findById(leagueStandingsDTO.get(i).getId()).orElseThrow();
                if (i < division) {
                    championGroupTeams.add(teams.get(i));
                    ls.setLeagueStandingType(LeagueStanding.LeagueStandingType.CHAMPION);
                }
                else {
                    relegationGroupTeams.add(teams.get(i));
                    ls.setLeagueStandingType(LeagueStanding.LeagueStandingType.RELEGATION);
                }
                leagueStandings.add(ls);
            }

            List<Match> matchesForChampionGroup = CompetitionMatchCreator.createMatchesForLeague(league, championGroupTeams, false, true, numberOfTeams);
            List<Match> matchesForRelegationGroup = CompetitionMatchCreator.createMatchesForLeague(league, relegationGroupTeams, false, true, numberOfTeams);

            matchRepository.saveAll(matchesForChampionGroup);
            matchRepository.saveAll(matchesForRelegationGroup);
            leagueStandingRepository.saveAll(leagueStandings);

            return false;
        }
        return false;
    }


    private void finishLeague(Long leagueId, League league) {
        league.setStatus(TournamentLeagueBase.Status.FINISHED);
        league.setEndDate(Date.valueOf(LocalDate.now()));

        Optional<List<Team>> allTeamsInLeagueByLeagueId = this.teamRepository.findAllTeamsInLeagueByLeagueId(leagueId);
        List<Team> leagueTeamList = allTeamsInLeagueByLeagueId.orElse(Collections.emptyList());

        leagueTeamList.forEach(t -> t.setInLeague(false));

        this.leagueRepository.save(league);
        this.teamRepository.saveAll(leagueTeamList);
    }

    public LeagueDTO convertToDTO(League league) {
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
