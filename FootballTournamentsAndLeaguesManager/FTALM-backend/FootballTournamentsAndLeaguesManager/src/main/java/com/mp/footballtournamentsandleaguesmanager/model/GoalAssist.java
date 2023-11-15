package com.mp.footballtournamentsandleaguesmanager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "GoalsAssists")
public class GoalAssist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "match_id", nullable = false)
    private Match match;
    @ManyToOne
    @JoinColumn(name = "team_id", nullable = false)
    private Team team;
    @ManyToOne
    @JoinColumn(name = "scorerPlayer_id", nullable = false)
    private Player scorerPlayer;

    private Long assistPlayerId;
    private int minute;

    public GoalAssist() {
    }

    public GoalAssist(Long id, Match match, Team team, Player scorerPlayer, Long assistPlayerId, int minute) {
        this.id = id;
        this.match = match;
        this.team = team;
        this.scorerPlayer = scorerPlayer;
        this.assistPlayerId = assistPlayerId;
        this.minute = minute;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Player getScorerPlayer() {
        return scorerPlayer;
    }

    public void setScorerPlayer(Player scorerPlayer) {
        this.scorerPlayer = scorerPlayer;
    }

    public Long getAssistPlayerId() {
        return assistPlayerId;
    }

    public void setAssistPlayerId(Long assistPlayerId) {
        this.assistPlayerId = assistPlayerId;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

}
