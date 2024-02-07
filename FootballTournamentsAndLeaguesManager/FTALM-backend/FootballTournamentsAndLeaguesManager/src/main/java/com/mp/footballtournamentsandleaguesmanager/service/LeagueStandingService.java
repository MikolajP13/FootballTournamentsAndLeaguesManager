package com.mp.footballtournamentsandleaguesmanager.service;

import com.mp.footballtournamentsandleaguesmanager.DTO.LeagueStandingDTO;
import com.mp.footballtournamentsandleaguesmanager.utils.StandingMapper;
import com.mp.footballtournamentsandleaguesmanager.utils.TeamComparator;
import com.mp.footballtournamentsandleaguesmanager.model.LeagueStanding;
import com.mp.footballtournamentsandleaguesmanager.repository.LeagueStandingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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

    public List<LeagueStandingDTO> getLeagueStandingsByLeagueIdOrderByGoalsForDesc(Long leagueId){
        Optional<List<LeagueStanding>> optionalLeagueStandingList = leagueStandingRepository.getLeagueStandingsByLeagueIdOrderByGoalsForDesc(leagueId);
        List<LeagueStanding> leagueStandingDTOList = optionalLeagueStandingList.orElse(Collections.emptyList());
        return leagueStandingDTOList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<LeagueStandingDTO> getLeagueStandingsByLeagueIdOrderByWinsDesc(Long leagueId){
        Optional<List<LeagueStanding>> optionalLeagueStandingList = leagueStandingRepository.getLeagueStandingsByLeagueIdOrderByWinsDesc(leagueId);
        List<LeagueStanding> leagueStandingList = optionalLeagueStandingList.orElse(Collections.emptyList());
        return leagueStandingList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<LeagueStandingDTO> getLeagueStandingsByLeagueIdOrderByLossesDesc(Long leagueId){
        Optional<List<LeagueStanding>> optionalLeagueStandingList = leagueStandingRepository.getLeagueStandingsByLeagueIdOrderByLossesDesc(leagueId);
        List<LeagueStanding> leagueStandingList = optionalLeagueStandingList.orElse(Collections.emptyList());
        return leagueStandingList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public LeagueStandingDTO convertToDTO(LeagueStanding leagueStanding){
        return StandingMapper.convertToDTO(leagueStanding, new LeagueStandingDTO());
    }
}
