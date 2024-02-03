package com.mp.footballtournamentsandleaguesmanager.DTO;

import lombok.Data;

@Data
public class GoalAssistDTO {
    private Long id;
    private Long matchId;
    private Long teamId;
    private String teamName;
    private Long scorerPlayerId;
    private String scorerPlayerFirstName;
    private String scorerPlayerLastName;
    private Long assistPlayerId;
    private String assistPlayerFirstName;
    private String assistPlayerLastName;
    private int minute;
}
