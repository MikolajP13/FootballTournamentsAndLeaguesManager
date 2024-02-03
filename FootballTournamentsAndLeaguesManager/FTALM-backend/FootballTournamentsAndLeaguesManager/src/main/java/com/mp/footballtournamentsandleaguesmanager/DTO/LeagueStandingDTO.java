package com.mp.footballtournamentsandleaguesmanager.DTO;

import lombok.Data;

@Data
public class LeagueStandingDTO extends StandingDTO{
    private Long leagueId;
    private String leagueName;
}
