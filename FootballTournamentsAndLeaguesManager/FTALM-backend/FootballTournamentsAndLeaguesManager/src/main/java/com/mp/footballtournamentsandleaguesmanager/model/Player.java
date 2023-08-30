package com.mp.footballtournamentsandleaguesmanager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Players")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
    private String firstName;
    private String lastName;
    private Position position;
    private PositionDetail positionDetail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public PositionDetail getPositionDetail() {
        return positionDetail;
    }

    public void setPositionDetail(PositionDetail positionDetail) {
        this.positionDetail = positionDetail;
    }

    public Player() {
    }
    public Player(Long id, Team team, String firstName, String lastName, Position position, PositionDetail positionDetail) {
        this.id = id;
        this.team = team;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.positionDetail = positionDetail;
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
