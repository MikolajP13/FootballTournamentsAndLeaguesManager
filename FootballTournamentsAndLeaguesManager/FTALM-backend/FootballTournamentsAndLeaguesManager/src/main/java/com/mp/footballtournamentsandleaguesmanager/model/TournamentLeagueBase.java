package com.mp.footballtournamentsandleaguesmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.sql.Date;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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
    private Date endDate;
    private int numberOfTeams;
    private Status status;

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

    public enum Status {
        NOT_STARTED, IN_PROGRESS, FINISHED
    }
}
