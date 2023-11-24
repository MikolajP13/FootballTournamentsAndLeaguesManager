package com.mp.footballtournamentsandleaguesmanager.DTO;

public class LeagueStandingDTO extends StandingDTO{
    private Long leagueId;
    private String leagueName;

    public Long getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(Long leagueId) {
        this.leagueId = leagueId;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
    }
}
