package com.mp.footballtournamentsandleaguesmanager.DTO;

import lombok.Data;

@Data
public class PlayerCardsDTO {
    private Long playerId;
    private String firstName;
    private String lastName;
    private Long teamId;
    private String teamName;
    private Long yellowCards;
    private Long redCards;
}
