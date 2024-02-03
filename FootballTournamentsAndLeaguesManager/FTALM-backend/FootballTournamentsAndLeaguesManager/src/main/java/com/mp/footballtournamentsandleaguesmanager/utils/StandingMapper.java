package com.mp.footballtournamentsandleaguesmanager.utils;

import com.mp.footballtournamentsandleaguesmanager.DTO.LeagueStandingDTO;
import com.mp.footballtournamentsandleaguesmanager.DTO.StandingDTO;
import com.mp.footballtournamentsandleaguesmanager.DTO.TournamentStandingDTO;
import com.mp.footballtournamentsandleaguesmanager.model.LeagueStanding;
import com.mp.footballtournamentsandleaguesmanager.model.Standing;
import com.mp.footballtournamentsandleaguesmanager.model.TournamentStanding;

public class StandingMapper {
    private static final int POINTS_FOR_WIN = 3;
    private static final int POINTS_FOR_DRAW = 1;

    public static <T extends Standing, D extends StandingDTO> D convertToDTO(T standing, D dto) {
        dto.setId(standing.getId());
        dto.setTeamId(standing.getTeam().getId());
        dto.setTeamName(standing.getTeam().getName());
        dto.setMatches(standing.getMatches());
        dto.setWins(standing.getWins());
        dto.setDraws(standing.getDraws());
        dto.setLosses(standing.getLosses());
        dto.setGoalsFor(standing.getGoalsFor());
        dto.setGoalsAgainst(standing.getGoalsAgainst());
        dto.setPoints(standing.getPoints());

        if (dto instanceof TournamentStandingDTO tournamentDTO) {
            tournamentDTO.setTournamentId(((TournamentStanding) standing).getTournament().getId());
            tournamentDTO.setTournamentName(((TournamentStanding) standing).getTournament().getName());
            tournamentDTO.setGroupId(((TournamentStanding) standing).getGroupId());
        } else if (dto instanceof LeagueStandingDTO leagueDTO) {
            leagueDTO.setLeagueId(((LeagueStanding) standing).getLeague().getId());
            leagueDTO.setLeagueName(((LeagueStanding) standing).getLeague().getName());
        }

        return dto;
    }

    public static <T extends Standing, D extends StandingDTO> T updateStandingAndReturn(T standing, D standingDTO) {
        standing.setMatches(standingDTO.getMatches() + 1);
        standing.setGoalsFor(standing.getGoalsFor() + standingDTO.getGoalsFor());
        standing.setGoalsAgainst(standing.getGoalsAgainst() + standingDTO.getGoalsAgainst());

        if (standingDTO.getWins() == 1) {
            standing.setWins(standing.getWins() + 1);
            standing.setPoints(standing.getPoints() + POINTS_FOR_WIN);
        } else if (standingDTO.getDraws() == 1){
            standing.setDraws(standing.getDraws() + 1);
            standing.setPoints(standing.getPoints() + POINTS_FOR_DRAW);
        } else {
            standing.setLosses(standing.getLosses() + 1);
        }

        return standing;
    }
}
