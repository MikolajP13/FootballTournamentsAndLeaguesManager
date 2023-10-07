package com.mp.footballtournamentsandleaguesmanager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Goals")
public class Goal {
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
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;
    private int minute;

    public Goal() {
    }

    public Goal(Long id, Match match, Team team, Player player, int minute) {
        this.id = id;
        this.match = match;
        this.team = team;
        this.player = player;
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Goal{");
        sb.append("id=").append(id);
        sb.append(", match=").append(match);
        sb.append(", team=").append(team);
        sb.append(", player=").append(player);
        sb.append(", minute=").append(minute);
        sb.append('}');
        return sb.toString();
    }
}
