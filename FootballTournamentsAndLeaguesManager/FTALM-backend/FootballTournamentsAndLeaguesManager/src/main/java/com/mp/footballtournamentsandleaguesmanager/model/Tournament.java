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
    private TournamentType type;

    public Tournament() {
    }

    public Tournament(Long id, User user, String name, Date startDate, Date endDate, int numberOfTeams, Status status) {
        super(id, user, name, startDate, endDate, numberOfTeams, status);
    }

    public Tournament(Long id, User user, String name, Date startDate, Date endDate, int numberOfTeams, Status status, Set<Team> teams, TournamentType type) {
        super(id, user, name, startDate, endDate, numberOfTeams, status);
        this.teams = teams;
        this.type = type;
    }

    public void addTeamToTournament(Team team){
        this.teams.add(team);
        team.getTournaments().add(this);
    }

    public void removeTeamFromTournament(Team team){
        this.teams.remove(team);
        team.getTournaments().remove(this);
    }

    public TournamentType getType() {
        return type;
    }

    public void setType(TournamentType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public enum TournamentType {
        SINGLE_ELIMINATION("Single Elimination"),
        GROUP_AND_KNOCKOUT("Group and Knockout"),
        DOUBLE_ELIMINATION("Double Elimination");

        TournamentType(String s) {
        }
    }
}
