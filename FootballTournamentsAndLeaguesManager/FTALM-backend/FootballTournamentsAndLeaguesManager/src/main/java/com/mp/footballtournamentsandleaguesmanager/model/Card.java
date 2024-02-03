package com.mp.footballtournamentsandleaguesmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Cards")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Card {
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
    private Type cardType;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Card{");
        sb.append("id=").append(id);
        sb.append(", match=").append(match);
        sb.append(", team=").append(team);
        sb.append(", player=").append(player);
        sb.append(", minute=").append(minute);
        sb.append(", cardType=").append(cardType);
        sb.append('}');
        return sb.toString();
    }

    public enum Type {
        YELLOW("Yellow"), SECOND_YELLOW("Second yellow"), RED("Red");

        Type(String s) {
        }
    }
}
