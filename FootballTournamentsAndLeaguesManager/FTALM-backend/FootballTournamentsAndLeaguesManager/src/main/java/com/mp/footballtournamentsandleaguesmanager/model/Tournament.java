package com.mp.footballtournamentsandleaguesmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Tournaments")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Tournament extends TournamentLeagueBase{

    @ManyToMany(
            cascade = {CascadeType.MERGE, CascadeType.PERSIST}
    )
    @JoinTable(
            name = "Tournaments_teams",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private Set<Team> teams = new HashSet<>();
    private TournamentType type;

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

    public enum TournamentType {
        SINGLE_ELIMINATION("Single Elimination"),
        GROUP_AND_KNOCKOUT("Group and Knockout"),
        DOUBLE_ELIMINATION("Double Elimination");

        TournamentType(String s) {
        }
    }
}
