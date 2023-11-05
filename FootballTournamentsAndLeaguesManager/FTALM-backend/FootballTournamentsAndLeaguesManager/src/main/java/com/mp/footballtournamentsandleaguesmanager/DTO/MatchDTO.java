package com.mp.footballtournamentsandleaguesmanager.DTO;

import java.util.Date;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(Long homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public void setHomeTeamName(String homeTeamName) {
        this.homeTeamName = homeTeamName;
    }

    public Long getAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(Long awayTeamId) {
        this.awayTeamId = awayTeamId;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public void setAwayTeamName(String awayTeamName) {
        this.awayTeamName = awayTeamName;
    }

    public Integer getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(Integer homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public Integer getAwayTeamScore() {
        return awayTeamScore;
    }

    public void setAwayTeamScore(Integer awayTeamScore) {
        this.awayTeamScore = awayTeamScore;
    }

    public Long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
    }

    public Long getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(Long leagueId) {
        this.leagueId = leagueId;
    }

    public int getMatchweek() {
        return matchweek;
    }

    public void setMatchweek(int matchweek) {
        this.matchweek = matchweek;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public boolean isMatchProtocolCreated() {
        return isMatchProtocolCreated;
    }

    public void setMatchProtocolCreated(boolean matchProtocolCreated) {
        isMatchProtocolCreated = matchProtocolCreated;
    }

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
