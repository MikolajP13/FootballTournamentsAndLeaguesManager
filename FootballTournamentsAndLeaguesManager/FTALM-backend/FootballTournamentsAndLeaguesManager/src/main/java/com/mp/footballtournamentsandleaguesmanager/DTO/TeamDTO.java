package com.mp.footballtournamentsandleaguesmanager.DTO;

import lombok.Data;
import java.util.Date;
@Data
public class TeamDTO {
    private Long id;
    private String name;
    private Long captainId;
    private Date established;
    private boolean isInLeague;
    private boolean isInTournament;
}
