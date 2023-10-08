package com.mp.footballtournamentsandleaguesmanager.DTO;

public class GoalAssistDTO {
    private Long id;
    private Long matchId;
    private Long teamId;
    private Long scorerPlayerId;
    private String scorerPlayerFirstName;
    private String scorerPlayerLastName;
    private Long assistPlayerId;
    private String assistPlayerFirstName;
    private String assistPlayerLastName;
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

    public Long getScorerPlayerId() {
        return scorerPlayerId;
    }

    public void setScorerPlayerId(Long scorerPlayerId) {
        this.scorerPlayerId = scorerPlayerId;
    }

    public String getScorerPlayerFirstName() {
        return scorerPlayerFirstName;
    }

    public void setScorerPlayerFirstName(String scorerPlayerFirstName) {
        this.scorerPlayerFirstName = scorerPlayerFirstName;
    }

    public String getScorerPlayerLastName() {
        return scorerPlayerLastName;
    }

    public void setScorerPlayerLastName(String scorerPlayerLastName) {
        this.scorerPlayerLastName = scorerPlayerLastName;
    }

    public Long getAssistPlayerId() {
        return assistPlayerId;
    }

    public void setAssistPlayerId(Long assistPlayerId) {
        this.assistPlayerId = assistPlayerId;
    }

    public String getAssistPlayerFirstName() {
        return assistPlayerFirstName;
    }

    public void setAssistPlayerFirstName(String assistPlayerFirstName) {
        this.assistPlayerFirstName = assistPlayerFirstName;
    }

    public String getAssistPlayerLastName() {
        return assistPlayerLastName;
    }

    public void setAssistPlayerLastName(String assistPlayerLastName) {
        this.assistPlayerLastName = assistPlayerLastName;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
}
