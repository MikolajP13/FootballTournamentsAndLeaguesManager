package com.mp.footballtournamentsandleaguesmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Substitutions")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
