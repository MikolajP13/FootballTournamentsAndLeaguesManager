package com.mp.footballtournamentsandleaguesmanager.DTO;

import com.mp.footballtournamentsandleaguesmanager.model.Player;

import java.util.Date;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Player.Foot getFoot() {
        return foot;
    }

    public void setFoot(Player.Foot foot) {
        this.foot = foot;
    }

    public Date getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(Date joinedDate) {
        this.joinedDate = joinedDate;
    }

    public Player.Position getPosition() {
        return position;
    }

    public void setPosition(Player.Position position) {
        this.position = position;
    }

    public Player.PositionDetail getPositionDetail() {
        return positionDetail;
    }

    public void setPositionDetail(Player.PositionDetail positionDetail) {
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
}
