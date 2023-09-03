package com.mp.footballtournamentsandleaguesmanager.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Tournaments")
public class Tournament extends TournamentLeagueBase{

    @ManyToMany(
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    @JoinTable(
            name = "Tournaments_teams",
            joinColumns = @JoinColumn(name = "torunament_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private Set<Team> teams = new HashSet<>();

    public Tournament() {
    }

    public Tournament(Long id, User user, String name, Date startDate, Date endDate, int numberOfTeams, Status status) {
        super(id, user, name, startDate, endDate, numberOfTeams, status);
    }

    public Tournament(Long id, User user, String name, Date startDate, Date endDate, int numberOfTeams, Status status, Set<Team> teams) {
        super(id, user, name, startDate, endDate, numberOfTeams, status);
        this.teams = teams;
    }

    public void addTeamToTournament(Team team){
        this.teams.add(team);
        team.getTournaments().add(this);
    }

    public void removeTeamFromTournament(Team team){
        this.teams.remove(team);
        team.getTournaments().remove(this);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
