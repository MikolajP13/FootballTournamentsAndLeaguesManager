package com.mp.footballtournamentsandleaguesmanager.DTO;

import lombok.Data;

@Data
public class PlayerCardsDTO {
    private Long playerId;
    private int rank;
    private String firstName;
    private String lastName;
    private Long teamId;
    private String teamName;
    private Long yellowCards;
    private Long redCards;

    public PlayerCardsDTO(Long playerId, String firstName, String lastName, Long teamId, String teamName, Long yellowCards, Long redCards) {
        this.playerId = playerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.teamId = teamId;
        this.teamName = teamName;
        this.yellowCards = yellowCards;
        this.redCards = redCards;
    }
}
