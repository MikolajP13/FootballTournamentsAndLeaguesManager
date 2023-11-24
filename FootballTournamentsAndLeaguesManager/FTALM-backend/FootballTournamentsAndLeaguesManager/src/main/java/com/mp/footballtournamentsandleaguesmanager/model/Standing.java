package com.mp.footballtournamentsandleaguesmanager.model;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

//TODO: code refactoring for League and TournamentStanding!
public class Standing {
    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
    private int matches;
    private int points;
    private int goalsFor;
    private int goalsAgainst;
    private int wins;
    private int draws;
    private int losses;

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public int getMatches() {
        return matches;
    }

    public void setMatches(int matches) {
        this.matches = matches;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getGoalsFor() {
        return goalsFor;
    }

    public void setGoalsFor(int goalsFor) {
        this.goalsFor = goalsFor;
    }

    public int getGoalsAgainst() {
        return goalsAgainst;
    }

    public void setGoalsAgainst(int goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }
}
