package com.mp.footballtournamentsandleaguesmanager.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "Matches")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date date;
    private Long homeTeamId;
    private Long awayTeamId;
    private Integer homeTeamScore;
    private Integer awayTeamScore;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tournament_id", nullable = true)
    private Tournament tournament;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "league_id", nullable = true)
    private League league;
    private int matchweek;
    private int round;
    private boolean isMatchProtocolCreated;
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Match{");
        sb.append("id=").append(id);
        sb.append(", date=").append(date);
        sb.append(", homeTeam=").append(homeTeamId);
        sb.append(", awayTeam=").append(awayTeamId);
        sb.append(", homeTeamScore=").append(homeTeamScore);
        sb.append(", awayTeamScore=").append(awayTeamScore);
        sb.append(", tournament=").append(tournament);
        sb.append(", league=").append(league);
        sb.append(", matchweek=").append(matchweek);
        sb.append(", round=").append(round);
        sb.append(", isMatchProtocolCreated=").append(isMatchProtocolCreated);
        sb.append('}');
        return sb.toString();
    }
}
