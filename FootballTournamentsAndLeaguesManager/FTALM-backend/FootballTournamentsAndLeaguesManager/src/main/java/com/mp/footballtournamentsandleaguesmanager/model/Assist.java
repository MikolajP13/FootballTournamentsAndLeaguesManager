package com.mp.footballtournamentsandleaguesmanager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "Assists")
public class Assist {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;
    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;
    private int minute;

    public Assist() {
    }

    public Assist(Long id, Match match, Player player, int minute) {
        this.id = id;
        this.match = match;
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
        final StringBuilder sb = new StringBuilder("Assist{");
        sb.append("id=").append(id);
        sb.append(", match=").append(match);
        sb.append(", player=").append(player);
        sb.append(", minute=").append(minute);
        sb.append('}');
        return sb.toString();
    }
}
