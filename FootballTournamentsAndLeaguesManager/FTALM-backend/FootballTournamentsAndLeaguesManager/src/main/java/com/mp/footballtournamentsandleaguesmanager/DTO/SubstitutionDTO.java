package com.mp.footballtournamentsandleaguesmanager.DTO;

public class SubstitutionDTO {
    private Long id;
    private Long matchId;
    private Long teamId;
    private Long enteringPlayerId;
    private String enteringPlayerFirstName;
    private String enteringPlayerLastName;
    private Long exitingPlayerId;
    private String exitingPlayerFirstName;
    private String exitingPlayerLastName;
    private int minute;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getEnteringPlayerId() {
        return enteringPlayerId;
    }

    public void setEnteringPlayerId(Long enteringPlayerId) {
        this.enteringPlayerId = enteringPlayerId;
    }

    public String getEnteringPlayerFirstName() {
        return enteringPlayerFirstName;
    }

    public void setEnteringPlayerFirstName(String enteringPlayerFirstName) {
        this.enteringPlayerFirstName = enteringPlayerFirstName;
    }

    public String getEnteringPlayerLastName() {
        return enteringPlayerLastName;
    }

    public void setEnteringPlayerLastName(String enteringPlayerLastName) {
        this.enteringPlayerLastName = enteringPlayerLastName;
    }

    public Long getExitingPlayerId() {
        return exitingPlayerId;
    }

    public void setExitingPlayerId(Long exitingPlayerId) {
        this.exitingPlayerId = exitingPlayerId;
    }

    public String getExitingPlayerFirstName() {
        return exitingPlayerFirstName;
    }

    public void setExitingPlayerFirstName(String exitingPlayerFirstName) {
        this.exitingPlayerFirstName = exitingPlayerFirstName;
    }

    public String getExitingPlayerLastName() {
        return exitingPlayerLastName;
    }

    public void setExitingPlayerLastName(String exitingPlayerLastName) {
        this.exitingPlayerLastName = exitingPlayerLastName;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
