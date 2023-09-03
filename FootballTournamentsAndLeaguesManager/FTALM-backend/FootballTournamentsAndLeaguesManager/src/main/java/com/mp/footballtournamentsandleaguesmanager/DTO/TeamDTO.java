package com.mp.footballtournamentsandleaguesmanager.DTO;

import java.util.Date;

public class TeamDTO {
    private Long id;
    private String name;
    private Long captainId;
    private Date established;
    private boolean isInLeague;
    private boolean isInTournament;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCaptainId() {
        return captainId;
    }

    public void setCaptainId(Long captainId) {
        this.captainId = captainId;
    }

    public Date getEstablished() {
        return established;
    }

    public void setEstablished(Date established) {
        this.established = established;
    }

    public boolean getIsInLeague() {
        return isInLeague;
    }

    public void setIsInLeague(boolean isInLeague) {
        this.isInLeague = isInLeague;
    }

    public boolean getIsInTournament() {
        return isInTournament;
    }

    public void setIsInTournament(boolean isInTournament) {
        this.isInTournament = isInTournament;
    }
}
