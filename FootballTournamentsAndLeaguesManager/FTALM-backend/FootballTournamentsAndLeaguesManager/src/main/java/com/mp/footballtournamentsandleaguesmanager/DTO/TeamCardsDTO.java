package com.mp.footballtournamentsandleaguesmanager.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TeamCardsDTO {
    private Long teamId;
    private String teamName;
    private Long yellowCards;
    private Long redCards;

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
