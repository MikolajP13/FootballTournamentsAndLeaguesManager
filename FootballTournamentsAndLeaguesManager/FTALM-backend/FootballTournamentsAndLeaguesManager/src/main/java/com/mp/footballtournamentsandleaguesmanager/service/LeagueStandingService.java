package com.mp.footballtournamentsandleaguesmanager.service;

import com.mp.footballtournamentsandleaguesmanager.DTO.LeagueStandingDTO;
import com.mp.footballtournamentsandleaguesmanager.business.TeamComparator;
import com.mp.footballtournamentsandleaguesmanager.model.LeagueStanding;
import com.mp.footballtournamentsandleaguesmanager.repository.LeagueRepository;
import com.mp.footballtournamentsandleaguesmanager.repository.LeagueStandingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class LeagueStandingService {
    private final Random random = new Random();
    private final LeagueStandingRepository leagueStandingRepository;
    private final MatchService matchService;
    private final LeagueRepository leagueRepository;
    private final CardService cardService;
    private static final int POINTS_FOR_WIN = 3;
    private static final int POINTS_FOR_DRAW = 1;

    @Autowired
    public LeagueStandingService(LeagueStandingRepository leagueStandingRepository, MatchService matchService,
                                 LeagueRepository leagueRepository,
                                 CardService cardService) {
        this.leagueStandingRepository = leagueStandingRepository;
        this.matchService = matchService;
        this.leagueRepository = leagueRepository;
        this.cardService = cardService;
    }

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
                .collect(Collectors.toList());
    }

    public List<LeagueStandingDTO> getLeagueStandingsByLeagueIdOrderByGoalsFor(Long leagueId){
        Optional<List<LeagueStanding>> optionalLeagueStandingList = leagueStandingRepository.getLeagueStandingsByLeagueIdOrderByGoalsFor(leagueId);
        List<LeagueStanding> leagueStandingDTOList = optionalLeagueStandingList.orElse(Collections.emptyList());
        return leagueStandingDTOList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<LeagueStandingDTO> getLeagueStandingsByLeagueIdOrderByWins(Long leagueId){
        Optional<List<LeagueStanding>> optionalLeagueStandingList = leagueStandingRepository.getLeagueStandingsByLeagueIdOrderByWins(leagueId);
        List<LeagueStanding> leagueStandingList = optionalLeagueStandingList.orElse(Collections.emptyList());
        return leagueStandingList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<LeagueStandingDTO> getLeagueStandingsByLeagueIdOrderByLosses(Long leagueId){
        Optional<List<LeagueStanding>> optionalLeagueStandingList = leagueStandingRepository.getLeagueStandingsByLeagueIdOrderByLosses(leagueId);
        List<LeagueStanding> leagueStandingList = optionalLeagueStandingList.orElse(Collections.emptyList());
        return leagueStandingList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public LeagueStandingDTO convertToDTO(LeagueStanding leagueStanding){
        return StandingMapper.convertToDTO(leagueStanding, new LeagueStandingDTO());
    }
}
