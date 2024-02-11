package com.mp.footballtournamentsandleaguesmanager.DTO;

import lombok.Data;

@Data
public class LeagueTeamStatisticsDTO {
    private Long id;
    private Long teamId;
    private String teamName;
    private int rank;
    private int goals;
    private int wins;
    private int losses;
}
