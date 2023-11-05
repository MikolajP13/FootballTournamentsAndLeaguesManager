package com.mp.footballtournamentsandleaguesmanager.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date date;
    private Long homeTeamId;
    private Long awayTeamId;
    private Integer homeTeamScore;
    private Integer awayTeamScore;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id", nullable = true)
    private Tournament tournament;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "league_id", nullable = true)
    private League league;
    private int matchweek;
    private int round;
    private boolean isMatchProtocolCreated;

    public Match() {
    }

    public Match(Long id, Date date, Long homeTeamId, Long awayTeamId, Integer homeTeamScore, Integer awayTeamScore,
                 Tournament tournament, League league, int matchweek, int round, boolean isMatchProtocolCreated) {
        this.id = id;
        this.date = date;
        this.homeTeamId = homeTeamId;
        this.awayTeamId = awayTeamId;
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
        this.tournament = tournament;
        this.league = league;
        this.matchweek = matchweek;
        this.round = round;
        this.isMatchProtocolCreated = isMatchProtocolCreated;
    }

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

    public Long getAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(Long awayTeamId) {
        this.awayTeamId = awayTeamId;
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

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
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
        final StringBuilder sb = new StringBuilder("Match{");
        sb.append("id=").append(id);
        sb.append(", date=").append(date);
        sb.append(", homeTeam=").append(homeTeamId);
        sb.append(", awayTeam=").append(awayTeamId);
        sb.append(", homeTeamScore=").append(homeTeamScore);
        sb.append(", awayTeamScore=").append(awayTeamScore);
        sb.append(", tournament=").append(tournament);
        sb.append(", league=").append(league);
        sb.append(", matchweek=").append(matchweek);
        sb.append(", round=").append(round);
        sb.append(", isMatchProtocolCreated=").append(isMatchProtocolCreated);
        sb.append('}');
        return sb.toString();
    }
}
