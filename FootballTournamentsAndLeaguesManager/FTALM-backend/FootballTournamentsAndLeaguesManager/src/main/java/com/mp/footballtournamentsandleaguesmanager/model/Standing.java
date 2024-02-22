package com.mp.footballtournamentsandleaguesmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Standing {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
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

    public Standing(Team team, int matches, int points, int goalsFor, int goalsAgainst, int wins, int draws, int losses) {
        this.team = team;
        this.matches = matches;
        this.points = points;
        this.goalsFor = goalsFor;
        this.goalsAgainst = goalsAgainst;
        this.wins = wins;
        this.draws = draws;
        this.losses = losses;
    }
}
