package com.mp.footballtournamentsandleaguesmanager.DTO;

import lombok.Data;

@Data
public class MatchEventDTO {
    private Long firstPlayerId;
    private Long secondPlayerId;
    private EventType event;

    public enum EventType {
        GOAL, YELLOW_CARD, SECOND_YELLOW_CARD, RED_CARD
    }
}
