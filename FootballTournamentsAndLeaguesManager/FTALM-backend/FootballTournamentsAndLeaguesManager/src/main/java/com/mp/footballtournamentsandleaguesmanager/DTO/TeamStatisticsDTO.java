package com.mp.footballtournamentsandleaguesmanager.DTO;

import lombok.Data;

@Data
public class TeamStatisticsDTO extends StandingDTO{
    private Long competitionId;
    private String competitionName;
    private String competitionType;
    private int leagueRank;
    private String tournamentRound;
}
