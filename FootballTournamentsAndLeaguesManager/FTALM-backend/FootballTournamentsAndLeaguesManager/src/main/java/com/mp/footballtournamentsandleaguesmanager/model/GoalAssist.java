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
    @ManyToOne
    @JoinColumn(name = "assistPlayer_id", nullable = false)
    private Player assistPlayer;
    private int minute;

    public GoalAssist() {
    }

    public GoalAssist(Long id, Match match, Team team, Player scorerPlayer, Player assistPlayer, int minute) {
        this.id = id;
        this.match = match;
        this.team = team;
        this.scorerPlayer = scorerPlayer;
        this.assistPlayer = assistPlayer;
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

    public Player getAssistPlayer() {
        return assistPlayer;
    }

    public void setAssistPlayer(Player assistPlayer) {
        this.assistPlayer = assistPlayer;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GoalAssist{");
        sb.append("id=").append(id);
        sb.append(", match=").append(match);
        sb.append(", team=").append(team);
        sb.append(", scorerPlayer=").append(scorerPlayer);
        sb.append(", assistPlayer=").append(assistPlayer);
        sb.append(", minute=").append(minute);
        sb.append('}');
        return sb.toString();
    }
}
