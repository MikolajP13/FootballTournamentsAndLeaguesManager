package com.mp.footballtournamentsandleaguesmanager.service;

import com.mp.footballtournamentsandleaguesmanager.DTO.TeamStatisticsDTO;
import com.mp.footballtournamentsandleaguesmanager.DTO.TournamentStandingDTO;
import com.mp.footballtournamentsandleaguesmanager.model.Tournament;
import com.mp.footballtournamentsandleaguesmanager.utils.StandingMapper;
import com.mp.footballtournamentsandleaguesmanager.utils.TeamComparator;
import com.mp.footballtournamentsandleaguesmanager.model.TournamentStanding;
import com.mp.footballtournamentsandleaguesmanager.repository.TournamentStandingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TournamentStandingService {
    private final TournamentStandingRepository tournamentStandingRepository;
    private final MatchService matchService;
    private final CardService cardService;

    public List<TournamentStanding> addTournamentStanding(List<TournamentStanding> tournamentStandings){
        return tournamentStandingRepository.saveAll(tournamentStandings);
    }

    public List<TournamentStandingDTO> getTournamentStandingByTournamentIdAndGroupId(Long tournamentId, int groupId){
        Optional<List<TournamentStanding>> optionalTournamentStandingList = tournamentStandingRepository.getTournamentStandingByTournamentIdAndGroupId(tournamentId, groupId);
        List<TournamentStanding> tournamentStandingList = optionalTournamentStandingList.orElse(Collections.emptyList());

        if(!tournamentStandingList.isEmpty()){
            if(tournamentStandingList.stream().anyMatch(t -> t.getMatches() > 0))
                tournamentStandingList.sort(new TeamComparator<>(tournamentId, matchService, cardService));
            else
                tournamentStandingList.sort(Comparator.comparing(t -> t.getTeam().getName()));
        }

        return tournamentStandingList.stream()
                .map(this::convertToDTO)
                .peek(e -> e.setTeamForm(this.matchService.findLastFiveMatchesByTeamIdAndTournamentId(tournamentId, e.getTeamId(), e.getGroupId())))
                .collect(Collectors.toList());
    }

    public TournamentStanding updateTournamentStanding(Long tournamentId, int groupId, Long teamId, TournamentStandingDTO tournamentStandingDTO){
        TournamentStanding tournamentStandingToUpdate = this.tournamentStandingRepository.findByTournamentIdAndGroupIdAndTeamId(tournamentId, groupId, teamId).orElseThrow();
        return tournamentStandingRepository.save(StandingMapper.updateStandingAndReturn(tournamentStandingToUpdate, tournamentStandingDTO));
    }

    public List<TeamStatisticsDTO> getAllByTeamId(Long teamId) {
        Optional<List<TournamentStanding>> optionalTournamentStandingList = tournamentStandingRepository.getAllByTeamId(teamId);
        List<TournamentStanding> tournamentStandingList = optionalTournamentStandingList.orElse(Collections.emptyList());

        return tournamentStandingList.stream()
                .map(this::convertToTeamStatisticsDTO)
                .collect(Collectors.toList());
    }

    private String getRoundName (int currentTeamRound, int numberOfTeams, Tournament.TournamentType tournamentType) {

        if (tournamentType == Tournament.TournamentType.GROUP_AND_KNOCKOUT && currentTeamRound == 0) {
            return "Group Stage";
        }

        if (tournamentType == Tournament.TournamentType.GROUP_AND_KNOCKOUT) {
            switch (numberOfTeams) {
                case 4 -> {
                    if (currentTeamRound == 1) return "Final";
                }
                case 8 -> {
                    if (currentTeamRound == 1) return "1/2";
                    else if (currentTeamRound == 2) return "Final";
                }
                case 16 -> {
                    if (currentTeamRound == 1) return "1/4";
                    else if (currentTeamRound == 2) return "1/2";
                    else if (currentTeamRound == 3) return "Final";
                }
                case 32 -> {
                    if (currentTeamRound == 1) return "1/8";
                    else if (currentTeamRound == 2) return "1/4";
                    else if (currentTeamRound == 3) return "1/2";
                    else if (currentTeamRound == 4) return "Final";
                }
                default -> {
                    return "";
                }
            }
        } else if (tournamentType == Tournament.TournamentType.SINGLE_ELIMINATION) {
            switch (numberOfTeams) {
                case 4 -> {
                    if (currentTeamRound == 1) return "1/2";
                    else if (currentTeamRound == 2) return "Final";
                }
                case 8 -> {
                    if (currentTeamRound == 1) return "1/4";
                    else if (currentTeamRound == 2) return "1/2";
                    else if (currentTeamRound == 3) return "Final";
                }
                case 16 -> {
                    if (currentTeamRound == 1) return "1/8";
                    else if (currentTeamRound == 2) return "1/4";
                    else if (currentTeamRound == 3) return "1/2";
                    else if (currentTeamRound == 4) return "Final";
                }
                case 32 -> {
                    if (currentTeamRound == 1) return "1/16";
                    else if (currentTeamRound == 2) return "1/8";
                    else if (currentTeamRound == 3) return "1/4";
                    else if (currentTeamRound == 4) return "1/2";
                    else if (currentTeamRound == 5) return "Final";
                }
                default -> {
                    return "";
                }
            }
        }

        return "";
    }

    public TournamentStandingDTO convertToDTO(TournamentStanding tournamentStanding) {
        return StandingMapper.convertToDTO(tournamentStanding, new TournamentStandingDTO());
    }

    public TeamStatisticsDTO convertToTeamStatisticsDTO(TournamentStanding tournamentStanding) {
        TeamStatisticsDTO dto = StandingMapper.convertToDTO(tournamentStanding, new TeamStatisticsDTO());
        dto.setCompetitionId(tournamentStanding.getTournament().getId());
        dto.setCompetitionName(tournamentStanding.getTournament().getName());
        dto.setCompetitionType(tournamentStanding.getTournament().getType().name());

        int round = this.matchService.getMaxRoundByTournamentIdAndTeamId(dto.getCompetitionId(), dto.getTeamId());
        int numberOfTeams = tournamentStanding.getTournament().getNumberOfTeams();
        Tournament.TournamentType type = tournamentStanding.getTournament().getType();

        dto.setTournamentRound(this.getRoundName(round, numberOfTeams, type));

        return dto;
    }
}
