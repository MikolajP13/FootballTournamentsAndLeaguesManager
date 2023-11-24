package com.mp.footballtournamentsandleaguesmanager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "TournamentStandings")
public class TournamentStanding extends Standing{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;
    private int groupId; // 1 - A, 2 - B, 3 - C, ...
    private int matches;
    private int points;
    private int goalsFor;
    private int goalsAgainst;
    private int wins;
    private int draws;
    private int losses;

    public TournamentStanding() {
    }

    public TournamentStanding(Long id, Team team, Tournament tournament, int groupId, int matches, int points,
                              int goalsFor, int goalsAgainst, int wins, int draws, int losses) {
        this.id = id;
        this.team = team;
        this.tournament = tournament;
        this.groupId = groupId;
        this.matches = matches;
        this.points = points;
        this.goalsFor = goalsFor;
        this.goalsAgainst = goalsAgainst;
        this.wins = wins;
        this.draws = draws;
        this.losses = losses;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
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
