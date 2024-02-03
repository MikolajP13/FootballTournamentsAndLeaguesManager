package com.mp.footballtournamentsandleaguesmanager.DTO;

import lombok.Data;

import java.util.Date;

@Data
public class MatchDTO {
    private Long id;
    private Date date;
    private Long homeTeamId;
    private String homeTeamName;
    private Long awayTeamId;
    private String awayTeamName;
    private Integer homeTeamScore;
    private Integer awayTeamScore;
    private Long tournamentId;
    private Long leagueId;
    private int matchweek;
    private int round;
    private boolean isMatchProtocolCreated;
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MatchDTO{");
        sb.append("id=").append(id);
        sb.append(", date=").append(date);
        sb.append(", homeTeamId=").append(homeTeamId);
        sb.append(", homeTeamName='").append(homeTeamName).append('\'');
        sb.append(", awayTeamId=").append(awayTeamId);
        sb.append(", awayTeamName='").append(awayTeamName).append('\'');
        sb.append(", homeTeamScore=").append(homeTeamScore);
        sb.append(", awayTeamScore=").append(awayTeamScore);
        sb.append(", tournamentId=").append(tournamentId);
        sb.append(", leagueId=").append(leagueId);
        sb.append(", matchweek=").append(matchweek);
        sb.append(", round=").append(round);
        sb.append(", isMatchProtocolCreated=").append(isMatchProtocolCreated);
        sb.append('}');
        return sb.toString();
    }
}
