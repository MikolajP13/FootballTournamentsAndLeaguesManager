package com.mp.footballtournamentsandleaguesmanager.DTO;

import com.mp.footballtournamentsandleaguesmanager.model.Card;
import lombok.Data;

@Data
public class CardDTO {
    private Long id;
    private Long matchId;
    private Long teamId;
    private String teamName;
    private Long playerId;
    private String playerFirstName;
    private String playerLastName;
    private int minute;
    private Card.Type cardType;
}
