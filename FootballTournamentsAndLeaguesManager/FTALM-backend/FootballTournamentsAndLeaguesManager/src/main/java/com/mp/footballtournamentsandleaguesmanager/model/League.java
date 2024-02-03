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
@Table(name = "Leagues")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    private LeagueType type;

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

    public enum LeagueType {
        STANDARD_MODE("Standard Mode"),
        SPLIT_MODE("Split Mode");

        LeagueType(String s) {
        }
    }
}
