package com.mp.footballtournamentsandleaguesmanager.DTO;

import lombok.Data;

@Data
public class PlayerAssistsDTO {
    private Long playerId;
    private int rank;
    private String firstName;
    private String lastName;
    private Long teamId;
    private String teamName;
    private Long assists;

    public PlayerAssistsDTO(Long playerId, String firstName, String lastName, Long teamId, String teamName, Long assists) {
        this.playerId = playerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.teamId = teamId;
        this.teamName = teamName;
        this.assists = assists;
    }
}
