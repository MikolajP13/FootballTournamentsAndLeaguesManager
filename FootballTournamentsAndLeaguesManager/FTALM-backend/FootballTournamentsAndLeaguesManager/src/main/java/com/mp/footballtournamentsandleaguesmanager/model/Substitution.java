package com.mp.footballtournamentsandleaguesmanager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Substitutions")
public class Substitution {
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
    @JoinColumn(name = "entering_player_id", nullable = false)
    private Player enteringPlayer;
    @ManyToOne
    @JoinColumn(name = "exiting_player_id", nullable = false)
    private Player exitingPlayer;
    private int minute;

    public Substitution() {
    }

    public Substitution(Long id, Match match, Team team, Player enteringPlayer, Player exitingPlayer, int minute) {
        this.id = id;
        this.match = match;
        this.team = team;
        this.enteringPlayer = enteringPlayer;
        this.exitingPlayer = exitingPlayer;
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

    public Player getEnteringPlayer() {
        return enteringPlayer;
    }

    public void setEnteringPlayer(Player enteringPlayer) {
        this.enteringPlayer = enteringPlayer;
    }

    public Player getExitingPlayer() {
        return exitingPlayer;
    }

    public void setExitingPlayer(Player exitingPlayer) {
        this.exitingPlayer = exitingPlayer;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Substitution{");
        sb.append("id=").append(id);
        sb.append(", match=").append(match);
        sb.append(", team=").append(team);
        sb.append(", enteringPlayer=").append(enteringPlayer);
        sb.append(", exitingPlayer=").append(exitingPlayer);
        sb.append(", minute=").append(minute);
        sb.append('}');
        return sb.toString();
    }
}
