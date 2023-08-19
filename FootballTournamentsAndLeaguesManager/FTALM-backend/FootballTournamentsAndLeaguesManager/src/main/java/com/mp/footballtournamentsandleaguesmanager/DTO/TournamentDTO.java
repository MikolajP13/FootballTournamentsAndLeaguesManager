package com.mp.footballtournamentsandleaguesmanager.DTO;

import com.mp.footballtournamentsandleaguesmanager.model.TournamentLeagueBase;

import java.util.Date;

public class TournamentDTO {
    private  Long id;
    private String tournamentName;
    private Date startDate;
    private int numberOfTeams;
    private TournamentLeagueBase.Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
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
}
