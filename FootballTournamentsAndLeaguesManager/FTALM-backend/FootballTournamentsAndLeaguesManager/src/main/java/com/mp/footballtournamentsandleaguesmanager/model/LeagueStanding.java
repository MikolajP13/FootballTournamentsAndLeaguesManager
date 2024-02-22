package com.mp.footballtournamentsandleaguesmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@Entity
@Table(name = "LeagueStandings")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LeagueStanding extends Standing{
    @ManyToOne
    @JoinColumn(name = "league_id", nullable = false)
    private League league;
    private LeagueStandingType leagueStandingType;

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    public enum LeagueStandingType {
        NORMAL, CHAMPION, RELEGATION
    }
}
