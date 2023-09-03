package com.mp.footballtournamentsandleaguesmanager.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Leagues")
public class League extends TournamentLeagueBase{
    //TODO expand data
    @ManyToMany(
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    @JoinTable(
            name = "Leagues_Teams",
            joinColumns = @JoinColumn(name = "league_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private Set<Team> teams = new HashSet<>();

    public League() {
    }
    public League(Long id, User user, String name, Date startDate, Date endDate, int numberOfTeams, Status status, Set<Team> teams) {
        super(id, user, name, startDate, endDate, numberOfTeams, status);
        this.teams = teams;
    }
    public League(Long id, User user, String name, Date startDate, Date endDate, int numberOfTeams, Status status) {
        super(id, user, name, startDate, endDate, numberOfTeams, status);
    }
    public Set<Team> getTeams() {
        return teams;
    }
    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }
    public void addTeamToLeague(Team team){
        this.teams.add(team);
        team.getLeagues().add(this);
    }
    public void removeTeamFromLeague(Team team){
        this.teams.remove(team);
        team.getLeagues().remove(this);
    }
    @Override
    public String toString() {
        return super.toString();
    }
}
