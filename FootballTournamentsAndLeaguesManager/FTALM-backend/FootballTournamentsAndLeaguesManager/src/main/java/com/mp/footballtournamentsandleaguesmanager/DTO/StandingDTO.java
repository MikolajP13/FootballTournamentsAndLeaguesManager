package com.mp.footballtournamentsandleaguesmanager.DTO;

import lombok.Data;

import java.util.List;

@Data
public class StandingDTO {
    private Long id;
    private Long teamId;
    private String teamName;
    private int matches;
    private int points;
    private int goalsFor;
    private int goalsAgainst;
    private int wins;
    private int draws;
    private int losses;
    private List<String> teamForm;
}
