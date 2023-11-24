package com.mp.footballtournamentsandleaguesmanager.service;

import com.mp.footballtournamentsandleaguesmanager.DTO.TournamentStandingDTO;
import com.mp.footballtournamentsandleaguesmanager.business.TeamComparator;
import com.mp.footballtournamentsandleaguesmanager.model.TournamentStanding;
import com.mp.footballtournamentsandleaguesmanager.repository.TournamentStandingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TournamentStandingService {
    private final TournamentStandingRepository tournamentStandingRepository;
    private final MatchService matchService;
    private final CardService cardService;
    private static final int POINTS_FOR_WIN = 3;
    private static final int POINTS_FOR_DRAW = 1;

    @Autowired
    public TournamentStandingService(TournamentStandingRepository tournamentStandingRepository, MatchService matchService, CardService cardService) {
        this.tournamentStandingRepository = tournamentStandingRepository;
        this.matchService = matchService;
        this.cardService = cardService;
    }

    public List<TournamentStanding> addTournamentStanding(List<TournamentStanding> tournamentStandings){
        return tournamentStandingRepository.saveAll(tournamentStandings);
    }

    public List<TournamentStandingDTO> getTournamentStandingByTournamentIdAndGroupId(Long tournamentId, int groupId){
        Optional<List<TournamentStanding>> optionalTournamentStandingList = tournamentStandingRepository.getTournamentStandingByTournamentIdAndGroupId(tournamentId, groupId);
        List<TournamentStanding> tournamentStandingList = optionalTournamentStandingList.orElse(Collections.emptyList());

        if(!tournamentStandingList.isEmpty()){
            if(tournamentStandingList.stream().anyMatch(t -> t.getMatches() > 0))
                tournamentStandingList.sort(Comparator.comparing(t -> t.getTeam().getName()));
            else
                tournamentStandingList.sort(new TeamComparator<>(tournamentId, matchService, cardService));
        }

        return tournamentStandingList.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public TournamentStanding updateTournamentStanding(Long tournamentId, int groupId, Long teamId, TournamentStandingDTO tournamentStandingDTO){
        TournamentStanding tournamentStandingToUpdate = this.tournamentStandingRepository.findByTournamentIdAndGroupIdAndTeamId(tournamentId, groupId, teamId).orElseThrow();

        return  tournamentStandingRepository.save(StandingMapper.updateStandingAndReturn(tournamentStandingToUpdate, tournamentStandingDTO));
    }

    public TournamentStandingDTO convertToDTO(TournamentStanding tournamentStanding){
        return StandingMapper.convertToDTO(tournamentStanding, new TournamentStandingDTO());
    }
}
