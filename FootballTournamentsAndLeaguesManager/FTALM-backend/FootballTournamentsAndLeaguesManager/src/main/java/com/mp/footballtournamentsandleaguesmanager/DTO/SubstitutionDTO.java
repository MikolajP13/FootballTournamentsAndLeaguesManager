package com.mp.footballtournamentsandleaguesmanager.DTO;

import lombok.Data;

@Data
public class SubstitutionDTO {
    private Long id;
    private Long matchId;
    private Long teamId;
    private String teamName;
    private Long enteringPlayerId;
    private String enteringPlayerFirstName;
    private String enteringPlayerLastName;
    private Long exitingPlayerId;
    private String exitingPlayerFirstName;
    private String exitingPlayerLastName;
    private int minute;
}
