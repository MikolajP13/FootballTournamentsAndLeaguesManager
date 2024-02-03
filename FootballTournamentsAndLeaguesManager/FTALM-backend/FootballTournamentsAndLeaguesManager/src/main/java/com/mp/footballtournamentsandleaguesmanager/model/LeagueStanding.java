package com.mp.footballtournamentsandleaguesmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }
}
