package com.mp.footballtournamentsandleaguesmanager.DTO;

import com.mp.footballtournamentsandleaguesmanager.model.Tournament;
import com.mp.footballtournamentsandleaguesmanager.model.TournamentLeagueBase;

import java.util.Date;

public class TournamentDTO {
    private  Long id;
    private String name;
    private Date startDate;
    private Date endDate;
    private int numberOfTeams;
    private TournamentLeagueBase.Status status;
    private Tournament.TournamentType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String tournamentName) {
        this.name = tournamentName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getNumberOfTeams() {
        return numberOfTeams;
    }

    public void setNumberOfTeams(int numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }

    public TournamentLeagueBase.Status getStatus() {
        return status;
    }

    public void setStatus(TournamentLeagueBase.Status status) {
        this.status = status;
    }

    public Tournament.TournamentType getType() {
        return type;
    }

    public void setType(Tournament.TournamentType type) {
        this.type = type;
    }
}
