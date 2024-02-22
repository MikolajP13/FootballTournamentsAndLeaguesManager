package com.mp.footballtournamentsandleaguesmanager.DTO;

import com.mp.footballtournamentsandleaguesmanager.model.LeagueStanding;
import lombok.Data;

@Data
public class LeagueStandingDTO extends StandingDTO{
    private Long leagueId;
    private String leagueName;
    private LeagueStanding.LeagueStandingType leagueStandingType;
}
