package com.mp.footballtournamentsandleaguesmanager.service;

import com.mp.footballtournamentsandleaguesmanager.DTO.TournamentStandingDTO;
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
