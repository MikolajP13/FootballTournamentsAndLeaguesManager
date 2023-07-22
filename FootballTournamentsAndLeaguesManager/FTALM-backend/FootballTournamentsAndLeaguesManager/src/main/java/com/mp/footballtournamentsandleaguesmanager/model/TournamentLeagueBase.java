package com.mp.footballtournamentsandleaguesmanager.model;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Date;

@MappedSuperclass
public class TournamentLeagueBase {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private User user;
    private String name;
    private Date startDate;
    private int numberOfTeams;
    private Status status;

    public TournamentLeagueBase() {
    }

    public TournamentLeagueBase(Long id, User user, String name, Date startDate, int numberOfTeams, Status status) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.startDate = startDate;
        this.numberOfTeams = numberOfTeams;
        this.status = status;
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

    public void setName(String leagueName) {
        this.name = leagueName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getNumberOfTeams() {
        return numberOfTeams;
    }

    public void setNumberOfTeams(int numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("League{");
        sb.append("id=").append(id);
        sb.append(", user=").append(user);
        sb.append(", leagueName='").append(name).append('\'');
        sb.append(", startDate=").append(startDate);
        sb.append(", numberOfTeams=").append(numberOfTeams);
        sb.append(", status=").append(status);
        sb.append('}');
        return sb.toString();
    }

    enum Status {
        NOT_STARTED, IN_PROGRESS, FINISHED
    }
}
