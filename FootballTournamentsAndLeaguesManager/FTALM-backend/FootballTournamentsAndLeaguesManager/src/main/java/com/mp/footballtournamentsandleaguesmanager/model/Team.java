package com.mp.footballtournamentsandleaguesmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Teams")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany(mappedBy = "teams")
    private Set<League> leagues = new HashSet<>();
    @ManyToMany(mappedBy = "teams")
    private Set<Tournament> tournaments = new HashSet<>();
    private String name;
    private Long captainId;
    private Date established;
    private boolean isInLeague;
    private boolean isInTournament;

    public Team(String name) {
        this.name = name;
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Team{");
        sb.append("id=").append(id);
        sb.append(", user=").append(user);
        sb.append(", name='").append(name).append('\'');
        sb.append(", captainId=").append(captainId);
        sb.append('}');
        return sb.toString();
    }
}
