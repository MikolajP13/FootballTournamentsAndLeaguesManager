package com.mp.footballtournamentsandleaguesmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Teams_match")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TeamsMatch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;
    @ManyToOne
    @JoinColumn(name = "home_team_id", nullable = false)
    private Team homeTeam;
    @ManyToOne
    @JoinColumn(name = "away_team_id", nullable = false)
    private Team awayTeam;
    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = true)
    private Tournament tournament;
    @ManyToOne
    @JoinColumn(name = "league_id", nullable = true)
    private League league;
}
