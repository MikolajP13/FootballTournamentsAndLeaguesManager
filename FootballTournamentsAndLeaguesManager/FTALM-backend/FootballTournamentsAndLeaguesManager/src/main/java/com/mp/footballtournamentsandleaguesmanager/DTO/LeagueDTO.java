package com.mp.footballtournamentsandleaguesmanager.DTO;

import com.mp.footballtournamentsandleaguesmanager.model.League;
import com.mp.footballtournamentsandleaguesmanager.model.TournamentLeagueBase;
import lombok.Data;

import java.util.Date;
@Data
public class LeagueDTO {
    private Long id;
    private String name;
    private Date startDate;
    private Date endDate;
    private int numberOfTeams;
    private TournamentLeagueBase.Status status;
    private League.LeagueType type;
}
