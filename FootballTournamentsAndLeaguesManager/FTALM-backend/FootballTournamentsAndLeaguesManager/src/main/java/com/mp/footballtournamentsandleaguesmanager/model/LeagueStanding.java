package com.mp.footballtournamentsandleaguesmanager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "LeagueStandings")
public class LeagueStanding extends Standing{
    @ManyToOne
    @JoinColumn(name = "league_id", nullable = false)
    private League league;

    public LeagueStanding() { }

    public LeagueStanding(Team team, int matches, int points, int goalsFor, int goalsAgainst, int wins,
                          int draws, int losses, League league) {
        super(team, matches, points, goalsFor, goalsAgainst, wins, draws, losses);
        this.league = league;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }
}
