package com.mp.footballtournamentsandleaguesmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "Players")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private int heightInCm;
    private Foot foot;
    private Date joinedDate;
    private Position position;
    private PositionDetail positionDetail;
    private int appearances;
    private long minutes;
    private int yellowCards;
    private int secondYellowCards;
    private int redCards;
    // fields for goalkeepers
    private int goalsConceded;
    private int cleanSheets;
    // fields for other positions
    private int goals;
    private int assists;
    private boolean isSuspended;
    private boolean isInjured;
    private boolean isTeamCaptain;
    public enum Foot {
        RIGHT("right"), LEFT("left"), BOTH("both");

        Foot(String s) {
        }
    }
    public enum Position {
        GOALKEEPER, DEFENDER, MIDFIELDER, FORWARD
    }
    public enum PositionDetail {
        LF("Left Forward"), CF("Centre Forward"), RF("Right Forward"),
        LW("Left Wing"), RW("Right Wing"), CAM("Centre Attacking Midfielder"),
        LM("Left Midfielder"), CM("Centre Midfielder"), RM("Right Midfielder"),
        CDM("Centre Defensive Midfielder"),
        LB("Left Back"), LCB("Left Centre Back"), CB("Centre Back"), RCB("Right Centre Back"), RB("Right Back"),
        GK("Goalkeeper");
        PositionDetail(String s) {
        }
    }
}
