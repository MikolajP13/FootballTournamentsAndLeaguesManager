package com.mp.footballtournamentsandleaguesmanager.DTO;

import lombok.Data;

@Data
public class PlayerGoalsDTO {
    private Long playerId;
    private String firstName;
    private String lastName;
    private Long teamId;
    private String teamName;
    private Long goals;

    public PlayerGoalsDTO(Long playerId, String firstName, String lastName, Long teamId, String teamName, Long goals) {
        this.playerId = playerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.teamId = teamId;
        this.teamName = teamName;
        this.goals = goals;
    }
}
