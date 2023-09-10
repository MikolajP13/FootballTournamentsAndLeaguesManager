package com.mp.footballtournamentsandleaguesmanager.DTO;

import com.mp.footballtournamentsandleaguesmanager.model.TournamentLeagueBase;

import java.util.Date;

public class LeagueDTO {
    private Long id;
    private String leagueName;
    private Date startDate;
    private Date endDate;
    private int numberOfTeams;
    private TournamentLeagueBase.Status status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
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
}
