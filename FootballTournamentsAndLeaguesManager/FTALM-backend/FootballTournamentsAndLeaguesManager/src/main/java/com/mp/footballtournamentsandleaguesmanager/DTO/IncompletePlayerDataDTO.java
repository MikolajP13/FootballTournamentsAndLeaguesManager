package com.mp.footballtournamentsandleaguesmanager.DTO;

import com.mp.footballtournamentsandleaguesmanager.model.Player;
import lombok.Data;

import java.util.Date;

@Data
public class IncompletePlayerDataDTO {
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private int heightInCm;
    private Player.Foot foot;
    private Player.Position position;
    private Player.PositionDetail positionDetail;
    private boolean isSuspended;
    private boolean isInjured;
    private boolean isTeamCaptain;
}
