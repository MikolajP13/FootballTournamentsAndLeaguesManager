package com.mp.footballtournamentsandleaguesmanager.model;

import jakarta.persistence.*;

import java.util.Date;

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
    private Date dateOfBirth;
    private int age;
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

    public Player() { }

    public Player(Long id, Team team, String firstName, String lastName, Date dateOfBirth, int age,
                  int heightInCm, Foot foot, Date joinedDate, Position position, PositionDetail positionDetail,
                  int appearances, long minutes, int yellowCards, int secondYellowCards, int redCards,
                  int goalsConceded, int cleanSheets, int goals, int assists, boolean isSuspended,
                  boolean isInjured, boolean isTeamCaptain) {
        this.id = id;
        this.team = team;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.age = age;
        this.heightInCm = heightInCm;
        this.foot = foot;
        this.joinedDate = joinedDate;
        this.position = position;
        this.positionDetail = positionDetail;
        this.appearances = appearances;
        this.minutes = minutes;
        this.yellowCards = yellowCards;
        this.secondYellowCards = secondYellowCards;
        this.redCards = redCards;
        this.goalsConceded = goalsConceded;
        this.cleanSheets = cleanSheets;
        this.goals = goals;
        this.assists = assists;
        this.isSuspended = isSuspended;
        this.isInjured = isInjured;
        this.isTeamCaptain = isTeamCaptain;
    }

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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeightInCm() {
        return heightInCm;
    }

    public void setHeightInCm(int heightInCm) {
        this.heightInCm = heightInCm;
    }

    public Foot getFoot() {
        return foot;
    }

    public void setFoot(Foot foot) {
        this.foot = foot;
    }

    public Date getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(Date joinedDate) {
        this.joinedDate = joinedDate;
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

    public int getAppearances() {
        return appearances;
    }

    public void setAppearances(int appearances) {
        this.appearances = appearances;
    }

    public long getMinutes() {
        return minutes;
    }

    public void setMinutes(long minutes) {
        this.minutes = minutes;
    }

    public int getYellowCards() {
        return yellowCards;
    }

    public void setYellowCards(int yellowCards) {
        this.yellowCards = yellowCards;
    }

    public int getSecondYellowCards() {
        return secondYellowCards;
    }

    public void setSecondYellowCards(int secondYellowCards) {
        this.secondYellowCards = secondYellowCards;
    }

    public int getRedCards() {
        return redCards;
    }

    public void setRedCards(int redCards) {
        this.redCards = redCards;
    }

    public int getGoalsConceded() {
        return goalsConceded;
    }

    public void setGoalsConceded(int goalsConceded) {
        this.goalsConceded = goalsConceded;
    }

    public int getCleanSheets() {
        return cleanSheets;
    }

    public void setCleanSheets(int cleanSheets) {
        this.cleanSheets = cleanSheets;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public void setSuspended(boolean suspended) {
        isSuspended = suspended;
    }

    public boolean isInjured() {
        return isInjured;
    }

    public void setInjured(boolean injured) {
        isInjured = injured;
    }

    public boolean isTeamCaptain() {
        return isTeamCaptain;
    }

    public void setTeamCaptain(boolean teamCaptain) {
        isTeamCaptain = teamCaptain;
    }

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
