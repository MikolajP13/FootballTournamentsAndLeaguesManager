package com.mp.footballtournamentsandleaguesmanager.DTO;

import lombok.Data;

@Data
public class TeamsMatchDTO {
    private Long matchId;
    private Long homeTeamId;
    private Long awayTeamId;
    private Long tournamentId;
    private Long leagueId;
}
