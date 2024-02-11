package com.mp.footballtournamentsandleaguesmanager.service;

import com.mp.footballtournamentsandleaguesmanager.DTO.LeagueStandingDTO;
import com.mp.footballtournamentsandleaguesmanager.DTO.LeagueTeamStatisticsDTO;
import com.mp.footballtournamentsandleaguesmanager.DTO.LeagueTeamStatisticsDTO;
import com.mp.footballtournamentsandleaguesmanager.DTO.TeamStatisticsDTO;
import com.mp.footballtournamentsandleaguesmanager.model.Tournament;
import com.mp.footballtournamentsandleaguesmanager.model.TournamentStanding;
import com.mp.footballtournamentsandleaguesmanager.utils.StandingMapper;
import com.mp.footballtournamentsandleaguesmanager.utils.TeamComparator;
import com.mp.footballtournamentsandleaguesmanager.model.LeagueStanding;
import com.mp.footballtournamentsandleaguesmanager.repository.LeagueStandingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@AllArgsConstructor
public class LeagueStandingService {

    private final LeagueStandingRepository leagueStandingRepository;
    private final MatchService matchService;
    private final CardService cardService;

    public List<LeagueStanding> addLeagueStanding(List<LeagueStanding> leagueStandings) {
        return leagueStandingRepository.saveAll(leagueStandings);
    }

    public LeagueStanding updateLeagueStanding(Long leagueId, Long teamId, LeagueStandingDTO leagueStandingDTO) {
        LeagueStanding leagueStandingToUpdate = this.leagueStandingRepository.findByLeagueIdAndTeamId(leagueId, teamId).orElseThrow();

        return leagueStandingRepository.save(StandingMapper.updateStandingAndReturn(leagueStandingToUpdate, leagueStandingDTO));
    }

    public List<LeagueStandingDTO> getLeagueStandingByLeagueId(Long leagueId){
        Optional<List<LeagueStanding>> optionalLeagueStandingList = leagueStandingRepository.getLeagueStandingByLeagueId(leagueId);
        List<LeagueStanding> leagueStandingList = optionalLeagueStandingList.orElse(Collections.emptyList());

        if(!leagueStandingList.isEmpty()){
            if(matchService.countAllByLeagueIdAndIsMatchProtocolCreated(leagueId, true) == 0)
                leagueStandingList.sort(Comparator.comparing(t -> t.getTeam().getName()));
            else
                leagueStandingList.sort(new TeamComparator<>(leagueId, matchService, cardService));
        }

        return leagueStandingList.stream()
                .map(this::convertToDTO)
                .peek(e -> e.setTeamForm(this.matchService.findLastFiveMatchesByTeamIdInLeagueId(leagueId, e.getTeamId())))
                .toList();
    }

    public List<LeagueTeamStatisticsDTO> getLeagueStandingsByLeagueIdOrderByGoalsForDesc(Long leagueId){
        Optional<List<LeagueStanding>> optionalLeagueStandingList = leagueStandingRepository.getLeagueStandingsByLeagueIdOrderByGoalsForDesc(leagueId);
        List<LeagueStanding> leagueStandingList = optionalLeagueStandingList.orElse(Collections.emptyList());
        List<LeagueTeamStatisticsDTO> teamStatistics = leagueStandingList.stream().map(this::convertToLeagueTeamStatisticsDTO).toList();
        this.setTeamGoalRanks(teamStatistics);

        return teamStatistics;
    }

    public List<LeagueTeamStatisticsDTO> getLeagueStandingsByLeagueIdOrderByWinsDesc(Long leagueId){
        Optional<List<LeagueStanding>> optionalLeagueStandingList = leagueStandingRepository.getLeagueStandingsByLeagueIdOrderByWinsDesc(leagueId);
        List<LeagueStanding> leagueStandingList = optionalLeagueStandingList.orElse(Collections.emptyList());
        List<LeagueTeamStatisticsDTO> teamStatistics = leagueStandingList.stream().map(this::convertToLeagueTeamStatisticsDTO).toList();
        this.setTeamWinRanks(teamStatistics);

        return teamStatistics;
    }

