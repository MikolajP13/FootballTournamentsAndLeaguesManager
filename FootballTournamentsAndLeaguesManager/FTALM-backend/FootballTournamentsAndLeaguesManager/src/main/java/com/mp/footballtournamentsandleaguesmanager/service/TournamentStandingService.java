package com.mp.footballtournamentsandleaguesmanager.service;

import com.mp.footballtournamentsandleaguesmanager.DTO.TournamentStandingDTO;
import com.mp.footballtournamentsandleaguesmanager.businessLogic.TeamComparator;
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
        tournamentStandingToUpdate.setMatches(tournamentStandingToUpdate.getMatches() + 1);
        tournamentStandingToUpdate.setGoalsFor(tournamentStandingToUpdate.getGoalsFor() + tournamentStandingDTO.getGoalsFor());
        tournamentStandingToUpdate.setGoalsAgainst(tournamentStandingToUpdate.getGoalsAgainst() + tournamentStandingDTO.getGoalsAgainst());

        if (tournamentStandingDTO.getWins() == 1) {
            tournamentStandingToUpdate.setWins(tournamentStandingToUpdate.getWins() + 1);
            tournamentStandingToUpdate.setPoints(tournamentStandingToUpdate.getPoints() + POINTS_FOR_WIN);
        } else if (tournamentStandingDTO.getDraws() == 1){
            tournamentStandingToUpdate.setDraws(tournamentStandingToUpdate.getDraws() + 1);
            tournamentStandingToUpdate.setPoints(tournamentStandingToUpdate.getPoints() + POINTS_FOR_DRAW);
        } else {
            tournamentStandingToUpdate.setLosses(tournamentStandingToUpdate.getLosses() + 1);
        }
        //TODO: code refactoring code due to duplication [LeagueStanding] !
        return  tournamentStandingRepository.save(tournamentStandingToUpdate);
    }

    //TODO: code refactoring code due to duplication [LeagueStanding] !
    public TournamentStandingDTO convertToDTO(TournamentStanding tournamentStanding){
        TournamentStandingDTO dto = new TournamentStandingDTO();
        dto.setId(tournamentStanding.getId());
        dto.setTournamentId(tournamentStanding.getTournament().getId());
        dto.setTournamentName(tournamentStanding.getTournament().getName());
        dto.setGroupId(tournamentStanding.getGroupId());
        dto.setTeamId(tournamentStanding.getTeam().getId());
        dto.setTeamName(tournamentStanding.getTeam().getName());
        dto.setMatches(tournamentStanding.getMatches());
        dto.setPoints(tournamentStanding.getPoints());
        dto.setGoalsFor(tournamentStanding.getGoalsFor());
        dto.setGoalsAgainst(tournamentStanding.getGoalsAgainst());
        dto.setWins(tournamentStanding.getWins());
        dto.setDraws(tournamentStanding.getDraws());
        dto.setLosses(tournamentStanding.getLosses());

        return dto;
    }
}
