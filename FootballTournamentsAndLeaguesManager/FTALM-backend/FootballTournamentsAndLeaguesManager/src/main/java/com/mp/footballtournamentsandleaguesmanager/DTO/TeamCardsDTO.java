package com.mp.footballtournamentsandleaguesmanager.DTO;

public class TeamCardsDTO {
    private Long teamId;
    private String teamName;
    private Long yellowCards;
    private Long redCards;

    public TeamCardsDTO(Long teamId, String teamName, Long yellowCards, Long redCards) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.yellowCards = yellowCards;
        this.redCards = redCards;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public Long getYellowCards() {
        return yellowCards;
    }

    public void setYellowCards(Long yellowCards) {
        this.yellowCards = yellowCards;
    }

    public Long getRedCards() {
        return redCards;
    }

    public void setRedCards(Long redCards) {
        this.redCards = redCards;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TeamCardsDTO{");
        sb.append("teamId=").append(teamId);
        sb.append(", teamName='").append(teamName).append('\'');
        sb.append(", yellowCards=").append(yellowCards);
        sb.append(", redCards=").append(redCards);
        sb.append('}');
        return sb.toString();
    }
}
