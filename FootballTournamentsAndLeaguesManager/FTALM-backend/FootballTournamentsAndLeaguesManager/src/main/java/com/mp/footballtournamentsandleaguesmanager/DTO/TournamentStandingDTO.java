package com.mp.footballtournamentsandleaguesmanager.DTO;

import lombok.Data;

@Data
public class TournamentStandingDTO extends StandingDTO {
    private Long tournamentId;
    private String tournamentName;
    private int groupId; // 1 - A, 2 - B, 3 - C, ...
}
