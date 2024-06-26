package com.mp.footballtournamentsandleaguesmanager.DTO;

import com.mp.footballtournamentsandleaguesmanager.model.Player;
import lombok.Data;

import java.util.Date;

@Data
public class PlayerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private int age;
    private int heightInCm;
    private Player.Foot foot;
    private Date joinedDate;
    private Player.Position position;
    private Player.PositionDetail positionDetail;
    private int appearances;
    private long minutes;
    private int yellowCards;
    private int secondYellowCards;
    private int redCards;
    private int goalsConceded;
    private int cleanSheets;
    private int goals;
    private int assists;
    private boolean isSuspended;
    private boolean isInjured;
    private boolean isTeamCaptain;
}
