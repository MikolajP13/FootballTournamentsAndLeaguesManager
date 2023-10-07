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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_team_id", nullable = false)
    private Team homeTeam;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "away_team_id", nullable = false)
    private Team awayTeam;
    private int homeTeamScore;
    private int awayTeamScore;
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

    public Match(Long id, Date date, Team homeTeam, Team awayTeam, int homeTeamScore, int awayTeamScore,
                 Tournament tournament, League league, int matchweek, int round, boolean isMatchProtocolCreated) {
        this.id = id;
        this.date = date;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
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

    public Team getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(Team homeTeam) {
        this.homeTeam = homeTeam;
    }

    public Team getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(Team awayTeam) {
        this.awayTeam = awayTeam;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public void setHomeTeamScore(int homeTeamScore) {
        this.homeTeamScore = homeTeamScore;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }

    public void setAwayTeamScore(int awayTeamScore) {
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
        sb.append(", homeTeam=").append(homeTeam);
        sb.append(", awayTeam=").append(awayTeam);
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
