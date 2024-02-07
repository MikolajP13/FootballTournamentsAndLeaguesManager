package com.mp.footballtournamentsandleaguesmanager.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
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
