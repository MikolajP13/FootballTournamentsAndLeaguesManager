package com.mp.footballtournamentsandleaguesmanager.model;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "Leagues")
public class League extends TournamentLeagueBase{
    //TODO expand data


    public League() {
    }

    public League(Long id, User user, String name, Date startDate, int numberOfTeams, Status status) {
        super(id, user, name, startDate, numberOfTeams, status);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
