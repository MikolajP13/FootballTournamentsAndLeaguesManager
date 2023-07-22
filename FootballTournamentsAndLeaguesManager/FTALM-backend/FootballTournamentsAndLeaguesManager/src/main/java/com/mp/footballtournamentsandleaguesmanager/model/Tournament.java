package com.mp.footballtournamentsandleaguesmanager.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.sql.Date;

@Entity
@Table(name = "Tournaments")
public class Tournament extends TournamentLeagueBase{

    public Tournament() {
    }

    public Tournament(Long id, User user, String name, Date startDate, int numberOfTeams, Status status) {
        super(id, user, name, startDate, numberOfTeams, status);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
