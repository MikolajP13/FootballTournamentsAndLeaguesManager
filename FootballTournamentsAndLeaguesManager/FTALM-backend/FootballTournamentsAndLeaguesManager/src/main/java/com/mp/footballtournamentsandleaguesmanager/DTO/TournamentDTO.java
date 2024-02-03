package com.mp.footballtournamentsandleaguesmanager.DTO;

import com.mp.footballtournamentsandleaguesmanager.model.Tournament;
import com.mp.footballtournamentsandleaguesmanager.model.TournamentLeagueBase;
import lombok.Data;

import java.util.Date;
@Data
public class TournamentDTO {
    private  Long id;
    private String name;
    private Date startDate;
    private Date endDate;
    private int numberOfTeams;
    private TournamentLeagueBase.Status status;
    private Tournament.TournamentType type;
}
