package com.mp.footballtournamentsandleaguesmanager.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Teams")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @ManyToMany(mappedBy = "teams")
    private Set<League> leagues = new HashSet<>();
    @ManyToMany(mappedBy = "teams")
    private Set<Tournament> tournaments = new HashSet<>();
    private String name;
    private Long captainId;

    public Team() {
    }

    public Team(Long id, User user, String name, Long captainId) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.captainId = captainId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<League> getLeagues() {
        return leagues;
    }

    public void setLeagues(Set<League> leagues) {
        this.leagues = leagues;
    }

    public Set<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(Set<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    public Long getCaptainId() {
        return captainId;
    }

    public void setCaptainId(Long captainId) {
        this.captainId = captainId;
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