    public List<LeagueTeamStatisticsDTO> getLeagueStandingsByLeagueIdOrderByLossesDesc(Long leagueId){
        Optional<List<LeagueStanding>> optionalLeagueStandingList = leagueStandingRepository.getLeagueStandingsByLeagueIdOrderByLossesDesc(leagueId);
        List<LeagueStanding> leagueStandingList = optionalLeagueStandingList.orElse(Collections.emptyList());
        List<LeagueTeamStatisticsDTO> teamStatistics = leagueStandingList.stream().map(this::convertToLeagueTeamStatisticsDTO).toList();
        this.setLossRanks(teamStatistics);

        return teamStatistics;
    }

    public List<TeamStatisticsDTO> getAllByTeamId(Long teamId) {
        Optional<List<LeagueStanding>> optionalLeagueStandingList = leagueStandingRepository.getAllByTeamId(teamId);
        List<LeagueStanding> leagueStandingList = optionalLeagueStandingList.orElse(Collections.emptyList());

        return leagueStandingList.stream()
                .map(this::convertToTeamStatisticsDTO)
                .collect(Collectors.toList());
    }

    private void setTeamGoalRanks(List<LeagueTeamStatisticsDTO> list) {
        int rank = 0;
        int previousTeamStatistic = -1;

        for (LeagueTeamStatisticsDTO teamStanding : list) {
            if (teamStanding.getGoals() == previousTeamStatistic) {
                teamStanding.setRank(rank);
            } else {
                rank++;
                teamStanding.setRank(rank);
                previousTeamStatistic = teamStanding.getGoals();
            }
        }
    }

    private void setTeamWinRanks(List<LeagueTeamStatisticsDTO> list) {
        int rank = 0;
        int previousTeamStatistic = -1;

        for (LeagueTeamStatisticsDTO teamStanding : list) {
            if (teamStanding.getWins() == previousTeamStatistic) {
                teamStanding.setRank(rank);
            } else {
                rank++;
                teamStanding.setRank(rank);
                previousTeamStatistic = teamStanding.getWins();
            }
        }
    }

    private void setLossRanks(List<LeagueTeamStatisticsDTO> list) {
        int rank = 0;
        int previousTeamStatistic = -1;

        for (LeagueTeamStatisticsDTO player : list) {
            if (player.getLosses() == previousTeamStatistic) {
                player.setRank(rank);
            } else {
                rank++;
                player.setRank(rank);
                previousTeamStatistic = player.getLosses();
            }
        }
    }

    public LeagueStandingDTO convertToDTO(LeagueStanding leagueStanding){
        return StandingMapper.convertToDTO(leagueStanding, new LeagueStandingDTO());
    }

    public LeagueTeamStatisticsDTO convertToLeagueTeamStatisticsDTO(LeagueStanding leagueStanding) {
        LeagueTeamStatisticsDTO dto = new LeagueTeamStatisticsDTO();
        dto.setId(leagueStanding.getId());
        dto.setTeamId(leagueStanding.getTeam().getId());
        dto.setTeamName(leagueStanding.getTeam().getName());
        dto.setGoals(leagueStanding.getGoalsFor());
        dto.setWins(leagueStanding.getWins());
        dto.setLosses(leagueStanding.getLosses());

        return dto;
    }

    public TeamStatisticsDTO convertToTeamStatisticsDTO(LeagueStanding leagueStanding) {
        TeamStatisticsDTO dto = StandingMapper.convertToDTO(leagueStanding, new TeamStatisticsDTO());
        dto.setCompetitionId(leagueStanding.getLeague().getId());
        dto.setCompetitionName(leagueStanding.getLeague().getName());
        dto.setCompetitionType(leagueStanding.getLeague().getType().name());

        List<LeagueStandingDTO> standing = this.getLeagueStandingByLeagueId(dto.getCompetitionId());
        int rank = IntStream.range(0, standing.size())
                .filter(i -> standing.get(i).getTeamId().equals(dto.getTeamId()))
                .findFirst()
                .orElse(-1);

        dto.setLeagueRank(rank + 1);

        return dto;
    }
}
